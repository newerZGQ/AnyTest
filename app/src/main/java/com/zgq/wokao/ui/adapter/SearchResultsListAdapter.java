package com.zgq.wokao.ui.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.TextView;

import com.arlib.floatingsearchview.util.Util;
import com.zgq.wokao.R;
import com.zgq.wokao.model.search.SearchInfoItem;
import com.zgq.wokao.model.search.SearchQstItem;
import com.zgq.wokao.model.search.Searchable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zgq on 2017/2/11.
 */

public class SearchResultsListAdapter extends RecyclerView.Adapter<SearchResultsListAdapter.ViewHolder> {
    private static final String TAG = "SearchListAdapter";


    private List<Searchable> mDataSet = new ArrayList<>();

    private int mLastAnimatedItemPosition = -1;

    public interface OnItemClickListener{
//        void onClick(ColorWrapper colorWrapper);
    }

    private OnItemClickListener mItemsOnClickListener;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView mColorName;
        public final TextView mColorValue;
        public final View mTextContainer;

        public ViewHolder(View view) {
            super(view);
            mColorName = (TextView) view.findViewById(R.id.color_name);
            mColorValue = (TextView) view.findViewById(R.id.color_value);
            mTextContainer = view.findViewById(R.id.text_container);
        }
    }

    public void swapData(List<Searchable> mNewDataSet) {
        mDataSet = mNewDataSet;
        notifyDataSetChanged();
    }

    public void setItemsOnClickListener(OnItemClickListener onClickListener){
        this.mItemsOnClickListener = onClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.search_results_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        Searchable tmp = mDataSet.get(position);
        if (tmp instanceof SearchInfoItem){
//            Log.d(TAG,"SearchInfoItem");
            SearchInfoItem infoItem = (SearchInfoItem)tmp;
            holder.mColorName.setText(infoItem.getInfo().getAuthor());
        }
        if (tmp instanceof SearchQstItem){
            Log.d(TAG,"SearchQstItem");
            SearchQstItem qstItem = (SearchQstItem)tmp;
            holder.mColorName.setText(qstItem.getQst().getBody());
        }
//        Log.d(TAG,"bindview");

//        holder.mColorName.setText(colorSuggestion.getName());
//        holder.mColorValue.setText(colorSuggestion.getHex());
//
//        int color = Color.parseColor(colorSuggestion.getHex());
//        holder.mColorName.setTextColor(color);
//        holder.mColorValue.setTextColor(color);
//
//        if(mLastAnimatedItemPosition < position){
//            animateItem(holder.itemView);
//            mLastAnimatedItemPosition = position;
//        }
//
//        if(mItemsOnClickListener != null){
//            holder.itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    mItemsOnClickListener.onClick(mDataSet.get(position));
//                }
//            });
//        }
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

    private void animateItem(View view) {
        view.setTranslationY(Util.getScreenHeight((Activity) view.getContext()));
        view.animate()
                .translationY(0)
                .setInterpolator(new DecelerateInterpolator(3.f))
                .setDuration(700)
                .start();
    }
}

