package com.zgq.wokaofree.model.search;

import com.zgq.wokaofree.model.CascadeDeleteable;

import io.realm.RealmObject;

/**
 * Created by zgq on 2017/2/15.
 */

public class SearchHistory extends RealmObject implements Searchable, CascadeDeleteable {
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

    @Override
    public void cascadeDelete() {

    }
}
