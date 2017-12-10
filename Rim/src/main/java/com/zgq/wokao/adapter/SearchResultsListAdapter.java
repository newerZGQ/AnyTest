package com.zgq.wokao.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zgq.wokao.R;
import com.zgq.wokao.module.search.entity.SearchInfoItem;
import com.zgq.wokao.module.search.entity.SearchQuestionItem;
import com.zgq.wokao.module.search.entity.Searchable;

import java.util.ArrayList;
import java.util.List;

public class SearchResultsListAdapter extends RecyclerView.Adapter<SearchResultsListAdapter.ViewHolder> {
    private static final String TAG = "SearchListAdapter";

    private List<Searchable> mDataSet = new ArrayList<>();

    public interface OnItemClickListener {
        void onClick(Searchable item);
    }

    private OnItemClickListener mItemsOnClickListener;

    public class ViewHolder extends RecyclerView.ViewHolder{
        final TextView itemType;
        final TextView itemContent;
        final View textContainer;
        final TextView itemTip;

        ViewHolder(View view) {
            super(view);
            itemType = view.findViewById(R.id.item_type);
            itemContent = view.findViewById(R.id.item_content);
            textContainer = view.findViewById(R.id.text_container);
            itemTip = view.findViewById(R.id.item_tip);
        }
    }

    public void replaceData(List<Searchable> mNewDataSet) {
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
        if (tmp instanceof SearchQuestionItem) {
            SearchQuestionItem qstItem = (SearchQuestionItem) tmp;
            holder.itemType.setText(qstItem.getQuestionType().getName());
            //holder.itemContent.setText(qstItem.getQuestion().getBody().getContent());
            //holder.itemTip.setText(qstItem.getInfo().getTitle());
        }
        holder.textContainer.setOnClickListener(view -> mItemsOnClickListener.onClick(mDataSet.get(position)));
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

}

