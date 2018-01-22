package org.eddy.selector;

import org.eddy.config.RegistryConfig;
import org.eddy.util.ZkUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component("zookeeperSelector")
public class ZookeeperSelector implements Selector{

    @Autowired
    private SelectorClient selectorClient;

    @Autowired
    private RegistryConfig registryConfig;

    @Override
    public boolean isLeader() {
        return selectorClient.isLeader();
    }

    @Override
    public void start() {
        ZkUtil.createClient(registryConfig.getAddress()).start();
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
