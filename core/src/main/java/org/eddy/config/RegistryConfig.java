package org.eddy.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("star.zookeeper")
@Getter
@Setter
public class RegistryConfig {

    public static String GROUP = "star";
    private String address;
    private String name;
    private String selectorPath;
}
