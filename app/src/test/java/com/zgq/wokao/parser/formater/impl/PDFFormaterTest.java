package com.zgq.wokao.parser.formater.impl;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by zgq on 2017/2/11.
 */
public class PDFFormaterTest {
    PDFFormater formater = new PDFFormater();
    @Before
    public void setUp() throws Exception {
        formater.params("/Users/zgq/AndroidStudioProjects/wokao_test_files/1.pdf");
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void params() throws Exception {

    }

    @Test
    public void getContent() throws Exception {
        String content = formater.getContent();
    }

    @Test
    public void fileAvailable() throws Exception {

    }

    @Test
    public void getContent1() throws Exception {

    }

}