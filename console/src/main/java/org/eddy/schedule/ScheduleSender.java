package org.eddy.schedule;

import org.eddy.engine.Engine;
import org.eddy.extension.ExtensionLoader;
import org.eddy.future.StarFuture;
import org.eddy.protocol.ClientProtocol;
import org.eddy.protocol.Data;
import org.eddy.protocol.DataContext;
import org.eddy.protocol.ProtocolFactory;
import org.eddy.registry.Registry;
import org.eddy.registry.RegistryDirectory;
import org.eddy.url.URL;
import org.eddy.util.KeyUtil;
import org.eddy.util.NetUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.concurrent.CountDownLatch;

@Component
public class ScheduleSender implements ApplicationListener{

    private static final Logger logger = LoggerFactory.getLogger(ScheduleSender.class);

    private CountDownLatch latch = new CountDownLatch(1);

    @Autowired
    private ExtensionLoader extensionLoader;

    public void send(String file) {
        try {
            latch.await();

            String content = extensionLoader.loadExtension(Engine.class).script(file);
            RegistryDirectory directory = extensionLoader.loadExtension(Registry.class).getDirectory();
            ClientProtocol client = extensionLoader.loadExtension(ProtocolFactory.class).client();

            URL url = directory.select();
            DataContext context = new DataContext(url, NetUtils.getLocalHost());
            StarFuture future = client.send(url, new Data(KeyUtil.key(), content, context, null));
//            logger.info(future.get().toString());
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
            latch.countDown();
        } else if (event.getClass() == ContextClosedEvent.class) {
            logger.info("unSubscribe task");
            extensionLoader.loadExtension(Registry.class).unSubscribe();
            extensionLoader.loadExtension(ProtocolFactory.class).client().close();
        }
    }
}
