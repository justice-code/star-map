package org.eddy;

import net.sourceforge.prograde.sm.ProgradeSecurityManager;
import org.eddy.engine.Engine;
import org.eddy.engine.GroovyEngine;
import org.junit.Test;

public class EngineTest {

    @Test
    public void test() {
        Engine engine = new GroovyEngine();
        engine.execute("throw new RuntimeException()");
    }

    @Test
    public void test2() {
        System.setProperty("java.security.policy", "/Users/xuyi/java/star-map/task/src/test/resources/java.policy");
        System.setSecurityManager(new SecurityManager());
        Engine engine = new GroovyEngine();
        engine.execute("print('test')");
    }

    @Test
    public void test3() {
        System.setProperty("java.security.policy", "/Users/xuyi/java/star-map/task/src/test/resources/java.policy");
//        System.setProperty("java.security.manager", "net.sourceforge.prograde.sm.ProgradeSecurityManager");
//        Policy.setPolicy(new ProgradePolicyFile());
        System.setSecurityManager(new ProgradeSecurityManager());
        Engine engine = new GroovyEngine();
        engine.execute("org.eddy.A a = new org.eddy.A()\n" +
                "a.a()");
    }

    @Test
    public void test4() {
        A a = new A();
        a.a();
    }
}
