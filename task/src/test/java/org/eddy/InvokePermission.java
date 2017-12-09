package org.eddy;

import java.security.BasicPermission;

public class InvokePermission extends BasicPermission{
    public InvokePermission(String name) {
        super(name);
    }
}
