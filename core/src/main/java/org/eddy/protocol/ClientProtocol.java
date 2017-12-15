package org.eddy.protocol;

import org.eddy.future.StarFuture;
import org.eddy.url.URL;

public interface ClientProtocol {

    void open();

    void connect(URL url) throws Exception;

    void close();

    StarFuture send(URL url, Data data) throws Exception;
}
