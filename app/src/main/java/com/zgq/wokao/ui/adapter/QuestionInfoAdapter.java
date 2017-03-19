package com.zgq.wokao.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.zgq.wokao.R;
import com.zgq.wokao.Util.ContextUtil;
import com.zgq.wokao.model.paper.QuestionType;
import com.zgq.wokao.model.viewdate.QstData;

import java.util.ArrayList;

/**
 * Created by zgq on 2017/3/19.
 */

public class QuestionInfoAdapter extends RecyclerView.Adapter<QuestionInfoAdapter.MyViewHolder> {
    private android.content.Context context = ContextUtil.getContext();
    private ArrayList<QstData> qstDatas = null;

    public QuestionInfoAdapter(){}
    public QuestionInfoAdapter(ArrayList<QstData> qstDatas) {
        this.qstDatas = qstDatas;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recyclerview_qstinfo_item, parent,false);
        TextView qstTypeIcon = (TextView) view.findViewById(R.id.type_icon);
        TextView qstTypeTitle = (TextView) view.findViewById(R.id.type_title);
        TextView accuracy = (TextView) view.findViewById(R.id.accuracy);
        TextView studyCount = (TextView) view.findViewById(R.id.study_count);
        Button fallible1 = (Button) view.findViewById(R.id.fallible_1);
        Button fallible2 = (Button) view.findViewById(R.id.fallible_2);
        Button fallible3 = (Button) view.findViewById(R.id.fallible_3);
        TextView basicInfo = (TextView) view.findViewById(R.id.basic_info);
        return new MyViewHolder(view,qstTypeIcon,qstTypeTitle,accuracy,studyCount,fallible1,fallible2,fallible3,basicInfo);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Log.d("----->>",""+qstDatas.size());
        if (qstDatas == null || qstDatas.size() == 0){
            return;
        }
        final MyViewHolder holder1 = (MyViewHolder) holder;
        final QstData data = qstDatas.get(position);

        int qstType = data.getType().getIndex();

        switch (qstType){
            case QuestionType.fillin_index:
                break;
            case QuestionType.tf_index:
                break;
            case QuestionType.sglc_index:
                break;
            case QuestionType.mtlc_index:
                break;
            case QuestionType.disc_index:
                break;
        }
        holder1.accuracy.setText("54%");
        holder1.fallible1.setText("1");
        holder1.fallible2.setText("2");
        holder1.fallible3.setText("3");
    }

    @Override
    public int getItemCount() {
        return qstDatas.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView qstTypeIcon;
        public TextView qstTypeTitle;
        public TextView accuracy;
        public TextView studyCount;
        public Button fallible1;
        public Button fallible2;
        public Button fallible3;
        public TextView basicInfo;

        public MyViewHolder(View itemView, TextView qstTypeIcon, TextView qstTypeTitle,
                            TextView accuracy, TextView studyCount, Button fallible1, Button fallible2,
                            Button fallible3, TextView basicInfo) {
            super(itemView);
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
}
