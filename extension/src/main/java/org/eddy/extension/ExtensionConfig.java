package org.eddy.extension;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * 扩展点获取
 */
@Component
public class ExtensionConfig {

    private Map<String, String> keyName = new HashMap<>();

    public void setProtocolFactory(String protocolFactory) {
        keyName.put("protocolFactory", protocolFactory);
    }

    public String name(String key) {

        Objects.requireNonNull(key);

        if (! keyName.containsKey(key)) {
            throw new RuntimeException("do not find key:" + key + ", please allocation it first");
        }

        return keyName.get(key);
    }
}
