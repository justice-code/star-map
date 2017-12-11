package org.eddy.protocol;

public interface ServerProtocol {

    void openServer();

    void connect();

    void close();

    void receive(Data data);
}
