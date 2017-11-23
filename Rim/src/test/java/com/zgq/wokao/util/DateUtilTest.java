package com.zgq.wokao.util;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class DateUtilTest {
    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void getCurrentDate() throws Exception {
        System.out.print("getCurrentDate " + DateUtil.getCurrentDate());
        Assert.assertNotNull(DateUtil.getCurrentDate());
    }

    @Test
    public void toLocalDateFormat() throws Exception {
        System.out.print("to local format " + DateUtil.toLocalDateFormat("2016-12-12"));
        Assert.assertNotNull(DateUtil.toLocalDateFormat("2016-12-12"));
    }

    @Test
    public void getTargetDateApart(){
        Assert.assertEquals("2016-12-13",DateUtil.getTargetDateApart("2016-12-12",1));
        Assert.assertEquals("2016-12-11",DateUtil.getTargetDateApart("2016-12-12",-1));
    }

}