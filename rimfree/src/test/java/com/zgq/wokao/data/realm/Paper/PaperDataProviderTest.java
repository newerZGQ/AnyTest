package com.zgq.wokaofree.data.realm.Paper;

import com.zgq.wokaofree.data.realm.Paper.impl.PaperDaoImpl;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by zgq on 2017/2/11.
 */
public class PaperDataProviderTest {

    PaperDaoImpl provider = PaperDaoImpl.getInstance();
    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void getInstance() throws Exception {

    }

    @Test
    public void save() throws Exception {

    }

    @Test
    public void getAllPaper() throws Exception {

    }

    @Test
    public void getAllPaperInfo() throws Exception {

    }

    @Test
    public void search() throws Exception {
        provider.search("政治");
    }

    @Test
    public void examPaperIsExist() throws Exception {

    }

    @Test
    public void parseTxt2Realm() throws Exception {

    }

}