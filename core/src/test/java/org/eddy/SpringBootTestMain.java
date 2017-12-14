package org.eddy;

import org.eddy.future.StarFuture;
import org.eddy.protocol.ClientProtocol;
import org.eddy.protocol.Data;
import org.eddy.protocol.ProtocolFactory;
import org.eddy.url.URL;
import org.eddy.util.KeyUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.concurrent.TimeUnit;

@SpringBootApplication
@SpringBootTest(classes = SpringBootTestMain.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class SpringBootTestMain {

    @Autowired
    @Qualifier("starProtocol")
    private ProtocolFactory protocolFactory;

    private URL url = new URL("star", "127.0.0.1", 10010);

    public static void main(String[] args) {
        SpringApplication springApplication = new SpringApplication(SpringBootTestMain.class);
        springApplication.run(args);
    }

    @Test
    public void testServer() throws Exception {
        protocolFactory.server().openServer(url);
        System.in.read();
    }

    @Test
    public void testClient() throws Exception {
        Data data = new Data(KeyUtil.key(), false);
        Data second = new Data(KeyUtil.key(), "test", false);
        ClientProtocol clientProtocol = protocolFactory.client();
        clientProtocol.open();
        clientProtocol.connect(url);
        StarFuture starFuture1 = clientProtocol.send(url, data);
        StarFuture starFuture2 = clientProtocol.send(url, second);

        TimeUnit.SECONDS.sleep(1);

        long begin = System.currentTimeMillis();
        System.out.println(starFuture1.get());
        System.out.println(starFuture2.get());
        System.out.println("task:" + (System.currentTimeMillis() - begin));

        System.in.read();
    }

}
