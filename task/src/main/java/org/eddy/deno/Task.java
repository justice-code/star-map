package org.eddy.deno;

import org.eddy.permission.annotation.Permission;
import org.springframework.stereotype.Component;

@Component
public class Task {

    @Permission("deny")
    public void print() {
        System.out.println("test spring invoke");
    }
}
