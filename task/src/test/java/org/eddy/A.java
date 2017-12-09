package org.eddy;

import java.util.Optional;

public class A {

    public void a() {
        Optional.ofNullable(System.getSecurityManager()).ifPresent(securityManager -> securityManager.checkPermission(new InvokePermission("t")));
        System.out.println("a");
    }
}
