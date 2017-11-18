package com.zgq.wokaofree.parser.context.item;

/**
 * Created by zgq on 2017/2/20.
 */

public enum PaperItemType {
    title(1), author(2), topic(3), other(4);
    private int type;

    private PaperItemType(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }
}
