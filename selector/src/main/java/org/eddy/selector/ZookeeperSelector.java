package org.eddy.selector;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component("zookeeperSelector")
public class ZookeeperSelector implements Selector{

    @Autowired
    private SelectorClient selectorClient;

    @Override
    public boolean isLeader() {
        return selectorClient.isLeader();
    }

    @Override
    public void start() {
        selectorClient.start();
    }

    @Override
    public void close() throws IOException{
        selectorClient.close();
    }

    @Override
    public void giveBack() {
        selectorClient.giveBack();
    }
}
