package org.eddy.registry;

import org.eddy.url.URL;
import org.eddy.util.NetUtils;

public class HostInfoHolder {

    public static URL TASK_PROTOCOL = new URL(RegistryConfig.PROTOCOL, NetUtils.getLocalHost(), NetUtils.getAvailablePort());
}
