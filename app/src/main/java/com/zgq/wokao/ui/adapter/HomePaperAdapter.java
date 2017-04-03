package com.zgq.wokao.ui.adapter;

import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zgq.wokao.R;
import com.zgq.wokao.Util.ContextUtil;
import com.zgq.wokao.Util.DateUtil;
import com.zgq.wokao.Util.RandomUtil;
import com.zgq.wokao.model.paper.info.IPaperInfo;
import com.zgq.wokao.ui.widget.exprecyclerview.ExpandableViewHoldersUtil;

import java.text.ParseException;
import java.util.ArrayList;

/**
 * Created by zgq on 2017/2/24.
 */

public class HomePaperAdapter extends RecyclerView.Adapter {
    //recyclerView adapter
    private android.content.Context context = ContextUtil.getContext();
    private ArrayList<IPaperInfo> paperInfos = null;

    private PaperAdapterListener listener;
    private int[] backgrounds = new int[4];
    private Drawable[] backgrounds_1 = new Drawable[4];
    private int lastColor = 0;

    ExpandableViewHoldersUtil.KeepOneH<MyViewHolder> keepOne = new ExpandableViewHoldersUtil.KeepOneH<MyViewHolder>();

    public HomePaperAdapter(ArrayList<IPaperInfo> paperInfos, PaperAdapterListener listener) {
        this.listener = listener;
        this.paperInfos = paperInfos;
    }

    public HomePaperAdapter setData(ArrayList<IPaperInfo> paperInfos){
        this.paperInfos = paperInfos;
        setBackgrounds();
        return this;
    }

    private void setBackgrounds(){
        backgrounds[0] = R.drawable.rectangle_blue;
        backgrounds[1] = R.drawable.rectangle_red;
        backgrounds[2] = R.drawable.rectangle_yellow;
        backgrounds[3] = R.drawable.rectangle_gree;

        backgrounds_1[0] = context.getResources().getDrawable(R.drawable.rectangle_blue);
        backgrounds_1[1] = context.getResources().getDrawable(R.drawable.rectangle_red);
        backgrounds_1[2] = context.getResources().getDrawable(R.drawable.rectangle_yellow);
        backgrounds_1[3] = context.getResources().getDrawable(R.drawable.rectangle_gree);
    }

    private int getNewBackgroundId(){
        int nextBack = RandomUtil.getRandom(0,4);
        if (nextBack == lastColor){
            if (nextBack == 3){
                lastColor = 0;
                return 0;
            }
            lastColor = ++nextBack;
            return nextBack;
        }
        lastColor = nextBack;
        return nextBack;
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (paperInfos == null || paperInfos.size() == 0 ) return;

        final int positionTmp = position;

        final MyViewHolder holder1 = (MyViewHolder) holder;
        final IPaperInfo info = paperInfos.get(position);
        //front background
        int backIndex = getNewBackgroundId();
        switch (backIndex){
            case 0:
                ((MyViewHolder) holder).frontLayout.setBackground(context.getResources()
                        .getDrawable(R.drawable.rectangle_blue));
                break;
            case 1:
                ((MyViewHolder) holder).frontLayout.setBackground(context.getResources()
                        .getDrawable(R.drawable.rectangle_red));
                break;
            case 2:
                ((MyViewHolder) holder).frontLayout.setBackground(context.getResources()
                        .getDrawable(R.drawable.rectangle_yellow));
                break;
            case 3:
                ((MyViewHolder) holder).frontLayout.setBackground(context.getResources()
                        .getDrawable(R.drawable.rectangle_gree));
                break;
        }

        //title
        if (info.getTitle() != null) {
            holder1.paperName.setText(info.getTitle());
        }

        //recyclerView item长按事件
        holder1.item.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                listener.onItemLongClick(positionTmp,paperInfos.get(positionTmp));
                return true;
            }
        });

        ((MyViewHolder) holder).bind(position);

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
        View view = LayoutInflater.from(context).inflate(R.layout.fragment_papers_recyclerview_item, parent,false);
        return new MyViewHolder(view);
    }


    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, ExpandableViewHoldersUtil.Expandable {

        public boolean isExpanded = false;
        public TextView paperName;
        public TextView createDate;
        public RelativeLayout item;
        public RelativeLayout frontLayout;
        public RelativeLayout backLayout;
        //public Button addSchedule;

        public MyViewHolder(View itemView) {
            super(itemView);
            item = (RelativeLayout) itemView.findViewById(R.id.list_item);
            frontLayout = (RelativeLayout) itemView.findViewById(R.id.front_layout);
            frontLayout.setOnClickListener(this);
            backLayout = (RelativeLayout) itemView.findViewById(R.id.back_layout);
            paperName = (TextView) itemView.findViewById(R.id.paper_name);
        }

        public void bind(int pos){
            keepOne.bind(this,pos);
        }

        @Override
        public void onClick(View view) {
            keepOne.toggle(this);
        }

        @Override
        public View getExpandView() {
            return backLayout;
        }

        public boolean isExpanded() {
            return isExpanded;
        }

        public void setExpanded(boolean expanded) {
            isExpanded = expanded;
        }
    }

    public interface PaperAdapterListener{
        public void onItemClick(int position, IPaperInfo info);
        public void onItemLongClick(int position, IPaperInfo info);
    }

}
