package org.eddy.util;

import java.util.UUID;

public class KeyUtil {

    public static String key() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }
}
