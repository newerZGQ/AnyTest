package com.zgq.wokaofree.executor;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by zgq on 2017/2/20.
 */
public class ParserThreadTest {
    ParserThread thread = new ParserThread("/Users/zgq/AndroidStudioProjects/wokaofree_test_files/test.txt");
    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void run() throws Exception {
        thread.run();
    }

}