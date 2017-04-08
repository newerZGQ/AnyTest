package com.zgq.wokao.ui.widget.exprecyclerview;

/**
 * Created by zgq on 2017/4/3.
 */

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.zgq.wokao.Util.ContextUtil;
import com.zgq.wokao.Util.DensityUtil;
import com.zgq.wokao.ui.adapter.HomePaperAdapter;

public class ExpandableViewHoldersUtil {
    private static final String TAG = ExpandableViewHoldersUtil.class.getSimpleName();

    private static final int slipDistance = 180;
    private static final float itemHeightInDp = 120f;
    private static final int duration = 200;

    public static void openH(final HomePaperAdapter.MyViewHolder holder, final View expandView, final boolean animate) {
        if (animate && expandView != null) {
            ValueAnimator animator = ValueAnimator.ofInt(0, slipDistance).setDuration(duration);
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    final ViewGroup.LayoutParams lp = holder.itemView.getLayoutParams();
                    expandView.setTranslationY(Float.valueOf("" + valueAnimator.getAnimatedValue()));
                    lp.height = expandView.getMeasuredHeight() + (int) valueAnimator.getAnimatedValue();
                    holder.itemView.setLayoutParams(lp);
                }
            });
            animator.start();
        } else {
            expandView.setTranslationY(slipDistance);
            final ViewGroup.LayoutParams lp = holder.itemView.getLayoutParams();
            lp.height = slipDistance + DensityUtil.dip2px(ContextUtil.getContext(), itemHeightInDp);
            holder.itemView.setLayoutParams(lp);
        }
    }

    public static void closeH(final HomePaperAdapter.MyViewHolder holder, final View expandView, final boolean animate) {
        if (animate) {
            ValueAnimator animator = ValueAnimator.ofInt(slipDistance, 0).setDuration(duration);
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    final ViewGroup.LayoutParams lp = holder.itemView.getLayoutParams();
                    lp.height = expandView.getMeasuredHeight() + (int) valueAnimator.getAnimatedValue();
                    holder.itemView.setLayoutParams(lp);
                    expandView.setTranslationY(Float.valueOf("" + valueAnimator.getAnimatedValue()));
                }
            });
            animator.start();
        } else {
            expandView.setTranslationY(0f);
            final ViewGroup.LayoutParams lp = holder.itemView.getLayoutParams();
            lp.height = DensityUtil.dip2px(ContextUtil.getContext(), itemHeightInDp);
            holder.itemView.setLayoutParams(lp);
        }
    }

    public static interface Expandable {
        public View getExpandView();
    }

    public static class KeepOneH<VH extends RecyclerView.ViewHolder & Expandable> {
        private int _opened = -1;
        private HomePaperAdapter.MyViewHolder previousHolder;

        public void bind(HomePaperAdapter.MyViewHolder holder, int pos) {
            if (pos == _opened) {
                Log.d("------>>bind",""+pos+" open");
                ExpandableViewHoldersUtil.openH(holder, holder.getExpandView(), false);
            } else {
                Log.d("------>>bind",""+pos+" close");
                ExpandableViewHoldersUtil.closeH(holder, holder.getExpandView(), false);
            }
        }

        @SuppressWarnings("unchecked")
        public void toggle(HomePaperAdapter.MyViewHolder holder) {
            if (_opened == holder.getPosition()) {
                _opened = -1;
                ExpandableViewHoldersUtil.closeH(holder, holder.getExpandView(), true);
            } else {
                int previous = _opened;
                _opened = holder.getPosition();
                ExpandableViewHoldersUtil.openH(holder, holder.getExpandView(), true);
                Log.d("------>>","find pos " +previous+" to close");
                final HomePaperAdapter.MyViewHolder oldHolder = (HomePaperAdapter.MyViewHolder) ((RecyclerView) holder
                        .itemView.getParent()).findViewHolderForLayoutPosition(previous);
                if (oldHolder != null) {
                    Log.d("------>>",""+oldHolder.getPosition()+" close");
                    ExpandableViewHoldersUtil.closeH(oldHolder, oldHolder.getExpandView(), true);
                }
            }
            previousHolder = holder;
        }
    }

}