package org.eddy.registry;

import org.eddy.url.URL;
import org.eddy.util.StringUtils;

public class ZookeeperRegistry implements Registry {

    @Override
    public void doRegister(URL url) {

    }

    @Override
    public void unRegister(URL url) {

    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unSubscribe() {

    }

    private static String rootPath() {
        return String.join(RegistryConstant.separator, StringUtils.EMPTY, RegistryConfig.GROUP, RegistryConstant.provider, StringUtils.EMPTY);
    }

}
