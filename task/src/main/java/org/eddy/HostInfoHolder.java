package org.eddy;

import org.eddy.registry.RegistryConfig;
import org.eddy.url.URL;
import org.eddy.util.NetUtils;

public class HostInfoHolder {

    public static URL TASK_PROTOCOL = new URL(RegistryConfig.PROTOCOL, NetUtils.getLocalHost(), NetUtils.getAvailablePort());
}
