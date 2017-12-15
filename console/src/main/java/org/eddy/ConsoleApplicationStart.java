package org.eddy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.system.ApplicationPidFileWriter;

@SpringBootApplication
public class ConsoleApplicationStart {

    public static void main(String[] args) {
        SpringApplication springApplication = new SpringApplication(ConsoleApplicationStart.class);
        springApplication.addListeners(new ApplicationPidFileWriter("console.pid"));
        springApplication.run(args);
    }
}
