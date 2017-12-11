package org.eddy.protocol;

public interface ProtocolFactory {

    ClientProtocol client();

    ServerProtocol server();
}
