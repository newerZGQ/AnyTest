package com.zgq.wokao.ui;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zgq.wokao.R;
import com.zgq.wokao.data.FillInQuestion;
import com.zgq.wokao.data.NormalExamPaper;

import java.util.LinkedList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;

public class AnswerStudyActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private Realm realm = Realm.getDefaultInstance();
    private NormalExamPaper normalExamPaper;
    private RealmList<FillInQuestion> fillInQuestions = new RealmList<>();

    private TextView showAnswerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
        setContentView(R.layout.activity_answer_study);
        initView();
    }
    private void initData(){
        RealmResults<NormalExamPaper> papers = realm.where(NormalExamPaper.class).findAll();
        normalExamPaper = papers.get(0);
        fillInQuestions = normalExamPaper.getFillInQuestions();
    }
    private void initView(){
        viewPager = (ViewPager) findViewById(R.id.answer);
        viewPager.setAdapter(new MyViewPagerAdapter(fillInQuestions,this));
        showAnswerButton = (TextView) findViewById(R.id.show_answer);
        showAnswerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                viewPager.getCurrentItem()
                View view = ((MyViewPagerAdapter)viewPager.getAdapter()).getCurrentView();
                ((MyViewPagerAdapter.FillInQuestionViewHolder)view.getTag()).questionAnswer.setText("333333");

            }
        });
    }
    public class MyViewPagerAdapter extends PagerAdapter {
        //显示的数据
        private List<FillInQuestion> datas = null;
        private LinkedList<View> mViewCache = null;
        private Context mContext ;
        private LayoutInflater mLayoutInflater = null;

//        private ViewGroup viewGroup = null;
//        private int position = 0;
        private FillInQuestionViewHolder holder;

        public MyViewPagerAdapter(List<FillInQuestion> datas, Context context) {
            super();
            this.datas = datas;
            this.mContext = context ;
            this.mLayoutInflater = LayoutInflater.from(mContext) ;
            this.mViewCache = new LinkedList<>(); }
        @Override public int getCount() {
            Log.e("test","getCount ");
            return this.datas.size();
        }
        @Override public int getItemPosition(Object object) {
            Log.e("test","getItemPosition ");
            return super.getItemPosition(object);
        }
        @Override public Object instantiateItem(ViewGroup container, int position) {
            Log.e("test","instantiateItem " + position);
            return getFillInQuestionView(container,position,false);
        }
        @Override public void destroyItem(ViewGroup container, int position, Object object) {
            Log.e("test","destroyItem " + position);
            View contentView = (View) object;
            container.removeView(contentView);
            this.mViewCache.add(contentView);
        }
        @Override public boolean isViewFromObject(View view, Object o) {
            Log.e("test","isViewFromObject ");
            return view == o;
        }

        public View getFillInQuestionView(ViewGroup container, int position,boolean showAnswer){
//            viewGroup = container;
//            this.position = position;
            FillInQuestionViewHolder fillInQuestionViewHolder = null;
            View convertView = null;
            if(mViewCache.size() == 0){
                convertView = this.mLayoutInflater.inflate(R.layout.viewadapter_fillinquestion_item , null ,false);
                TextView questionBody   = (TextView) convertView.findViewById(R.id.fillinquestion_body);
                TextView questionAnswer = (TextView) convertView.findViewById(R.id.fillinQuestion_answer);
                fillInQuestionViewHolder = new FillInQuestionViewHolder();
                fillInQuestionViewHolder.questionBody = questionBody;
                fillInQuestionViewHolder.questionAnswer = questionAnswer;
                convertView.setTag(fillInQuestionViewHolder);
            }else {
                convertView = mViewCache.removeFirst();
                fillInQuestionViewHolder = (FillInQuestionViewHolder)convertView.getTag();
            }
            holder = fillInQuestionViewHolder;
            fillInQuestionViewHolder.questionBody.setText(datas.get(position).getBody());
            fillInQuestionViewHolder.questionAnswer.setText(datas.get(position).getBody());
            container.addView(convertView ,ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT );
            return convertView;
        }

        @Override
        public void setPrimaryItem(ViewGroup container, int position, Object object) {
            currentView = (View) object;
            super.setPrimaryItem(container, position, object);
        }
        public View currentView = null;
        public View getCurrentView(){
            return currentView;
        }

        public void showFillInQuestionAnswer(){
            Log.d("------------->>","fffff");
            holder.questionAnswer.setText(datas.get(0).getAnswer());
        }
        public final class FillInQuestionViewHolder {
            public TextView questionBody;
            public TextView questionAnswer;
        }
    }
}
