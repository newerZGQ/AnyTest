package com.zgq.wokao.util;

import java.util.UUID;

public class UUIDUtil {
    public static String getID() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}
