package com.iqiyi.vr.assistant.util;

import com.iqiyi.vr.assistant.R;

import java.util.Random;

/**
 * Created by wangyancong on 2017/8/31.
 * 默认背景工厂类
 */

public final class DefIconFactory {

    private final static int[] DEF_ICON_ID = new int[]{
            R.drawable.ic_default_1,
            R.drawable.ic_default_2,
            R.drawable.ic_default_3,
            R.drawable.ic_default_4,
            R.drawable.ic_default_5
    };

    private static Random random = new Random();

    private DefIconFactory() {
        throw new RuntimeException("DefIconFactory cannot be initialized!");
    }

    public static int provideIcon() {
        int index = random.nextInt(DEF_ICON_ID.length);
        return DEF_ICON_ID[index];
    }
}
