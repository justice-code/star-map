package org.eddy.registry;

import lombok.*;
import org.eddy.loadbalance.LoadBalance;
import org.eddy.url.URL;
import org.eddy.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.List;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class RegistryDirectory {

    private List<String> urls;

    @NonNull
    private LoadBalance loadBalance;

    public void notify(List<String> currentChildren) {
        urls = currentChildren;
    }

    public URL list() {
        String url = loadBalance.select(urls);
        return URL.valueOf(URL.decode(url));
    }
}
