package org.eddy.engine;

import org.eddy.extension.Activation;
import org.eddy.extension.Extension;

@Extension("engine")
@Activation("groovy")
public interface Engine {

    void execute(String script);

    String script(String path);
}
