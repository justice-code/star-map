package org.eddy.engine;

import groovy.lang.Binding;
import groovy.lang.GroovyShell;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;

@Component("groovy")
public class GroovyEngine implements Engine{
    @Override
    public void execute(String script) {
        Binding binding = new Binding();
        GroovyShell groovyShell = new GroovyShell(this.getClass().getClassLoader(), binding);
        groovyShell.evaluate(script);
    }

    @Override
    public String script(String path) {
        String p = GroovyEngine.class.getClassLoader().getResource("groovy/" + path + ".Groovy").getFile();
        try {
            return FileUtils.readFileToString(new File(path), "UTF-8");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
