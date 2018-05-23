package org.eddy;

import org.apache.curator.framework.CuratorFramework;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@SpringBootTest(classes = SpringBootTestMain.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class CuratorTest {

    @Autowired
    private CuratorFramework curatorFramework;

    @Test
    public void test() {

    }
}
