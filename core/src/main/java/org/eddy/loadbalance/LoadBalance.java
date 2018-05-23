package org.eddy.loadbalance;

import org.eddy.extension.Activation;
import org.eddy.extension.Extension;
import org.eddy.url.URL;

import java.util.List;

@Extension("loadBalance")
@Activation("random")
public interface LoadBalance {

    URL select(List<URL> urls);
}
