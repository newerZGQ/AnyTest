package com.zgq.wokao.adapter;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by zgq on 16-7-17.
 */
public class TFQuestionAdapterTest {
    private TFQuestionAdapter adapter;

    @Before
    public void setUp() throws Exception {
        adapter = new TFQuestionAdapter(null,null);
    }

    @After
    public void tearDown() throws Exception {
        adapter.initData();
    }
}