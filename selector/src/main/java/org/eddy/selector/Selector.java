package org.eddy.selector;

import org.eddy.extension.Extension;

import java.io.IOException;

@Extension("selector")
public interface Selector {

    boolean isLeader();

    void start();

    void close();

    void giveBack();
}
