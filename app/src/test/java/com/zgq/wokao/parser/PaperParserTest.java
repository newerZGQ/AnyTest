package com.zgq.wokao.parser;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Created by zgq on 2017/2/20.
 */
public class PaperParserTest {
    PaperParser paperParser = new PaperParser();
    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void parse() throws Exception {
        InputStream is = new FileInputStream(new File("/Users/zgq/AndroidStudioProjects/wokao_test_files/test.txt"));
        paperParser.initParam().parse(is);
        ArrayList<PaperParser.Topic> list = paperParser.getTopicLists();
        for (PaperParser.Topic topic : list){
            System.out.println("--->>"+topic.getType());
            System.out.println("--->>"+topic.getContent());
        }
    }

}