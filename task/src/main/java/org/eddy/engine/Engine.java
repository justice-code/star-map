package org.eddy.engine;

import org.eddy.extension.Extension;

@Extension("engine")
public interface Engine {

    void execute(String script);
}
