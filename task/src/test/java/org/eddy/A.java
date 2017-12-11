package org.eddy;

import org.eddy.permission.annotation.Permission;
import org.springframework.stereotype.Component;

@Component
public class A {

    @Permission("test")
    public void a() {
        System.out.println("a");
    }
}
