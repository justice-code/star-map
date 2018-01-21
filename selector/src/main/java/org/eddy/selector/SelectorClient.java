package org.eddy.selector;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.leader.LeaderSelector;
import org.apache.curator.framework.recipes.leader.LeaderSelectorListenerAdapter;

import java.io.Closeable;
import java.io.IOException;

public class SelectorClient extends LeaderSelectorListenerAdapter implements Closeable{

    private final LeaderSelector leaderSelector;
    private final String selectorPath = "/star/leader";

    public SelectorClient(CuratorFramework curatorFramework) {
        this.leaderSelector = new LeaderSelector(curatorFramework, selectorPath, this);
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
