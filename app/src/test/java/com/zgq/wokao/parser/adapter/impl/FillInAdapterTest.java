package com.zgq.wokao.parser.adapter.impl;

import com.zgq.wokao.model.paper.question.impl.FillInQuestion;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

/**
 * Created by zhangguoqiang on 2017/2/19.
 */
public class FillInAdapterTest {
    FillInAdapter adapter = new FillInAdapter();
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
        String test = "1. 及时（）\n答案：就是\n(2) haha()\n答案：xiaoshenem";
//        System.out.println("---->>"+test);
        ArrayList<FillInQuestion> results = adapter.parse(test);
        System.out.println("--->>size"+results.size());
        for (FillInQuestion tmp: results){
//            System.out.println("---->>"+tmp.getQstId());
//            System.out.println("---->>"+tmp.getBody());
//            System.out.println("---->>"+tmp.getAnswer());
        }
    }

}