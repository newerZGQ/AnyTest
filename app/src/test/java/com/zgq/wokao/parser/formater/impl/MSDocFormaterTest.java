package com.zgq.wokao.parser.formater.impl;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by zgq on 2017/2/10.
 */
public class MSDocFormaterTest {
    MSDocFormater formater = new MSDocFormater();
    @Before
    public void setUp() throws Exception {
        formater.params("/Users/zgq/AndroidStudioProjects/wokao_test_files/1.docx");
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void params() throws Exception {

    }

    @Test
    public void getContent() throws Exception {
        String txt = formater.getContent();
        System.out.println("---->>"+txt);
    }

    @Test
    public void fileAvailable() throws Exception {

    }

    @Test
    public void getContent1() throws Exception {

    }

}