package org.eddy.schedule;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableScheduling
public class PrintSchedule {

    private static final Logger logger = LoggerFactory.getLogger(PrintSchedule.class);

    @Autowired
    private ScheduleSender sender;

    @Scheduled(cron = "0/10 * * * * ?")
    public void print() {
        sender.send("print");
    }
}
