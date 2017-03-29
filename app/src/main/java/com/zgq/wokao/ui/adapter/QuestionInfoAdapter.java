package com.zgq.wokao.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zgq.wokao.R;
import com.zgq.wokao.Util.LogUtil;
import com.zgq.wokao.model.paper.Constant;
import com.zgq.wokao.model.paper.QuestionType;
import com.zgq.wokao.model.viewdate.QstData;
import com.zgq.wokao.ui.activity.AnswerStudyActivity;

import java.util.ArrayList;

/**
 * Created by zgq on 2017/3/19.
 */

public class QuestionInfoAdapter extends RecyclerView.Adapter<QuestionInfoAdapter.MyViewHolder> {
    public static final String TAG = QuestionInfoAdapter.class.getName();
    private Context context;
    private ArrayList<QstData> qstDatas = null;

    public QuestionInfoAdapter(){}
    public QuestionInfoAdapter(Context context,ArrayList<QstData> qstDatas) {
        this.qstDatas = qstDatas;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recyclerview_qstinfo_item, parent,false);
        LinearLayout rootView = (LinearLayout) view.findViewById(R.id.root_view);
        TextView qstTypeIcon = (TextView) view.findViewById(R.id.type_icon);
        TextView qstTypeTitle = (TextView) view.findViewById(R.id.type_title);
        TextView accuracy = (TextView) view.findViewById(R.id.accuracy);
        TextView studyCount = (TextView) view.findViewById(R.id.study_count);
        Button fallible1 = (Button) view.findViewById(R.id.fallible_1);
        Button fallible2 = (Button) view.findViewById(R.id.fallible_2);
        Button fallible3 = (Button) view.findViewById(R.id.fallible_3);
        TextView basicInfo = (TextView) view.findViewById(R.id.basic_info);
        return new MyViewHolder(view,rootView,qstTypeIcon,qstTypeTitle,accuracy,studyCount,
                fallible1,fallible2,fallible3,basicInfo);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        if (qstDatas == null || qstDatas.size() == 0){
            return;
        }
        final QstData data = qstDatas.get(position);

        int qstType = data.getType().getIndex();

        switch (qstType){
            case QuestionType.fillin_index:
                holder.rootView.setBackground(context.getResources().getDrawable(R.drawable.rectangle_test));
                holder.rootView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.d(LogUtil.PREFIX+TAG,"----->>click in");
                        startStudy(data.getPaperId(), Constant.FILLINQUESTIONTYPE, 0);
                    }
                });
                holder.qstTypeIcon.setBackground(context.getResources().getDrawable(R.drawable.qst_icon_fillin));
                holder.qstTypeTitle.setText("填空题");
                break;
            case QuestionType.tf_index:
                holder.rootView.setBackground(context.getResources().getDrawable(R.drawable.qst_background_tf));
                holder.rootView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startStudy(data.getPaperId(), Constant.TFQUESTIONTYPE, 0);
                    }
                });
                holder.qstTypeIcon.setBackground(context.getResources().getDrawable(R.drawable.qst_icon_tf));
                holder.qstTypeTitle.setText("选择题");
                break;
            case QuestionType.sglc_index:
                holder.rootView.setBackground(context.getResources().getDrawable(R.drawable.qst_background_sgl));
                holder.rootView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startStudy(data.getPaperId(), Constant.SGLCHOQUESTIONTYPE, 0);
                    }
                });
                holder.qstTypeIcon.setBackground(context.getResources().getDrawable(R.drawable.qst_icon_sgl));
                holder.qstTypeTitle.setText("单选题");
                break;
            case QuestionType.mtlc_index:
                holder.rootView.setBackground(context.getResources().getDrawable(R.drawable.qst_background_mlc));
                holder.rootView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startStudy(data.getPaperId(), Constant.MULTCHOQUESTIONTYPE, 0);
                    }
                });
                holder.qstTypeIcon.setBackground(context.getResources().getDrawable(R.drawable.qst_icon_mlc));
                holder.qstTypeTitle.setText("多选题");
                break;
            case QuestionType.disc_index:
                holder.rootView.setBackground(context.getResources().getDrawable(R.drawable.qst_background_dis));
                holder.rootView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startStudy(data.getPaperId(), Constant.DISCUSSQUESTIONTYPE, 0);
                    }
                });
                holder.qstTypeIcon.setBackground(context.getResources().getDrawable(R.drawable.qst_icon_dis));
                holder.qstTypeTitle.setText("简答题");
                break;
        }
        holder.accuracy.setText(""+data.getAccuracy());
        holder.studyCount.setText("答题" + data.getStarCount() + "次");
        holder.basicInfo.setText("共" + data.getQstCount() + "题，收藏" + data.getStarCount() + "题");
    }

    @Override
    public int getItemCount() {
        return qstDatas.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        public LinearLayout rootView;
        public TextView qstTypeIcon;
        public TextView qstTypeTitle;
        public TextView accuracy;
        public TextView studyCount;
        public Button fallible1;
        public Button fallible2;
        public Button fallible3;
        public TextView basicInfo;

        public MyViewHolder(View itemView, LinearLayout rootView, TextView qstTypeIcon, TextView qstTypeTitle,
                            TextView accuracy, TextView studyCount, Button fallible1, Button fallible2,
                            Button fallible3, TextView basicInfo) {
            super(itemView);
            this.rootView = rootView;
            this.qstTypeIcon = qstTypeIcon;
            this.qstTypeTitle = qstTypeTitle;
            this.accuracy = accuracy;
            this.studyCount = studyCount;
            this.fallible1 = fallible1;
            this.fallible2 = fallible2;
            this.fallible3 = fallible3;
            this.basicInfo = basicInfo;
        }
    }

    private void startStudy(String paperId, int type, int qstNum){
        Log.d(LogUtil.PREFIX+TAG,"----->>"+paperId + " "+ type + " "+ qstNum);
        Intent intent = new Intent(context,AnswerStudyActivity.class);
        if (paperId != null && !paperId.equals("")) {
            intent.putExtra("paperId", paperId);
            intent.putExtra("qstType", type);
            intent.putExtra("qstNum",qstNum);
        }else {
            return;
        }
        context.startActivity(intent);
    }
}
