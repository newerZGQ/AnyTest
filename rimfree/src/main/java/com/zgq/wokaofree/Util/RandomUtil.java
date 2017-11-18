package com.zgq.wokaofree.Util;

import java.util.Random;

/**
 * Created by zgq on 2017/4/3.
 */

public class RandomUtil {
    public static int getRandom(int start, int end) {
        Random random = new Random();
        if (start >= end) return 0;
        int tmp = (int) (random.nextFloat() * (end - start));
        return tmp + start;
    }
}
