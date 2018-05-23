package org.eddy.protocol;

import org.eddy.extension.Activation;
import org.eddy.extension.Extension;

@Extension("protocolFactory")
@Activation("starProtocol")
public interface ProtocolFactory {

    ClientProtocol client();

    ServerProtocol server();

    String protocol();
}
