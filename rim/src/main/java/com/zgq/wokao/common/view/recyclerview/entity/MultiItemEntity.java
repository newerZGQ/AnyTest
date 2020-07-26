package com.zgq.wokao.common.view.recyclerview.entity;

import java.io.Serializable;

/**
 * Created by wangyancong on 2017/8/23.
 */

public abstract class MultiItemEntity implements Serializable {
    protected int itemType;

    public MultiItemEntity(int itemType) {
        this.itemType = itemType;
    }

    public int getItemType() {
        return itemType;
    }

    public void setItemType(int itemType) {
        this.itemType = itemType;
    }
}
