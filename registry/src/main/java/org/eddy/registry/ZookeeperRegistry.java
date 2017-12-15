package org.eddy.registry;

import org.I0Itec.zkclient.IZkChildListener;
import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.ZkConnection;
import org.eddy.loadbalance.RandomLoadBalance;
import org.eddy.url.URL;
import org.eddy.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

@Component
public class ZookeeperRegistry implements Registry, ApplicationListener {

    private static final Logger logger = LoggerFactory.getLogger(ZookeeperRegistry.class);

    private ZkClient zkClient;
    private String rootPath;
    private RegistryDirectory directory;

    @PostConstruct
    public void init() {
        zkClient = new ZkClient(new ZkConnection(RegistryConfig.ADDRESS), RegistryConstant.timeout);
        rootPath = createNotExists();
        //TODO 拓展点
        directory = new RegistryDirectory(new RandomLoadBalance());
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
        directory.notify(zkClient.getChildren(rootPath));

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

    @Override
    public RegistryDirectory getDirectory() {
        return directory;
    }

    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        if (event.getClass() == ContextRefreshedEvent.class) {
            logger.info("ContextRefreshedEvent");
            doRegister(HostInfoHolder.TASK_PROTOCOL);
        } else if (event.getClass() == ContextClosedEvent.class) {
            logger.info("ContextClosedEvent");
            unRegister(HostInfoHolder.TASK_PROTOCOL);
        }
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
