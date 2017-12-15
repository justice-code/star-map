package org.eddy.executor;

import org.eddy.engine.Engine;
import org.eddy.protocol.Data;
import org.eddy.queue.ServerQueue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
public class TaskExecutor {

    private static final Logger logger = LoggerFactory.getLogger(TaskExecutor.class);
    private final ExecutorService executors = Executors.newSingleThreadExecutor();

    @Autowired
    private Engine engine;

    @PostConstruct
    private void init() {
        executors.submit(() -> {
            while (true) {
                try {
                    Data data = ServerQueue.take();
                    engine.execute(data.getScript());
                } catch (Exception e) {
                    logger.error("execute error", e);
                }
            }
        });
    }
}
