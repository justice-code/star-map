package org.eddy.selector;

import org.eddy.extension.Activation;
import org.eddy.extension.Extension;

import java.io.IOException;

@Extension("selector")
@Activation("zookeeperSelector")
public interface Selector {

    boolean isLeader();

    void start();

    void close();

    void giveBack();
}
