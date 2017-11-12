package com.zgq.rim.recyclerviewhelper.entity;

/**
 * Created by wangyancong on 2017/8/23.
 */

public abstract class SectionEntity<T> {
    public boolean isHeader;
    public T t;
    public String header;

    public SectionEntity(boolean isHeader, String header) {
        this.isHeader = isHeader;
        this.header = header;
        this.t = null;
    }

    public SectionEntity(T t) {
        this.isHeader = false;
        this.header = null;
        this.t = t;
    }
}
