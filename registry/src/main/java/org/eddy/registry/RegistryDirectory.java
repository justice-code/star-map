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

    private String path;
    private List<String> urls;

    public RegistryDirectory(String path) {
        this.path = path;
    }

    public void notify(String parentPath, List<String> currentChildren) {
        if (StringUtils.isEquals(parentPath, path)) {
            urls = currentChildren;
        }
    }

    public List<String> list() {
        return urls;
    }
}
