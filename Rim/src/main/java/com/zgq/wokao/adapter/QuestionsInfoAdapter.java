package com.zgq.wokao.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.zgq.wokao.R;
import com.zgq.wokao.entity.paper.info.ExamPaperInfo;
import com.zgq.wokao.entity.paper.question.QuestionType;

import java.util.List;

import lombok.Builder;
import lombok.Data;

public class QuestionsInfoAdapter extends RecyclerView.Adapter {

    private final int header_type = 0;
    private final int common_type = 1;

    private List<QuestionsInfo> questions;
    private ExamPaperInfo paperInfo;
    private ItemClickListener clickListener;
    private Context context;

    public QuestionsInfoAdapter(List<QuestionsInfo> questions,
                                ExamPaperInfo paperInfo, ItemClickListener clickListener) {
        this.questions = questions;
        this.paperInfo = paperInfo;
        this.clickListener = clickListener;
    }

    public void replaceData(List<QuestionsInfo> questions, ExamPaperInfo paperInfo){
        this.questions = questions;
        this.paperInfo = paperInfo;
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return header_type;
        } else {
            return common_type;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        if (viewType == common_type) {
            View view = LayoutInflater.from(parent.getContext()).
                    inflate(R.layout.question_list_item, null);
            return new CommonViewHolder(view, clickListener);
        } else {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.question_list_header, null);
            return new HeaderViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (position == 0) {
            if (paperInfo.getTitle() != null && !paperInfo.getTitle().equals(""))
                ((HeaderViewHolder) holder).title.setText(paperInfo.getTitle());

            if (paperInfo.getAuthor() != null && !paperInfo.getAuthor().equals(""))
                ((HeaderViewHolder) holder).author.setText(paperInfo.getAuthor());

        } else {
            int realPosition = position-1;

            TextView qstType = ((CommonViewHolder) holder).qstType;
            switch (questions.get(realPosition).getType()){
                case FILLIN:
                    qstType.setText(context.getResources().getString(R.string.fillin_question));
                    break;
                case TF:
                    qstType.setText(context.getResources().getString(R.string.tf_question));
                    break;
                case SINGLECHOOSE:
                    qstType.setText(context.getResources().getString(R.string.sglcho_question));
                    break;
                case MUTTICHOOSE:
                    qstType.setText(context.getResources().getString(R.string.multicho_question));
                    break;
                case DISCUSS:
                    qstType.setText(context.getResources().getString(R.string.discus_question));
                    break;
                default:
                    break;
            }

            ((CommonViewHolder) holder).qstInfo.
                    setText(context.getString(R.string.total) + " " + questions.get(realPosition).getQstCount() +
                            ",   " + context.getString(R.string.star) + " " + questions.get(realPosition).getStarCount());
            ImageView icon = ((CommonViewHolder) holder).titleIcon;
            switch (questions.get(realPosition).getType()){
                case FILLIN:
                    icon.setBackgroundColor(context.getResources().getColor(R.color.colorLime));
                    break;
                case TF:
                    icon.setBackgroundColor(context.getResources().getColor(R.color.colorTeal));
                    break;
                case SINGLECHOOSE:
                    icon.setBackgroundColor(context.getResources().getColor(R.color.colorYellow));
                    break;
                case MUTTICHOOSE:
                    icon.setBackgroundColor(context.getResources().getColor(R.color.colorBlue));
                    break;
                case DISCUSS:
                    icon.setBackgroundColor(context.getResources().getColor(R.color.colorCyan));
                    break;
                default:
                    break;

            }
        }

    }

    @Override
    public int getItemCount() {
        return questions.size() + 1;
    }

    class CommonViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView qstType;
        ImageView titleIcon;
        TextView qstInfo;
        private ItemClickListener clickListener = position -> {

        };

        CommonViewHolder(View itemView, ItemClickListener clickListener) {
            super(itemView);
            this.clickListener = clickListener;
            itemView.setOnClickListener(this);
            qstType = itemView.findViewById(R.id.question_type_text);
            titleIcon = itemView.findViewById(R.id.title_icon);
            qstInfo = itemView.findViewById(R.id.qst_info);
        }

        @Override
        public void onClick(View view) {
            clickListener.onItemClicked(getAdapterPosition()-1);
        }
    }

    class HeaderViewHolder extends RecyclerView.ViewHolder {

        public TextView title;
        public TextView author;

        HeaderViewHolder(View itemView) {
            super(itemView);
            this.title = itemView.findViewById(R.id.paper_title);
            this.author = itemView.findViewById(R.id.paper_author);
        }
    }

    public interface ItemClickListener {
        void onItemClicked(int position);
    }

    @Data
    @Builder
    public static class QuestionsInfo{
        private String paperId;
        @Builder.Default
        private QuestionType type = QuestionType.FILLIN;
        @Builder.Default
        private int qstCount = 0;
        @Builder.Default
        private int starCount = 0;
    }
}
