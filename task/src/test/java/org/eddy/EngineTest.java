package org.eddy;

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
        Engine engine = new GroovyEngine();
        engine.execute("print('test')");
    }
}
