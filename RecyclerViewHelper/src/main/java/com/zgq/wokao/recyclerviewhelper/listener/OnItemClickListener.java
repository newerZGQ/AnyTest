package com.zgq.wokao.recyclerviewhelper.listener;

import android.view.View;

/**
 * Created by wangyancong on 2017/8/23.
 * Item点击监听
 */

public interface OnItemClickListener {
    /**
     * item click
     *
     * @param view
     * @param position
     */
    public void onItemClick(View view, int position);
}
