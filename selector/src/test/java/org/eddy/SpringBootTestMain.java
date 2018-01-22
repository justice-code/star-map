package org.eddy;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@SpringBootApplication
@SpringBootTest(classes = SpringBootTestMain.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class SpringBootTestMain {

    public static void main(String[] args) {
        SpringApplication springApplication = new SpringApplication(SpringBootTestMain.class);
        springApplication.run(args);
    }

    @Test
    public void test() {
    }
}
