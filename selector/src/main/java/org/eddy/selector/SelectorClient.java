package org.eddy.selector;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.leader.LeaderSelector;
import org.apache.curator.framework.recipes.leader.LeaderSelectorListenerAdapter;
import org.eddy.config.RegistryConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.Closeable;
import java.io.IOException;

@Component
public class SelectorClient extends LeaderSelectorListenerAdapter implements Closeable{

    private final String name;
    private final LeaderSelector leaderSelector;

    @Autowired
    private RegistryConfig registryConfig;

    public SelectorClient(CuratorFramework curatorFramework) {
        this.name = registryConfig.getName();
        this.leaderSelector = new LeaderSelector(curatorFramework, registryConfig.getSelectorPath(), this);
        this.leaderSelector.autoRequeue();
    }

    @Override
    public void takeLeadership(CuratorFramework curatorFramework) throws Exception {

    }

    @Override
    public void close() throws IOException {
        this.leaderSelector.close();
    }
}
