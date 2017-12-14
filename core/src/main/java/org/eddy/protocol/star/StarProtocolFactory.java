package org.eddy.protocol.star;

import org.eddy.protocol.ClientProtocol;
import org.eddy.protocol.ProtocolFactory;
import org.eddy.protocol.ServerProtocol;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component("starProtocol")
public class StarProtocolFactory implements ProtocolFactory {

    @Autowired
    @Qualifier("starClient")
    private ClientProtocol clientProtocol;

    @Autowired
    @Qualifier("starServer")
    private ServerProtocol serverProtocol;

    @Override
    public ClientProtocol client() {
        return clientProtocol;
    }

    @Override
    public ServerProtocol server() {
        return serverProtocol;
    }
}
