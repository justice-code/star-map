package org.eddy.selector;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.leader.LeaderSelector;
import org.apache.curator.framework.recipes.leader.LeaderSelectorListenerAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.Closeable;
import java.io.IOException;

@Component
public class SelectorClient extends LeaderSelectorListenerAdapter implements Closeable{

    private final LeaderSelector leaderSelector;
    private final String selectorPath = "/star/leader";

    private boolean leader = false;

    @Autowired
    public SelectorClient(CuratorFramework curatorFramework) {
        this.leaderSelector = new LeaderSelector(curatorFramework, selectorPath, this);
        this.leaderSelector.autoRequeue();
    }

    @Override
    public void takeLeadership(CuratorFramework curatorFramework) throws Exception {
        leader = true;
    }

    @Override
    public void close() throws IOException {
        this.leaderSelector.close();
    }

    public void start() {
        this.leaderSelector.start();
    }

    public boolean isLeader() {
        return leader;
    }

    public void giveBack() {
        this.leaderSelector.requeue();
    }

}
