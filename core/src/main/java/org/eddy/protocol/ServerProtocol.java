package org.eddy.protocol;

import io.netty.channel.socket.SocketChannel;
import org.eddy.url.URL;

public interface ServerProtocol {

    void openServer(URL url) throws Exception;

}
