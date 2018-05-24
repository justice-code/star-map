package org.eddy.schedule;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableScheduling
public class ScheduleSample{

    private static final Logger logger = LoggerFactory.getLogger(ScheduleSample.class);

    @Autowired
    private ScheduleSender sender;

//    @Scheduled(cron = "0/10 * * * * *")
    public void print() {
        sender.send("print");
    }

//    @Scheduled(cron = "0/5 * * * * *")
    public void time() {
        sender.send("time");
    }

//    @Scheduled(cron = "0/5 * * * * *")
    public void waitExecute() {
        sender.send("wait");
    }

    @Scheduled(fixedDelay = 1000)
    public void waitExecute2() {
        sender.send("print");
    }
}
