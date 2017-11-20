package com.zgq.wokao.util;

import java.util.UUID;

/**
 * Created by zgq on 2017/2/11.
 */

public class UUIDUtil {
    public static String getID() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}
