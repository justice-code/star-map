package org.eddy.engine;

import groovy.lang.Binding;
import groovy.lang.GroovyShell;

public class GroovyEngine implements Engine{
    @Override
    public void execute(String script) {
        Binding binding = new Binding();
        GroovyShell groovyShell = new GroovyShell(this.getClass().getClassLoader(), binding);
        groovyShell.evaluate(script);
    }
}