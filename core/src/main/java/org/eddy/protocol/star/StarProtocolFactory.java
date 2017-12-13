package org.eddy.protocol.star;

import org.eddy.protocol.ClientProtocol;
import org.eddy.protocol.ProtocolFactory;
import org.eddy.protocol.ServerProtocol;
import org.springframework.stereotype.Component;

@Component
public class StarProtocolFactory implements ProtocolFactory {

    @Override
    public ClientProtocol client() {
        return null;
    }

    @Override
    public ServerProtocol server() {
        return null;
    }
}
