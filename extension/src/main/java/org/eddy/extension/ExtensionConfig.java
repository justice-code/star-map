package org.eddy.extension;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * 扩展点获取
 */
@Component
@ConfigurationProperties("star")
public class ExtensionConfig {

    private Map<String, String> keyName = new HashMap<>();

    public void setProtocolFactory(String protocolFactory) {
        keyName.put("protocolFactory", protocolFactory);
    }

    public void setLoadBalance(String loadBalance) {
        keyName.put("loadBalance", loadBalance);
    }

    public void setRegistry(String registry) {
        keyName.put("registry", registry);
    }

    public void setEngine(String engine) {
        keyName.put("engine", engine);
    }

    public void setSelector(String selector) {
        keyName.put("selector", selector);
    }

    public String name(String key) {

        Objects.requireNonNull(key);

        if (! keyName.containsKey(key)) {
            throw new RuntimeException("do not find key:" + key + ", please allocation it first");
        }

        return keyName.get(key);
    }

    public boolean contain(String key) {
        Objects.requireNonNull(key);

        return keyName.containsKey(key);
    }
}
