package org.eddy.loadbalance;

import org.eddy.url.URL;
import org.eddy.util.CollectionUtils;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Random;

@Component("random")
public class RandomLoadBalance implements LoadBalance{

    private Random random = new Random();

    @Override
    public URL select(List<URL> urls) {
        if (CollectionUtils.isEmpty(urls)) {
            throw new RuntimeException("urls is empty");
        }

        int length = urls.size();
        return urls.get(random.nextInt(length));
    }
}
