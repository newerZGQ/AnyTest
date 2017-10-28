package com.zgq.wokao.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.zgq.wokao.R;
import com.zgq.wokao.model.paper.info.IPaperInfo;
import com.zgq.wokao.model.viewdate.QstData;

import java.util.List;

/**
 * Created by zgq on 2017/8/23.
 */

public class QuestionsInfoAdapter extends RecyclerView.Adapter {

    private final int header_type = 0;
    private final int common_type = 1;

    private List<QstData> qstDatas;
    private IPaperInfo paperInfo;
    private ItemClickListener clickListener;
    private Context context;

    public QuestionsInfoAdapter(Context context, List<QstData> qstDatas,
                                IPaperInfo paperInfo, ItemClickListener clickListener) {
        this.context = context;
        this.qstDatas = qstDatas;
        this.paperInfo = paperInfo;
        this.clickListener = clickListener;
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
        if (viewType == common_type) {
            View view = LayoutInflater.from(parent.getContext()).
                    inflate(R.layout.recyclerview_questions_item, null);
            return new CommonViewHolder(view, clickListener);
        } else {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.recycleview_questions_header, null);
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
            switch (qstDatas.get(realPosition).getType()){
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
                    setText("共" + qstDatas.get(realPosition).getQstCount() +
                            "题，收藏" + qstDatas.get(realPosition).getStarCount() + "题");
            ImageView icon = ((CommonViewHolder) holder).titleIcon;
            switch (qstDatas.get(realPosition).getType()){
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
        return qstDatas.size() + 1;
    }

    class CommonViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView qstType;
        public ImageView titleIcon;
        public TextView qstInfo;
        private ItemClickListener clickListener = new ItemClickListener() {
            @Override
            public void onItemClicked(int position) {

            }
        };

        public CommonViewHolder(View itemView, ItemClickListener clickListener) {
            super(itemView);
            this.clickListener = clickListener;
            itemView.setOnClickListener(this);
            qstType = (TextView) itemView.findViewById(R.id.question_type_text);
            titleIcon = (ImageView) itemView.findViewById(R.id.title_icon);
            qstInfo = (TextView) itemView.findViewById(R.id.qst_info);
        }

        @Override
        public void onClick(View view) {
            clickListener.onItemClicked(getPosition()-1);
        }
    }

    class HeaderViewHolder extends RecyclerView.ViewHolder {

        public TextView title;
        public TextView author;

        public HeaderViewHolder(View itemView) {
            super(itemView);
            this.title = (TextView) itemView.findViewById(R.id.paper_title);
            this.author = (TextView) itemView.findViewById(R.id.paper_author);
        }
    }

    public interface ItemClickListener {
        void onItemClicked(int position);
    }
}
