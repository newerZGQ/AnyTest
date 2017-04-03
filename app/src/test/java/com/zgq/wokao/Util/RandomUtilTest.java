package com.zgq.wokao.Util;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by zgq on 2017/4/3.
 */
public class RandomUtilTest {
    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void getRandom() throws Exception {
        System.out.println(RandomUtil.getRandom(0,4));
        System.out.println(RandomUtil.getRandom(0,4));
        System.out.println(RandomUtil.getRandom(0,4));
        System.out.println(RandomUtil.getRandom(0,4));
        System.out.println(RandomUtil.getRandom(0,4));
        System.out.println(RandomUtil.getRandom(0,4));
        System.out.println(RandomUtil.getRandom(0,4));
    }

}