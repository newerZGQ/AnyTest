package com.zgq.wokao.widget.exprecyclerview;

import android.animation.ValueAnimator;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.zgq.wokao.util.DensityUtil;
import com.zgq.wokao.adapter.HomePaperAdapter;

public class KeepOneH {
    private int _opened = -1;

    private final int slipDistance = 180;
    private final float itemHeightInDp = 120f;
    private final int duration = 200;

    private HomePaperAdapter.MyViewHolder previousHolder;

    private Context context;

    public void bind(HomePaperAdapter.MyViewHolder holder, int pos, Context context) {
        this.context = context;
        if (pos == _opened) {
            openH(holder, holder.getExpandView(), false);
        } else {
            closeH(holder, holder.getExpandView(), false);
        }
    }

    @SuppressWarnings("unchecked")
    public void toggle(HomePaperAdapter.MyViewHolder holder) {
        if (_opened == holder.getPosition()) {
            _opened = -1;
            closeH(holder, holder.getExpandView(), true);
        } else {
            int previous = _opened;
            _opened = holder.getPosition();
            openH(holder, holder.getExpandView(), true);
            final HomePaperAdapter.MyViewHolder oldHolder = (HomePaperAdapter.MyViewHolder) ((RecyclerView) holder
                    .itemView.getParent()).findViewHolderForLayoutPosition(previous);
            if (oldHolder != null) {
                closeH(oldHolder, oldHolder.getExpandView(), true);
            }
        }
        previousHolder = holder;
    }

    public void openH(final HomePaperAdapter.MyViewHolder holder, final View expandView, final boolean animate) {
        if (animate && expandView != null) {
            ValueAnimator animator = ValueAnimator.ofInt(0, slipDistance).setDuration(duration);
            animator.addUpdateListener(valueAnimator -> {
                final ViewGroup.LayoutParams lp = holder.itemView.getLayoutParams();
                expandView.setTranslationY(Float.valueOf("" + valueAnimator.getAnimatedValue()));
                lp.height = expandView.getMeasuredHeight() + (int) valueAnimator.getAnimatedValue();
                holder.itemView.setLayoutParams(lp);
            });
            animator.start();
        } else {
            expandView.setTranslationY(slipDistance);
            final ViewGroup.LayoutParams lp = holder.itemView.getLayoutParams();
            lp.height = slipDistance + DensityUtil.dip2px(context, itemHeightInDp);
            holder.itemView.setLayoutParams(lp);
        }
    }

    public void closeH(final HomePaperAdapter.MyViewHolder holder, final View expandView, final boolean animate) {
        if (animate) {
            ValueAnimator animator = ValueAnimator.ofInt(slipDistance, 0).setDuration(duration);
            animator.addUpdateListener(valueAnimator -> {
                final ViewGroup.LayoutParams lp = holder.itemView.getLayoutParams();
                lp.height = expandView.getMeasuredHeight() + (int) valueAnimator.getAnimatedValue();
                holder.itemView.setLayoutParams(lp);
                expandView.setTranslationY(Float.valueOf("" + valueAnimator.getAnimatedValue()));
            });
            animator.start();
        } else {
            expandView.setTranslationY(0f);
            final ViewGroup.LayoutParams lp = holder.itemView.getLayoutParams();
            lp.height = DensityUtil.dip2px(context, itemHeightInDp);
            holder.itemView.setLayoutParams(lp);
        }
    }

    public static interface Expandable {
        View getExpandView();
    }
}

