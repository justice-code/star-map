package org.eddy;


import org.apache.curator.framework.CuratorFramework;
import org.apache.zookeeper.CreateMode;
import org.eddy.selector.Selector;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;

@SpringBootTest(classes = SpringBootTestMain.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class SelectorTest {

    @Autowired
    private Selector selector;

    @Autowired
    private CuratorFramework framework;

    @Test
    public void test() throws IOException {
        selector.start();
        System.in.read();
    }

    @Test
    public void test2() throws Exception {
        framework.create().withMode(CreateMode.PERSISTENT).forPath("/t");
    }
}
