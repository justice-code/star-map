package org.eddy.loadbalance;

import org.eddy.util.CollectionUtils;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Random;

public class RandomLoadBalance implements LoadBalance{

    private Random random = new Random();

    @Override
    public String select(List<String> urls) {
        if (CollectionUtils.isEmpty(urls)) {
            throw new RuntimeException("urls is empty");
        }

        int length = urls.size();
        return urls.get(random.nextInt(length));
    }
}
