package org.eddy.future;

import org.eddy.protocol.Data;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class FutureHolder {

    private static Map<String, StarFuture> holder = new HashMap<>();

    public static void receive(Data result) {
        Optional.ofNullable(holder.remove(result.getId())).orElseThrow(() -> new RuntimeException("can not find future")).receive(result);
    }

    public static StarFuture createFuture(Data data) {
        StarFuture future = new StarFuture();
        holder.put(data.getId(), future);
        return future;
    }
}
