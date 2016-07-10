package com.zgq.wokao.Util;

import android.util.Log;

import org.junit.Before;
import org.junit.Test;

/**
 * Created by zgq on 16-7-10.
 */
public class DrawableUtilTest {

    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void testGetColor() throws Exception {
        for (int i = 0;i<10;i++) {
            Log.d("getDrawable", "" + DrawableUtil.getDrawable());
        }
    }
}