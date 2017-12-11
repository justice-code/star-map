package org.eddy;

import net.sourceforge.prograde.sm.ProgradeSecurityManager;
import org.eddy.engine.Engine;
import org.eddy.engine.GroovyEngine;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@SpringBootApplication
@SpringBootTest(classes = SpringBootTestMain.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class SpringBootTestMain {

    public static void main(String[] args) {
        System.setProperty("spring.aop.auto", "true");
        SpringApplication springApplication = new SpringApplication(SpringBootTestMain.class);
        springApplication.run(args);
    }

    @Test
    public void test() {
        System.setProperty("java.security.policy", "/Users/xuyi/java/star-map/task/src/test/resources/java.policy");
        System.setSecurityManager(new ProgradeSecurityManager());
        Engine engine = new GroovyEngine();
        engine.execute("org.eddy.A a1 = org.eddy.SpringContext.getA()\n" +
                "a1.a()");
    }
}
