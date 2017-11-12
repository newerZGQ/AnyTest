package com.zgq.rim.recyclerviewhelper.listener;

import android.view.View;

/**
 * Created by wangyancong on 2017/8/23.
 * Item 长按监听
 */

public interface OnItemLongClickListener {
    /**
     * item long click
     *
     * @param view
     * @param position
     * @return
     */
    public boolean onItemLongClick(View view, int position);
}
