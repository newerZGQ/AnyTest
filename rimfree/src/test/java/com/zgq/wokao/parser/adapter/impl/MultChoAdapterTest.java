package com.zgq.wokaofree.parser.adapter.impl;

import com.zgq.wokaofree.model.paper.question.impl.MultChoQuestion;
import com.zgq.wokaofree.model.paper.question.option.Option;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

/**
 * Created by zhangguoqiang on 2017/2/19.
 */
public class MultChoAdapterTest {
    MultChoAdapter adapter = new MultChoAdapter();
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
        String test = "1. 及时（）\n就是derhang\nA 这是A\n" +
                "B 这是B\n" +
                "C 这是C\n" +
                "D 这是D\n答案：A" + "\n(2）. 及时（）\n就是derhang\nA 这是A\n" +
                "B 这是B\n" +
                "C 这是C\n" +
                "D 这是D\n答案：A";
//        System.out.println("---->>"+test);
        ArrayList<MultChoQuestion> results = adapter.parse(test);
        System.out.println("--->>size"+results.size());
        for (MultChoQuestion tmp: results){
//            System.out.println("---->>id "+tmp.getQstId());
//            System.out.println("---->>body "+tmp.getBody());
//            for (Option option : tmp.getOptionList()){
//                System.out.println("---->>option "+ option.getOption());
//            }
//            System.out.println("---->>answer "+tmp.getAnswer());
        }
    }

}