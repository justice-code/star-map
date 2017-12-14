package org.eddy.future;

import org.eddy.protocol.Data;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class StarFuture {

    private final Lock lock = new ReentrantLock();
    private final Condition done = lock.newCondition();
    private final int timeout = 3;
    private Data response;

    public Data get() {
        Data result = null;
        try {
            lock.lock();

            if (response != null) {
                result = response;
            } else {
                done.await(timeout, TimeUnit.SECONDS);
                result = response;
            }

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            lock.unlock();
        }

        return result;
    }

    public void receive(Data data) {
        try {
            lock.lock();
            response = data;
            done.signal();
        } finally {
            lock.unlock();
        }
    }
}
