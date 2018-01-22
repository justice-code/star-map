package org.eddy.selector;

import org.apache.curator.framework.CuratorFramework;
import org.eddy.config.RegistryConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component("zookeeperSelector")
public class ZookeeperSelector implements Selector{

    @Autowired
    private SelectorClient selectorClient;

    @Autowired
    private CuratorFramework client;

    @Override
    public boolean isLeader() {
        return selectorClient.isLeader();
    }

    @Override
    public void start() {
        client.start();
        selectorClient.start();
    }

    @Override
    public void close(){
        try {
            selectorClient.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void giveBack() {
        selectorClient.giveBack();
    }

}
