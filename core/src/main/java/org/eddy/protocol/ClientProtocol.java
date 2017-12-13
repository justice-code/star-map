package org.eddy.protocol;

import org.eddy.url.URL;

public interface ClientProtocol {

    void open();

    void connect(URL url) throws Exception;

    void close(URL url);

    void send(URL url, Data data) throws Exception;
}
