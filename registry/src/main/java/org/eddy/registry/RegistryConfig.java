package org.eddy.registry;

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
    public static String PROTOCOL = "star";
    private String address;
}
