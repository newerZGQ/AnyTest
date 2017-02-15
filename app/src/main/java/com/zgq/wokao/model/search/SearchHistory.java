package com.zgq.wokao.model.search;

import io.realm.RealmObject;

/**
 * Created by zgq on 2017/2/15.
 */

public class SearchHistory extends RealmObject implements Searchable{
    private String content;
    private String date;
    private int count;


    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
