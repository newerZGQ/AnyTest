package com.zgq.wokao.parser.context.item;

/**
 * Created by zgq on 2017/2/20.
 */

public class PaperItem implements ParserItem {

    private int type;

    public PaperItem(int type){
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

}
