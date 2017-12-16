package org.eddy.queue;

import org.eddy.protocol.Data;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ServerQueue {

    private static final BlockingQueue<Data> queue = new LinkedBlockingQueue<>();
    private static final BlockingQueue<Data> responseQueue = new LinkedBlockingQueue<>();
    private static final int timeout = 1;

    public static void put(Data data) throws InterruptedException {
        queue.put(data);
    }

    public static Data take() throws InterruptedException {
        return queue.take();
    }

    public static void offerResponse(Data response){
        responseQueue.offer(response);
    }

    public static Data takeResponse() throws InterruptedException {
        return responseQueue.take();
    }
}
