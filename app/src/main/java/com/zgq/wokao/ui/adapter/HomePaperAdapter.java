package com.zgq.wokao.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zgq.wokao.R;
import com.zgq.wokao.Util.ContextUtil;
import com.zgq.wokao.Util.DateUtil;
import com.zgq.wokao.model.paper.info.IPaperInfo;

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

    public HomePaperAdapter(ArrayList<IPaperInfo> paperInfos, PaperAdapterListener listener) {
        this.listener = listener;
        this.paperInfos = paperInfos;
    }

    public HomePaperAdapter setData(ArrayList<IPaperInfo> paperInfos){
        this.paperInfos = paperInfos;
        return this;
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (paperInfos.size() == 0 || paperInfos == null) return;

        final int positionTmp = position;

        final MyViewHolder holder1 = (MyViewHolder) holder;
        final IPaperInfo info = paperInfos.get(position);
        //title
        if (info.getTitle() != null) {
            holder1.paperName.setText(info.getTitle());
        }
        if (info.getCreateDate() != null && !info.getCreateDate().equals("")){
            try {
                holder1.createDate.setText(DateUtil.getYYYY_MM_DD(info.getCreateDate()));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        //recyclerView item单击事件
        holder1.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(positionTmp, paperInfos.get(positionTmp));
            }
        });
        //recyclerView item长按事件
        holder1.item.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                listener.onItemLongClick(positionTmp,paperInfos.get(positionTmp));
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
        View view = LayoutInflater.from(context).inflate(R.layout.fragment_papers_recyclerview_item, parent,false);
        return new MyViewHolder(view);
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView paperName;
        public TextView createDate;
        public LinearLayout item;
        public Button addSchedule;

        public MyViewHolder(View itemView) {
            super(itemView);
            item = (LinearLayout) itemView.findViewById(R.id.list_item);
            paperName = (TextView) itemView.findViewById(R.id.paper_name);
            createDate = (TextView) itemView.findViewById(R.id.create_date);
            addSchedule = (Button) itemView.findViewById(R.id.add_schedule);
        }
    }

    public interface PaperAdapterListener{
        public void onItemClick(int position, IPaperInfo info);
        public void onItemLongClick(int position, IPaperInfo info);
    }

}
