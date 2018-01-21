package org.eddy.registry;

import org.I0Itec.zkclient.IZkChildListener;
import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.ZkConnection;
import org.eddy.config.RegistryConfig;
import org.eddy.extension.ExtensionLoader;
import org.eddy.loadbalance.LoadBalance;
import org.eddy.url.URL;
import org.eddy.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

@Component("zookeeper")
public class ZookeeperRegistry implements Registry {

    private static final Logger logger = LoggerFactory.getLogger(ZookeeperRegistry.class);

    private ZkClient zkClient;
    private String rootPath;
    private RegistryDirectory directory;

    @Autowired
    private ExtensionLoader extensionLoader;
    @Autowired
    private RegistryConfig registryConfig;

    @PostConstruct
    public void init() {
        zkClient = new ZkClient(new ZkConnection(registryConfig.getAddress()), RegistryConstant.timeout);
        rootPath = createNotExists();
        directory = new RegistryDirectory(extensionLoader.loadExtension(LoadBalance.class));
    }

    @Override
    public void doRegister(URL url) {
        zkClient.createEphemeral(String.join(RegistryConstant.separator, rootPath, URL.encode(url.toFullString())));
    }

    @Override
    public void unRegister(URL url) {
        String path = String.join(RegistryConstant.separator, rootPath, URL.encode(url.toFullString()));
        zkClient.delete(path);
    }

    @Override
    public void subscribe() {
        List<String> children = zkClient.subscribeChildChanges(rootPath, new IZkChildListener() {
            @Override
            public void handleChildChange(String parentPath, List<String> currentChildren) throws Exception {
                directory.notify(currentChildren);
            }
        });
        directory.notify(children);
    }

    @Override
    public void unSubscribe() {
        zkClient.unsubscribeAll();
    }

    @Override
    public RegistryDirectory getDirectory() {
        return directory;
    }

    //************************************************** private ********************************************************

    private String createNotExists() {

        String groupPath = String.join(RegistryConstant.separator, StringUtils.EMPTY, RegistryConfig.GROUP);
        if (! zkClient.exists(groupPath)) {
            zkClient.createPersistent(groupPath);
        }

        String providerPath = String.join(RegistryConstant.separator, StringUtils.EMPTY, RegistryConfig.GROUP, RegistryConstant.provider);
        if (! zkClient.exists(providerPath)) {
            zkClient.createPersistent(providerPath);
        }

        return providerPath;
    }

}
