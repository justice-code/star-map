package org.eddy.loadbalance;

import org.eddy.url.URL;

import java.util.List;

public interface LoadBalance {

    String select(List<String> urls);
}
