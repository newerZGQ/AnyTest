package com.zgq.wokao.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zgq.wokao.R;
import com.zgq.wokao.data.FillInQuestion;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by zgq on 16-7-6.
 */
public class TFQuestionAdapter extends PagerAdapter implements BaseStudySystemAdapter{
    private List<FillInQuestion> datas = null;
    private LinkedList<View> mViewCache = null;
    private Context mContext ;
    private LayoutInflater mLayoutInflater = null;
    private ArrayList<Boolean> hasShowAnswer = null;
    private ArrayList<Boolean> myAnswer = new ArrayList<>();

    private View currentView = null;
    private int currentPosition = 0;


    private TFQuestionViewHolder holder;

    public TFQuestionAdapter(List<FillInQuestion> datas, Context context) {
        super();
        this.datas = datas;
        this.mContext = context ;
        this.mLayoutInflater = LayoutInflater.from(mContext) ;
        this.mViewCache = new LinkedList<>();
        initData();
    }
    public void initData(){
        hasShowAnswer = new ArrayList<>();
        for (int i = 0;i<datas.size();i++){
            hasShowAnswer.add(false);
        }
    }
    @Override
    public int getCount() {
        return this.datas.size();
    }
    @Override
    public int getItemPosition(Object object) {
        return super.getItemPosition(object);
    }
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        return getFillInQuestionView(container,position);
    }
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        View contentView = (View) object;
        container.removeView(contentView);
        this.mViewCache.add(contentView);
    }
    @Override public boolean isViewFromObject(View view, Object o) {
        return view == o;
    }

    public View getFillInQuestionView(ViewGroup container, int position){
        TFQuestionViewHolder tfQuestionViewHolder = null;
        View convertView = null;
        if(mViewCache.size() == 0){
            convertView = this.mLayoutInflater.inflate(R.layout.viewadapter_tfquestion_item , null ,false);
            TextView questionBody       = (TextView) convertView.findViewById(R.id.tfquestion_body);
            LinearLayout optionTrue     = (LinearLayout) convertView.findViewById(R.id.tfqst_option_true);
            LinearLayout optionFalse    = (LinearLayout) convertView.findViewById(R.id.tfqst_option_false);
            tfQuestionViewHolder = new TFQuestionViewHolder();
            tfQuestionViewHolder.questionBody   = questionBody;
            tfQuestionViewHolder.optionTrue     = optionTrue;
            tfQuestionViewHolder.optionFalse    = optionFalse;
            convertView.setTag(tfQuestionViewHolder);
        }else {
            convertView = mViewCache.removeFirst();
            tfQuestionViewHolder = (TFQuestionViewHolder)convertView.getTag();
        }
        holder = tfQuestionViewHolder;
        tfQuestionViewHolder.questionBody.setText(datas.get(position).getBody());
        if (hasShowAnswer.get(position)) {
//            tfQuestionViewHolder.optionTrue.setText(datas.get(position).getAnswer());
        }
        container.addView(convertView ,ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT );
        return convertView;
    }

    @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        currentView = (View) object;
        currentPosition = position;
        super.setPrimaryItem(container, position, object);
    }
    @Override
    public View getCurrentView(){
        return currentView;
    }
    @Override
    public int getCurrentPosition(){
        return currentPosition;
    }

    public void showCurrentAnswer(){
        if (hasShowAnswer.get(currentPosition)) return;
//        ((TFQuestionViewHolder)(currentView.getTag())).optionTrue.setText(datas.get(currentPosition).getAnswer());
        hasShowAnswer.set(currentPosition,true);
    }
    @Override
    public void hideCurrentAnswer() {

    }
    public final class TFQuestionViewHolder {
        public TextView questionBody;
        public LinearLayout optionTrue;
        public LinearLayout optionFalse;
    }
}
