package com.zgq.wokao.parser.adapter;

import com.zgq.wokao.model.paper.QuestionType;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Created by zhangguoqiang on 2017/2/19.
 */
public class BaseAdapterTest {
    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void trimNum() throws Exception {
        BaseAdapter adapter = new BaseAdapter() {
            @Override
            public QuestionType getType() {
                return null;
            }

            @Override
            public ArrayList parse(String resource) {
                return null;
            }
        };
        String ss = adapter.trimNum("45 及时答复都是开放啦");
        System.out.println("---->"+ss);
    }

    @Test
    public void isQstNumber() throws Exception {

    }

}