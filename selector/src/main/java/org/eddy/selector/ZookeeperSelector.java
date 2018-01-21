package org.eddy.selector;

import org.springframework.stereotype.Component;

@Component("zookeeperSelector")
public class ZookeeperSelector implements Selector{

    @Override
    public boolean isLeader() {
        return false;
    }
}
