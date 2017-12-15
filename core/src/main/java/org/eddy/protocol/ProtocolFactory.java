package org.eddy.protocol;

import org.eddy.extension.Extension;

@Extension("protocolFactory")
public interface ProtocolFactory {

    ClientProtocol client();

    ServerProtocol server();
}
