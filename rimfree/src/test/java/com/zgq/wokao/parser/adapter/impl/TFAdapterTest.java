package com.zgq.wokaofree.parser.adapter.impl;

import com.zgq.wokaofree.model.paper.question.impl.TFQuestion;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

/**
 * Created by zhangguoqiang on 2017/2/19.
 */
public class TFAdapterTest {
    TFAdapter adapter  = new TFAdapter();
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
        String test = "1. 及时（）\niderhang\n答案：对\n(2) haha()\n答案：错";
//        System.out.println("---->>"+test);
        ArrayList<TFQuestion> results = adapter.parse(test);
        System.out.println("--->>size"+results.size());
        for (TFQuestion tmp: results){
//            System.out.println("---->>"+tmp.getQstId());
//            System.out.println("---->>"+tmp.getBody());
//            System.out.println("---->>"+tmp.getAnswer());
        }
    }
}