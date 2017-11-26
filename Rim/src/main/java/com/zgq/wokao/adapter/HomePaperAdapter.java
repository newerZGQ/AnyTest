package com.zgq.wokao.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zgq.wokao.R;
import com.zgq.wokao.entity.paper.NormalExamPaper;
import com.zgq.wokao.entity.paper.info.ExamPaperInfo;
import com.zgq.wokao.entity.paper.question.QuestionType;
import com.zgq.wokao.widget.exprecyclerview.KeepOneH;

import java.util.ArrayList;
import java.util.List;

public class HomePaperAdapter extends RecyclerView.Adapter {
    private List<NormalExamPaper> papers;
    private Context context;
    private PaperAdapterListener listener;
    private KeepOneH keepOne = new KeepOneH();

    public HomePaperAdapter(List<NormalExamPaper> paperInfos, PaperAdapterListener listener) {
        this.listener = listener;
        this.papers = paperInfos;
    }

    public void replaceData(List<NormalExamPaper> papers) {
        this.papers = papers;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (papers == null || papers.size() == 0) return;
        final int positionTmp = position;
        final MyViewHolder holder1 = (MyViewHolder) holder;
        final ExamPaperInfo info = papers.get(position).getPaperInfo();
        if (info.getSchedule().isInSked()) {
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
        ArrayList<QuestionType> qstList = new ArrayList<>();
        if (papers.get(position).getFillInQuestions().size() != 0){
            qstList.add(QuestionType.FILLIN);
        }
        if (papers.get(position).getTfQuestions().size() != 0){
            qstList.add(QuestionType.TF);
        }
        if (papers.get(position).getSglChoQuestions().size() != 0){
            qstList.add(QuestionType.SINGLECHOOSE);
        }
        if (papers.get(position).getMultChoQuestions().size() != 0){
            qstList.add(QuestionType.MUTTICHOOSE);
        }
        if (papers.get(position).getDiscussQuestions().size() != 0){
            qstList.add(QuestionType.DISCUSS);
        }

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
        if (papers.get(position).getPaperInfo().getSchedule().isInSked()) {
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
        holder1.item.setOnLongClickListener(v -> {
            listener.onItemLongClick(positionTmp, papers.get(positionTmp));
            return true;
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
        return papers.size();
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.context = parent.getContext();
        View view = LayoutInflater.from(context)
                .inflate(R.layout.fragment_papers_list_item, parent, false);
        return new MyViewHolder(view);
    }


    public class MyViewHolder extends RecyclerView.ViewHolder implements
            View.OnClickListener, KeepOneH.Expandable {
        private Button exitBtn;
        private LinearLayout scheduleOpt;
        private Button startBtn;
        private Button deleteBtn;
        private LinearLayout qstTypes;
        private TextView studyStatus;
        private View statusIcon;
        private TextView paperName;
        private RelativeLayout item;
        private RelativeLayout frontLayout;
        private RelativeLayout backLayout;

        private RelativeLayout frontLayoutColorLayout;

        private MyViewHolder(View itemView) {
            super(itemView);
            item =  itemView.findViewById(R.id.list_item);
            exitBtn =  itemView.findViewById(R.id.exit_schedule_btn);
            scheduleOpt =  itemView.findViewById(R.id.schedule_opt_layout);
            startBtn =  itemView.findViewById(R.id.start_schedule_btn);
            deleteBtn =  itemView.findViewById(R.id.delete_paper_btn);
            qstTypes =  itemView.findViewById(R.id.qst_types);
            studyStatus =  itemView.findViewById(R.id.study_status);
            statusIcon = itemView.findViewById(R.id.schedule_status_icon);
            frontLayout =  itemView.findViewById(R.id.front_layout);
            backLayout =  itemView.findViewById(R.id.back_layout);
            frontLayoutColorLayout =  itemView.findViewById(R.id.front_layout_content);
            paperName =  itemView.findViewById(R.id.paper_name);
            frontLayout.setOnClickListener(this);
            exitBtn.setOnClickListener(this);
            startBtn.setOnClickListener(this);
            deleteBtn.setOnClickListener(this);
        }

        private void bind(int pos) {
            keepOne.bind(this, pos, context);
        }

        @Override
        public void onClick(View view) {
            int position = this.getLayoutPosition();
            switch (view.getId()) {
                case R.id.front_layout:
                    keepOne.toggle(this);
                    break;
                case R.id.exit_schedule_btn:
                    listener.onExitClick(position, papers.get(position));
                    break;
                case R.id.delete_paper_btn:
                    listener.onDeleteClick(position, papers.get(position));
                    break;
                case R.id.start_schedule_btn:
                    listener.onStartClick(position, papers.get(position));
                    break;
            }
        }

        @Override
        public View getExpandView() {
            return backLayout;
        }
    }

    public interface PaperAdapterListener {
        void onItemClick(int position, NormalExamPaper paper);

        void onItemLongClick(int position, NormalExamPaper paper);

        void onDeleteClick(int position, NormalExamPaper paper);

        void onExitClick(int position, NormalExamPaper paper);

        void onStartClick(int position, NormalExamPaper paper);
    }

}
