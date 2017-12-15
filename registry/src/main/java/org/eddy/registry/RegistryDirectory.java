package org.eddy.registry;

import lombok.*;
import org.eddy.loadbalance.LoadBalance;
import org.eddy.url.URL;
import org.eddy.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    public URL select() {
        return loadBalance.select(list());
    }

    public List<URL> list() {
        return Optional.ofNullable(urls).orElse(new ArrayList<>()).stream().map(url -> URL.valueOf(URL.decode(url))).collect(Collectors.toList());
    }
}
