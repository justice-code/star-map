package org.eddy.protocol;

public interface ClientProtocol {

    void open();

    void connect();

    void close();

    void send(Data data);
}
