package org.eddy.registry;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.api.CuratorWatcher;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.Watcher;
import org.eddy.config.RegistryConfig;
import org.eddy.extension.ExtensionLoader;
import org.eddy.loadbalance.LoadBalance;
import org.eddy.url.URL;
import org.eddy.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.stream.Collectors;

@Component("curatorZkRegistry")
public class CuratorZkRegistry implements Registry {

    private String rootPath;
    private RegistryDirectory directory;

    @Autowired
    private ExtensionLoader extensionLoader;

    @Autowired
    private CuratorFramework curatorFramework;


    @PostConstruct
    public void init() throws Exception {
        rootPath = createNotExists();
        directory = new RegistryDirectory(extensionLoader.loadExtension(LoadBalance.class));
    }

    @Override
    public void doRegister(URL url) {
        try {
            curatorFramework.create().withMode(CreateMode.EPHEMERAL).forPath(String.join(RegistryConstant.separator, rootPath, URL.encode(url.toFullString())));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void unRegister(URL url) {
        String path = String.join(RegistryConstant.separator, rootPath, URL.encode(url.toFullString()));
        try {
            curatorFramework.delete().forPath(path);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void subscribe() {
        try {
            List<String> children = curatorFramework.getChildren().usingWatcher((CuratorWatcher) event -> {
                if (event.getType() != Watcher.Event.EventType.NodeChildrenChanged) {
                    return;
                }

                String root = event.getPath();
                List<String> listenChildren = curatorFramework.getChildren().forPath(root);
                directory.notify(listenChildren);

            }).forPath(rootPath);

            directory.notify(children);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void unSubscribe() {
        curatorFramework.watches().removeAll();
    }

    @Override
    public RegistryDirectory getDirectory() {
        return directory;
    }

    //--------------------------------------------private--------------------------------------------

    private String createNotExists() throws Exception {
        String groupPath = String.join(RegistryConstant.separator, StringUtils.EMPTY, RegistryConfig.GROUP);
        if (null == curatorFramework.checkExists().forPath(groupPath)) {
            curatorFramework.create().withMode(CreateMode.EPHEMERAL).forPath(groupPath);
        }

        String providerPath = String.join(RegistryConstant.separator, StringUtils.EMPTY, RegistryConfig.GROUP, RegistryConstant.provider);
        if (null == curatorFramework.checkExists().forPath(providerPath)) {
            curatorFramework.create().withMode(CreateMode.EPHEMERAL).forPath(providerPath);
        }

        return providerPath;
    }
}
