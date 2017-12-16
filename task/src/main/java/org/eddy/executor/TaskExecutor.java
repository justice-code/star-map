package org.eddy.executor;

import org.eddy.engine.Engine;
import org.eddy.extension.ExtensionLoader;
import org.eddy.protocol.Data;
import org.eddy.protocol.DataResponse;
import org.eddy.protocol.ProtocolFactory;
import org.eddy.queue.ServerQueue;
import org.eddy.registry.HostInfoHolder;
import org.eddy.registry.Registry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
public class TaskExecutor implements ApplicationListener{

    private static final Logger logger = LoggerFactory.getLogger(TaskExecutor.class);
    private final ExecutorService executors = Executors.newFixedThreadPool(2);

    @Autowired
    private ExtensionLoader extensionLoader;

    @PostConstruct
    private void init() {
        //执行脚本
        executors.submit(() -> {
            while (true) {
                Data data = null;
                boolean success = false;
                long begin = 0;
                try {
                    data = ServerQueue.take();
                    begin = System.currentTimeMillis();
                    extensionLoader.loadExtension(Engine.class).execute(data.getScript());
                    success = true;
                } catch (Exception e) {
                    logger.error("execute error", e);
                }
                handleResponse(data, success, begin);
            }
        });

        //处理响应
        executors.submit(() -> {
            while (true) {
                try {
                    Data response = ServerQueue.takeResponse();
                    extensionLoader.loadExtension(ProtocolFactory.class).server().response(response);
                } catch (Exception e) {
                    logger.error("handle response error", e);
                }
            }
        });
    }

    private void handleResponse(Data data, boolean success, long begin) {
        if (null == data) {
            return;
        }

        DataResponse response = new DataResponse(LocalDateTime.ofEpochSecond(begin / 1000, 0, ZoneOffset.ofHours(8)), success ? System.currentTimeMillis() - begin : 0, success);
        data.setResponse(response);

        ServerQueue.offerResponse(data);
    }

    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        if (event.getClass() == ContextRefreshedEvent.class) {
            logger.info("register url: " + HostInfoHolder.TASK_PROTOCOL);
            extensionLoader.loadExtension(Registry.class).doRegister(HostInfoHolder.TASK_PROTOCOL);
            try {
                extensionLoader.loadExtension(ProtocolFactory.class).server().openServer(HostInfoHolder.TASK_PROTOCOL);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else if (event.getClass() == ContextClosedEvent.class) {
            logger.info("unregister url: " + HostInfoHolder.TASK_PROTOCOL);
            extensionLoader.loadExtension(Registry.class).unRegister(HostInfoHolder.TASK_PROTOCOL);
            extensionLoader.loadExtension(ProtocolFactory.class).server().close();
        }
    }
}
