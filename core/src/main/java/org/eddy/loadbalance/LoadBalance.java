package org.eddy.loadbalance;

import org.eddy.extension.Extension;
import org.eddy.url.URL;

import java.util.List;

@Extension("loadBalance")
public interface LoadBalance {

    String select(List<String> urls);
}
