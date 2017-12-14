package org.eddy.listener;

import org.eddy.HostInfoHolder;
import org.eddy.registry.Registry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;

public class ApplicationStartListener implements ApplicationListener<ApplicationReadyEvent> {

    @Autowired
    private Registry registry;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        registry.doRegister(HostInfoHolder.TASK_PROTOCOL);
    }
}
