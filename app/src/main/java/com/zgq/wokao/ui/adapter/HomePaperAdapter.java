package com.zgq.wokao.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zgq.wokao.R;
import com.zgq.wokao.Util.ContextUtil;
import com.zgq.wokao.Util.DrawableUtil;
import com.zgq.wokao.model.paper.info.IPaperInfo;
import com.zgq.wokao.ui.widget.RotateTextView;

import java.util.ArrayList;

/**
 * Created by zgq on 2017/2/24.
 */

public class HomePaperAdapter extends RecyclerView.Adapter {
    //recyclerView adapter
    private android.content.Context context = ContextUtil.getContext();
    private ArrayList<IPaperInfo> paperInfos = null;

    private PaperAdapterListener listener;

    public HomePaperAdapter(ArrayList<IPaperInfo> paperInfos, PaperAdapterListener listener) {
        this.listener = listener;
        this.paperInfos = paperInfos;
    }

    public HomePaperAdapter setData(ArrayList<IPaperInfo> paperInfos){
        this.paperInfos = paperInfos;
        return this;
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        if (paperInfos.size() == 0 || paperInfos == null) return;

        final int positionTmp = position;

        final PaperInfoViewHolder holder1 = (PaperInfoViewHolder) holder;
        final IPaperInfo info = paperInfos.get(position);
        //title 以及label的初始显示
        if (info.getTitle() != null) {
            holder1.paperName.setText(info.getTitle());
        }
//        if (!isItemSelectedList.get(position)) {
//            holder1.paperLabel.setCurrentSide(RotateTextView.UPSIDE);
//            holder1.paperLabel.setText(info.getTitle().substring(0, 1));
//            holder1.paperLabel.setBackground(context.getDrawable(colorlabelList.get(position)));
//            holder1.item.setBackgroundColor(context.getColor(R.color.colorWhite));
//        } else {
//            holder1.paperLabel.setCurrentSide(RotateTextView.DOWNSIDE);
//            holder1.paperLabel.setText("√");
//            holder1.paperLabel.setBackground(context.getDrawable(R.drawable.circle_background_downside));
//            holder1.item.setBackgroundColor(context.getColor(R.color.colorRecyclerViewItemSelectedBackGround));
//        }
        //作者信息初始显示
        if (info.getAuthor() != null) {
            holder1.paperAuthorAndDate.setText(getAuthorAndData(info));
        }
        //是否收藏信息显示
        if (!info.isStared()) {
            holder1.paperStar.setBackground(context.getDrawable(R.drawable.inactive_star));
        } else {
            holder1.paperStar.setBackground(context.getDrawable(R.drawable.active_star));
        }
        //paperlabel的单击事件
        holder1.paperLabel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                holder1.paperLabel.changeSide();
//                if ((holder1.paperLabel).getCurrentSide() == RotateTextView.UPSIDE) {
//                    isItemSelectedList.set(position, false);
//                } else {
//                    isItemSelectedList.set(position, true);
//                }
            }
        });
        //recyclerView item单击事件
        holder1.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(positionTmp);
//                Intent intent = new Intent();
//                intent.setClass(getActivity(), QuestionsListActivity.class);
//                intent.putExtra("paperTitle", info.getTitle());
//                intent.putExtra("paperAuthor", info.getAuthor());
//                startActivity(intent);
            }
        });
        //recyclerView item长按事件
        holder1.item.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                listener.onItemLongClick(positionTmp);
                return true;
            }
        });

    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    @Override
    public int getItemCount() {
        return paperInfos.size();
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.activity_main_recyclerview_item, parent,false);
        return new PaperInfoViewHolder(view);
    }

    private String getAuthorAndData(IPaperInfo info) {
        String author = info.getAuthor();
        String lastDate = info.getLastStudyDate();
        if (author == null || author.equals("")) author = "      ";
        if (lastDate == null || lastDate.equals("")) {
            lastDate = "未学习";
        } else {
        }
        return author;
    }


    public class PaperInfoViewHolder extends RecyclerView.ViewHolder {
        public TextView paperName;
        public RotateTextView paperLabel;
        public TextView paperAuthorAndDate;
        public RelativeLayout item;
        public TextView paperStar;
        public TextView groupTitle;

        public PaperInfoViewHolder(View itemView) {
            super(itemView);
            item = (RelativeLayout) itemView.findViewById(R.id.list_item);
            paperName = (TextView) itemView.findViewById(R.id.paper_name);
            paperLabel = (RotateTextView) itemView.findViewById(R.id.question_label);
            paperLabel.setClickable(true);
            paperAuthorAndDate = (TextView) itemView.findViewById(R.id.auther);
            paperStar = (TextView) itemView.findViewById(R.id.activity_main_paper_star_tv);
            groupTitle = (TextView) itemView.findViewById(R.id.group_title);
        }
    }

    public interface PaperAdapterListener{
        public void onItemClick(int position);
        public void onItemLongClick(int position);
    }

}
