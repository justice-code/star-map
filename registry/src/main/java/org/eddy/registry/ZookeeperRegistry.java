package org.eddy.registry;

import org.I0Itec.zkclient.IZkChildListener;
import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.ZkConnection;
import org.eddy.url.URL;
import org.eddy.util.StringUtils;

import javax.annotation.PostConstruct;
import java.util.List;

public class ZookeeperRegistry implements Registry {

    private ZkClient zkClient;
    private String rootPath;
    private RegistryDirectory directory;

    @PostConstruct
    public void init() {
        directory = new RegistryDirectory();
        zkClient = new ZkClient(new ZkConnection(RegistryConfig.ADDRESS), RegistryConstant.timeout);
        rootPath = createNotExists();
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
        zkClient.subscribeChildChanges(rootPath, new IZkChildListener() {
            @Override
            public void handleChildChange(String parentPath, List<String> currentChildren) throws Exception {
                directory.notify(currentChildren);
            }
        });
    }

    @Override
    public void unSubscribe() {
        zkClient.unsubscribeAll();
    }

    //************************************************** private ********************************************************

    private String createNotExists() {

        String groupPath = String.join(RegistryConstant.separator, StringUtils.EMPTY, RegistryConfig.GROUP);
        if (! zkClient.exists(groupPath)) {
            zkClient.createPersistent(groupPath);
        }

        String providerPath = String.join(RegistryConstant.separator, StringUtils.EMPTY, RegistryConfig.GROUP, RegistryConstant.provider, StringUtils.EMPTY);
        if (! zkClient.exists(providerPath)) {
            zkClient.createPersistent(providerPath);
        }

        return providerPath;
    }

}
