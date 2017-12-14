package org.eddy.registry;

import org.eddy.url.URL;

public interface Registry {

    void doRegister(URL url);

    void unRegister(URL url);

    void subscribe();

    void unSubscribe();

    RegistryDirectory getDirectory();
}
