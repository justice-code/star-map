package org.eddy.registry;

import org.eddy.extension.Extension;
import org.eddy.url.URL;

@Extension("registry")
public interface Registry {

    void doRegister(URL url);

    void unRegister(URL url);

    void subscribe();

    void unSubscribe();

    RegistryDirectory getDirectory();

}
