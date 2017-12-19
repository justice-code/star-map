package org.eddy.permission.config;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import net.sourceforge.prograde.sm.ProgradeSecurityManager;
import org.eddy.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@ToString
@Component
@ConfigurationProperties("star.policy")
public class SandboxConfiguration {
    private static final Logger logger = LoggerFactory.getLogger(SandboxConfiguration.class);

    private String path;

    public void configSandbox() {
        if (StringUtils.isBlank(path)) {
            return;
        }
        logger.info("use security path:" + path);
        System.setProperty("java.security.policy", path);
        System.setSecurityManager(new ProgradeSecurityManager());
    }
}
