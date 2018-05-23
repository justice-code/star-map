package org.eddy;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.api.CuratorWatcher;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.data.Stat;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@SpringBootTest(classes = SpringBootTestMain.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class CuratorTest {

    @Autowired
    private CuratorFramework curatorFramework;

    @Test
    public void test() throws Exception {
        Stat stat = curatorFramework.checkExists().forPath("/star");
    }

    @Test
    public void test2() throws Exception {
        List<String> children = curatorFramework.getChildren().usingWatcher((CuratorWatcher) event -> {
            if (event.getType() == Watcher.Event.EventType.NodeChildrenChanged) {
                System.out.println(event.getPath());
                System.out.println(curatorFramework.getChildren().inBackground().forPath(event.getPath()));
            }
        }).forPath("/star");

        System.out.println(children);
        System.in.read();
    }
}
