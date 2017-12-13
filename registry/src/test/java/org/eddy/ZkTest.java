package org.eddy;

import org.I0Itec.zkclient.IZkChildListener;
import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.ZkConnection;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

public class ZkTest {

    @Test
    public void test() throws IOException {
        ZkClient zkc = new ZkClient(new ZkConnection("192.168.23.136:2181"), 3_000);
//        zkc.createEphemeral("/star");
//        zkc.createPersistent("/star");
//        zkc.createPersistent("/star/provider");
        zkc.subscribeChildChanges("/test/provider", new IZkChildListener() {
            @Override
            public void handleChildChange(String parentPath, List<String> currentChildren) throws Exception {
                currentChildren.stream().forEach(s -> System.out.println(s));
            }
        });

        System.in.read();
    }

    @Test
    public void test2() {
        ZkClient zkc = new ZkClient(new ZkConnection("192.168.23.127:2181"), 3_000);
        zkc.createPersistent("/star");
    }

    @Test
    public void test3() {
        ZkClient zkc = new ZkClient(new ZkConnection("192.168.23.127:2181"), 3_000);
        System.out.println(zkc.delete("/t"));;
    }
}
