package com.zgq.wokao.ui.adapter.ultiadapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.marshalchen.ultimaterecyclerview.UltimateRecyclerviewViewHolder;
import com.marshalchen.ultimaterecyclerview.expanx.SmartItem;
import com.marshalchen.ultimaterecyclerview.expanx.Util.DataUtil;
import com.marshalchen.ultimaterecyclerview.expanx.Util.child;
import com.marshalchen.ultimaterecyclerview.expanx.Util.parent;
import com.marshalchen.ultimaterecyclerview.expanx.customizedAdapter;
import com.zgq.wokao.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zgq on 2017/4/2.
 */

public class ExpCustomAdapter extends customizedAdapter {
    public ExpCustomAdapter(Context context){
        super(context);
    }
    public static List<SmartItem> getPreCodeMenu(String[] a, String[] b, String[] c) {
        List<SmartItem> e = new ArrayList<>();
        e.add(SmartItem.parent("hones", "open", DataUtil.getSmallList(a)));
        e.add(SmartItem.parent("XXX", "open", DataUtil.getSmallList(b)));
        e.add(SmartItem.parent("RIVER", "open", DataUtil.getSmallList(c)));
        return e;
    }


    /**
     * please do work on this id the custom object is true
     *
     * @param parentview the inflated view
     * @return the actual parent holder
     */
    @Override
    protected Category iniCustomParentHolder(View parentview) {
        return new Category(parentview);
    }

    /**
     * please do work on this if the custom object is true
     *
     * @param childview the inflated view
     * @return the actual child holder
     */
    @Override
    protected SubCategory iniCustomChildHolder(View childview) {
        return new SubCategory(childview);
    }

    @Override
    protected int getLayoutResParent() {
        return R.layout.fragment_papers_exp_parent;
    }

    @Override
    protected int getLayoutResChild() {
        return R.layout.fragment_papers_exp_child;
    }

    @Override
    protected List<SmartItem> getChildrenByPath(String path, int depth, int position) {
        return null;
    }


    @Override
    public RecyclerView.ViewHolder newFooterHolder(View view) {
        return new UltimateRecyclerviewViewHolder(view);
    }

    @Override
    public RecyclerView.ViewHolder newHeaderHolder(View view) {
        return new UltimateRecyclerviewViewHolder(view);
    }
}
