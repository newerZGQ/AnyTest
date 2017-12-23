package com.zgq.wokao.parser.context.item;

public class PaperItem implements ParserItem {

    private PaperItemType type;

    public PaperItem(PaperItemType type) {
        this.type = type;
    }

    public PaperItemType getType() {
        return type;
    }

    public void setType(PaperItemType type) {
        this.type = type;
    }

}
