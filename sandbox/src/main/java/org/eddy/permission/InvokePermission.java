package org.eddy.permission;

import java.security.BasicPermission;

public class InvokePermission extends BasicPermission {

    public InvokePermission() {
        super("invoke");
    }
}
