package org.eddy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.system.ApplicationPidFileWriter;

@SpringBootApplication
public class ApplicationStart {

    public static void main(String[] args) {
        SpringApplication springApplication = new SpringApplication(ApplicationStart.class);
        springApplication.addListeners(new ApplicationPidFileWriter("task.pid"));
        springApplication.run(args);
    }
}