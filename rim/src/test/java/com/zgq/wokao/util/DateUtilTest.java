package com.zgq.wokao.util;

import com.zgq.rim.common.utils.DateUtil;

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
        Assert.assertNotNull(DateUtil.getCurrentDate());
    }

    @Test
    public void toLocalDateFormat() throws Exception {
        Assert.assertNotNull(DateUtil.toLocalDateFormat("2016-12-12"));
    }

    @Test
    public void getTargetDateApart(){
        Assert.assertEquals("2016-12-13",DateUtil.getTargetDateApart("2016-12-12",1));
        Assert.assertEquals("2016-12-11",DateUtil.getTargetDateApart("2016-12-12",-1));
    }

}