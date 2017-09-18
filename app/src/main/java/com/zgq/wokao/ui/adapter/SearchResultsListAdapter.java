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

    public interface OnItemClickListener {
        void onClick(Searchable item);
    }

    private OnItemClickListener mItemsOnClickListener;

    public class ViewHolder extends RecyclerView.ViewHolder{
        public final TextView itemType;
        public final TextView itemContent;
        public final View textContainer;
        public final TextView itemTip;

        public ViewHolder(View view) {
            super(view);
            itemType = (TextView) view.findViewById(R.id.item_type);
            itemContent = (TextView) view.findViewById(R.id.item_content);
            textContainer = view.findViewById(R.id.text_container);
            itemTip = (TextView) view.findViewById(R.id.item_tip);
        }
    }

    public void swapData(List<Searchable> mNewDataSet) {
        mDataSet = mNewDataSet;
        notifyDataSetChanged();
    }

    public void setItemsOnClickListener(OnItemClickListener onClickListener) {
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
        if (tmp instanceof SearchInfoItem) {
            SearchInfoItem infoItem = (SearchInfoItem) tmp;
            holder.itemType.setText("试卷");
            holder.itemContent.setText(infoItem.getInfo().getTitle());
            holder.itemTip.setText("");
        }
        if (tmp instanceof SearchQstItem) {
            SearchQstItem qstItem = (SearchQstItem) tmp;
            holder.itemType.setText(qstItem.getQstType().getName());
            holder.itemContent.setText(qstItem.getQst().getBody().getContent());
            holder.itemTip.setText(qstItem.getInfo().getTitle());
        }
        holder.textContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG,"click");
                mItemsOnClickListener.onClick(mDataSet.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

}

