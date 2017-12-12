package org.eddy.registry;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.eddy.util.StringUtils;

import java.util.List;

@Getter
@Setter
@ToString
public class RegistryDirectory {

    private List<String> urls;

    public void notify(String parentPath, List<String> currentChildren) {
        urls = currentChildren;
    }

    public List<String> list() {
        return urls;
    }
}
