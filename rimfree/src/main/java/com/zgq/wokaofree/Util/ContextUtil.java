package com.zgq.wokaofree.Util;

import android.content.Context;

/**
 * Created by zhangguoqiang on 2017/2/12.
 */

public class ContextUtil {
    private static Context context;

    public static void init(Context ct) {
        context = ct;
    }

    public static Context getContext() {
        if (context != null) {
            return context;
        }
        return null;
    }
}
