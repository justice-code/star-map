package org.eddy;


import org.eddy.selector.Selector;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@SpringBootTest(classes = SpringBootTestMain.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class SelectorTest {

    @Autowired
    private Selector selector;

    @Test
    public void test() {
        selector.start();
        System.out.println(selector.isLeader());
    }
}
