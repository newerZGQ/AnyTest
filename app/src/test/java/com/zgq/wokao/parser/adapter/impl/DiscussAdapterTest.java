package com.zgq.wokao.parser.adapter.impl;

import com.zgq.wokao.model.paper.DiscussQuestion;
import com.zgq.wokao.model.paper.TFQuestion;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Created by zhangguoqiang on 2017/2/19.
 */
public class DiscussAdapterTest {
    DiscussAdapter adapter = new DiscussAdapter();
    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void getType() throws Exception {

    }

    @Test
    public void parse() throws Exception {
        String test = "1. 及时（）\n答案：对\n(2) haha()\n答案：错";
//        System.out.println("---->>"+test);
        ArrayList<DiscussQuestion> results = adapter.parse(test);
        System.out.println("--->>size"+results.size());
        for (DiscussQuestion tmp: results){
            System.out.println("---->>"+tmp.getId());
            System.out.println("---->>"+tmp.getBody());
            System.out.println("---->>"+tmp.getAnswer());
        }
    }

}