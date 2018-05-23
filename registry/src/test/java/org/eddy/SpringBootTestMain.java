package org.eddy;

import org.eddy.registry.Registry;
import org.eddy.registry.RegistryDirectory;
import org.eddy.url.URL;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

@SpringBootApplication
@SpringBootTest(classes = SpringBootTestMain.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class SpringBootTestMain {

    @Autowired
    @Qualifier("zookeeper")
    private Registry registry;

    public static void main(String[] args) {
        SpringApplication springApplication = new SpringApplication(SpringBootTestMain.class);
        springApplication.run(args);
    }

    @Test
    public void registerTest() throws IOException {
        registry.doRegister(new URL("star", "127.0.0.1", 10010));
        registry.doRegister(new URL("star", "127.0.0.2", 10010));

        System.in.read();
    }

    @Test
    public void testSub() throws IOException, InterruptedException {
        registry.subscribe();
        RegistryDirectory directory = registry.getDirectory();

        System.out.println(directory.getUrls());
        TimeUnit.SECONDS.sleep(10);

        System.out.println(directory.getUrls());
        System.out.println(directory.list());
    }

}
