package org.eddy.registry;

import org.eddy.extension.ExtensionLoader;
import org.eddy.protocol.ProtocolFactory;
import org.eddy.url.URL;
import org.eddy.util.NetUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class HostInfoHolder {

    @Autowired
    private ExtensionLoader extensionLoader;

    private static final String LOCALHOST = NetUtils.getLocalHost();
    private static final int PORT = NetUtils.getAvailablePort();

    public URL taskProtocolUrl() {
        return new URL(extensionLoader.loadExtension(ProtocolFactory.class).protocol(), LOCALHOST, PORT);
    }
}
