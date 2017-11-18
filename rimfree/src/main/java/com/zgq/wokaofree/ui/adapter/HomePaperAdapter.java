package com.zgq.wokaofree.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zgq.wokaofree.R;
import com.zgq.wokaofree.Util.ContextUtil;
import com.zgq.wokaofree.action.paper.impl.PaperAction;
import com.zgq.wokaofree.model.paper.QuestionType;
import com.zgq.wokaofree.model.paper.info.IPaperInfo;
import com.zgq.wokaofree.ui.widget.exprecyclerview.ExpandableViewHoldersUtil;

import java.util.ArrayList;

/**
 * Created by zgq on 2017/2/24.
 */

public class HomePaperAdapter extends RecyclerView.Adapter {
    private android.content.Context context = ContextUtil.getContext();
    private ArrayList<IPaperInfo> paperInfos = null;

    private PaperAdapterListener listener;
    ExpandableViewHoldersUtil.KeepOneH<MyViewHolder> keepOne = new ExpandableViewHoldersUtil.KeepOneH<MyViewHolder>();

    public HomePaperAdapter(ArrayList<IPaperInfo> paperInfos, PaperAdapterListener listener) {
        this.listener = listener;
        this.paperInfos = paperInfos;
    }

    public HomePaperAdapter setData(ArrayList<IPaperInfo> paperInfos) {
        this.paperInfos = paperInfos;
        return this;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (paperInfos == null || paperInfos.size() == 0) return;

        final int positionTmp = position;

        final MyViewHolder holder1 = (MyViewHolder) holder;
        final IPaperInfo info = paperInfos.get(position);
        if (info.isInSchedule()) {
            ((MyViewHolder) holder).frontLayoutColorLayout.setBackgroundColor(context.getResources()
                    .getColor(R.color.color_accuracy_circle_2_hard));
        } else {
            ((MyViewHolder) holder).frontLayoutColorLayout.setBackgroundColor(context.getResources()
                    .getColor(R.color.color_accuracy_circle_1_hard));
        }

        //title
        if (info.getTitle() != null) {
            holder1.paperName.setText(info.getTitle());
        }

        //设置问题类型
        ArrayList<QuestionType> qstList = paperInfos.get(position).getQuestionTypes();
        LinearLayout qstTypes = ((MyViewHolder) holder).qstTypes;
        for (int i = 0; i < qstTypes.getChildCount(); i++) {
            if (i < qstList.size()) {
                qstTypes.getChildAt(i).setVisibility(View.VISIBLE);
                TextView qstType = (TextView)qstTypes.getChildAt(i);
                switch (qstList.get(i)){
                    case FILLIN:
                        qstType.setText(context.getResources().getString(R.string.fillin_question_short));
                        break;
                    case TF:
                        qstType.setText(context.getResources().getString(R.string.tf_question_short));
                        break;
                    case SINGLECHOOSE:
                        qstType.setText(context.getResources().getString(R.string.sglcho_question_short));
                        break;
                    case MUTTICHOOSE:
                        qstType.setText(context.getResources().getString(R.string.multicho_question_short));
                        break;
                    case DISCUSS:
                        qstType.setText(context.getResources().getString(R.string.discus_question_short));
                        break;
                    default:
                        break;
                }
            } else {
                qstTypes.getChildAt(i).setVisibility(View.INVISIBLE);
            }
        }

        //根据学习状态设置View
        if (paperInfos.get(position).isInSchedule()) {
            ((MyViewHolder) holder).statusIcon.setVisibility(View.VISIBLE);
            ((MyViewHolder) holder).exitBtn.setVisibility(View.VISIBLE);
            ((MyViewHolder) holder).studyStatus.setVisibility(View.VISIBLE);
            ((MyViewHolder) holder).scheduleOpt.setVisibility(View.GONE);
        } else {
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

    private RecyclerView.Adapter getMyAdapter() {
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

        public RelativeLayout frontLayoutColorLayout;

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
            frontLayoutColorLayout = (RelativeLayout) itemView.findViewById(R.id.front_layout_content);
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
                    listener.onExitClick(position, paperInfos.get(position));
                    break;
                case R.id.delete_paper_btn:
                    listener.onDeleteClick(position, paperInfos.get(position));
                    break;
                case R.id.start_schedule_btn:
                    listener.onStartClick(position, paperInfos.get(position));
                    break;
            }
        }

        @Override
        public View getExpandView() {
            return backLayout;
        }
    }

    public interface PaperAdapterListener {
        void onItemClick(int position, IPaperInfo info);

        void onItemLongClick(int position, IPaperInfo info);

        void onDeleteClick(int position, IPaperInfo info);

        void onExitClick(int position, IPaperInfo info);

        void onStartClick(int position, IPaperInfo info);
    }

}