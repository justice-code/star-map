package org.eddy.schedule;

import org.apache.commons.io.FileUtils;
import org.eddy.extension.ExtensionLoader;
import org.eddy.protocol.ClientProtocol;
import org.eddy.protocol.Data;
import org.eddy.protocol.ProtocolFactory;
import org.eddy.registry.Registry;
import org.eddy.registry.RegistryDirectory;
import org.eddy.util.KeyUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
public class ScheduleSender implements ApplicationListener{

    private static final Logger logger = LoggerFactory.getLogger(ScheduleSender.class);

    @Autowired
    private ExtensionLoader extensionLoader;

    public void send(String file) {
        try {
            String path = ScheduleSender.class.getClassLoader().getResource("groovy/" + file + ".Groovy").getFile();
            String content = FileUtils.readFileToString(new File(path), "UTF-8");
            RegistryDirectory directory = extensionLoader.loadExtension(Registry.class).getDirectory();
            ClientProtocol client = extensionLoader.loadExtension(ProtocolFactory.class).client();
            client.send(directory.select(), new Data(KeyUtil.key(), content, false));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        if (event.getClass() == ContextRefreshedEvent.class) {
            extensionLoader.loadExtension(Registry.class).subscribe();
            extensionLoader.loadExtension(ProtocolFactory.class).client().open();
            logger.info("subscribe:" + extensionLoader.loadExtension(Registry.class).getDirectory().list());
        } else if (event.getClass() == ContextClosedEvent.class) {
            logger.info("unSubscribe task");
            extensionLoader.loadExtension(Registry.class).unSubscribe();
            extensionLoader.loadExtension(ProtocolFactory.class).client().close();
        }
    }
}
