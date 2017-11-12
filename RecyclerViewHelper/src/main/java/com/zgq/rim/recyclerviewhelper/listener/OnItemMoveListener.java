package com.zgq.rim.recyclerviewhelper.listener;

/**
 * Created by wangyancong on 2017/8/23.
 * Item 移动监听
 */

public interface OnItemMoveListener {
    /**
     * Item 移动
     *
     * @param fromPosition 初始位置
     * @param toPosition   移动位置
     */
    void onItemMove(int fromPosition, int toPosition);
}
