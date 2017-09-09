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
import com.zgq.wokao.action.paper.impl.PaperAction;
import com.zgq.wokao.model.paper.QuestionType;
import com.zgq.wokao.model.paper.info.IPaperInfo;
import com.zgq.wokao.ui.widget.exprecyclerview.ExpandableViewHoldersUtil;

import java.util.ArrayList;

import io.realm.RealmList;

/**
 * Created by zgq on 2017/2/24.
 */

public class HomePaperAdapter extends RecyclerView.Adapter {
    //recyclerView adapter
    private android.content.Context context = ContextUtil.getContext();
    private ArrayList<IPaperInfo> paperInfos = null;

    private PaperAdapterListener listener;
    private int lastBackground = 0;
    private PaperAction paperAction = PaperAction.getInstance();

    ExpandableViewHoldersUtil.KeepOneH<MyViewHolder> keepOne = new ExpandableViewHoldersUtil.KeepOneH<MyViewHolder>();

    public HomePaperAdapter(ArrayList<IPaperInfo> paperInfos, PaperAdapterListener listener) {
        this.listener = listener;
        this.paperInfos = paperInfos;
    }

    public HomePaperAdapter setData(ArrayList<IPaperInfo> paperInfos) {
        this.paperInfos = paperInfos;
        return this;
    }

    private int getNewBackgroundId() {
        lastBackground++;
        if (lastBackground == 4){
            lastBackground = 0;
        }
        return lastBackground;
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (paperInfos == null || paperInfos.size() == 0) return;

        final int positionTmp = position;

        final MyViewHolder holder1 = (MyViewHolder) holder;
        final IPaperInfo info = paperInfos.get(position);
        //front background
        int backIndex = getNewBackgroundId();
        if (info.isInSchedule()){
            ((MyViewHolder) holder).frontLayout.setBackground(context.getResources()
                    .getDrawable(R.drawable.rectangle_blue));
        }else{
            ((MyViewHolder) holder).frontLayout.setBackground(context.getResources()
                    .getDrawable(R.drawable.rectangle_red));
        }
//        switch (info.isInSchedule()) {
//            case true:
//                ((MyViewHolder) holder).frontLayout.setBackground(context.getResources()
//                        .getDrawable(R.drawable.rectangle_blue));
//                break;
//            case false:
//                ((MyViewHolder) holder).frontLayout.setBackground(context.getResources()
//                        .getDrawable(R.drawable.rectangle_red));
//                break;
//            case 2:
//                ((MyViewHolder) holder).frontLayout.setBackground(context.getResources()
//                        .getDrawable(R.drawable.rectangle_yellow));
//                break;
//            case 3:
//                ((MyViewHolder) holder).frontLayout.setBackground(context.getResources()
//                        .getDrawable(R.drawable.rectangle_gree));
//                break;
//        }

        //title
        if (info.getTitle() != null) {
            holder1.paperName.setText(info.getTitle());
        }

        //设置问题类型
        ArrayList<QuestionType> qstList = paperInfos.get(position).getQuestionTypes();
        LinearLayout qstTypes = ((MyViewHolder) holder).qstTypes;
        for (int i = 0; i< qstTypes.getChildCount(); i++){
            if (i < qstList.size()) {
                qstTypes.getChildAt(i).setVisibility(View.VISIBLE);
                ((TextView) qstTypes.getChildAt(i)).setText(qstList.get(i).name());
            }else{
                qstTypes.getChildAt(i).setVisibility(View.INVISIBLE);
            }
        }

        //根据学习状态设置View
        if (paperInfos.get(position).isInSchedule()){
            ((MyViewHolder) holder).statusIcon.setVisibility(View.VISIBLE);
            ((MyViewHolder) holder).exitBtn.setVisibility(View.VISIBLE);
            ((MyViewHolder) holder).studyStatus.setVisibility(View.VISIBLE);
            ((MyViewHolder) holder).scheduleOpt.setVisibility(View.GONE);
        }else{
            ((MyViewHolder) holder).statusIcon.setVisibility(View.INVISIBLE);
            ((MyViewHolder) holder).exitBtn.setVisibility(View.GONE);
            ((MyViewHolder) holder).studyStatus.setVisibility(View.INVISIBLE);
            ((MyViewHolder) holder).scheduleOpt.setVisibility(View.VISIBLE);
        }

        //recyclerView item长按事件
        holder1.item.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                listener.onItemLongClick(positionTmp, paperInfos.get(positionTmp));
                return true;
            }
        });
        //item backlayout 动画
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
        View view = LayoutInflater.from(context).inflate(R.layout.fragment_papers_recyclerview_item, parent, false);
        return new MyViewHolder(view);
    }

    private RecyclerView.Adapter getMyAdapter(){
        return this;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, ExpandableViewHoldersUtil.Expandable {
        public Button exitBtn;
        public LinearLayout scheduleOpt;
        public Button startBtn;
        public Button deleteBtn;
        public LinearLayout qstTypes;
        public TextView studyStatus;
        public View statusIcon;
        public TextView paperName;
        public RelativeLayout item;
        public RelativeLayout frontLayout;
        public RelativeLayout backLayout;

        public MyViewHolder(View itemView) {
            super(itemView);
            item = (RelativeLayout) itemView.findViewById(R.id.list_item);
            exitBtn = (Button) itemView.findViewById(R.id.exit_schedule_btn);
            scheduleOpt = (LinearLayout) itemView.findViewById(R.id.schedule_opt_layout);
            startBtn = (Button) itemView.findViewById(R.id.start_schedule_btn);
            deleteBtn = (Button) itemView.findViewById(R.id.delete_paper_btn);
            qstTypes = (LinearLayout) itemView.findViewById(R.id.qst_types);
            studyStatus = (TextView) itemView.findViewById(R.id.study_status);
            statusIcon = itemView.findViewById(R.id.schedule_status_icon);
            frontLayout = (RelativeLayout) itemView.findViewById(R.id.front_layout);
            backLayout = (RelativeLayout) itemView.findViewById(R.id.back_layout);
            paperName = (TextView) itemView.findViewById(R.id.paper_name);
            frontLayout.setOnClickListener(this);
            exitBtn.setOnClickListener(this);
            startBtn.setOnClickListener(this);
            deleteBtn.setOnClickListener(this);
        }

        public void bind(int pos) {
            keepOne.bind(this, pos);
        }

        @Override
        public void onClick(View view) {
            int position = this.getLayoutPosition();
            switch (view.getId()) {
                case R.id.front_layout:
                    keepOne.toggle(this);
                    break;
                case R.id.exit_schedule_btn:
                    listener.onExitClick(position,paperInfos.get(position));
                    break;
                case R.id.delete_paper_btn:
                    listener.onDeleteClick(position,paperInfos.get(position));
                    break;
                case R.id.start_schedule_btn:
                    listener.onStartClick(position,paperInfos.get(position));
                    break;
            }
        }

        @Override
        public View getExpandView() {
            return backLayout;
        }
    }

    public interface PaperAdapterListener {
        public void onItemClick(int position, IPaperInfo info);

        public void onItemLongClick(int position, IPaperInfo info);

        public void onDeleteClick(int position, IPaperInfo info);

        public void onExitClick(int position, IPaperInfo info);

        public void onStartClick(int position, IPaperInfo info);
    }

}
