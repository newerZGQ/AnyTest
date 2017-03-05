package com.zgq.wokao.ui.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.zgq.wokao.R;
import com.zgq.wokao.Util.ContextUtil;
import com.zgq.wokao.model.viewdate.ScheduleData;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Created by zgq on 2017/3/5.
 */

public class SchedulePagerAdapter extends PagerAdapter {

    private ArrayList<ScheduleData> scheduleDatas;
    //缓存 复用
    private LinkedList<View> mViewCache = null;
    private LayoutInflater layoutInflater = LayoutInflater.from(ContextUtil.getContext());

    public SchedulePagerAdapter(ArrayList<ScheduleData> scheduleDatas){
        this.scheduleDatas = scheduleDatas;
    }
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        super.destroyItem(container, position, object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
//        View view = getInflater().inflate(R.layout.viewpager_schedule_item,null);
//        container.addView(view);
        return null;
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return scheduleDatas.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return false;
    }

    public View getScheduleView(ViewGroup container, int position){
        ViewHolder holder = null;
        View convertView = null;
        if(mViewCache.size() == 0){
            convertView = this.layoutInflater.inflate(R.layout.viewpager_schedule_item , null ,false);
            TextView accuracy;
            TextView title;
            TextView countToday;
            TextView countEveryday;
            Button fillInBtn;
            Button tfBtn;
            Button sglChoBtn;
            Button mulchoBtn;
            Button discBtn;
            ViewHolder viewHolder = new ViewHolder();
            viewHolder.accuracy = accuracy;
            viewHolder.title = title;
            viewHolder.countToday = countToday;
            viewHolder.countEveryday = countEveryday;
            viewHolder.fillInBtn = fillInBtn;
            viewHolder.tfBtn = tfBtn;
            viewHolder.sglChoBtn = sglChoBtn;
            viewHolder.mulchoBtn = mulchoBtn;
            viewHolder.discBtn = discBtn;
            convertView.setTag(fillInQuestionViewHolder);
        }else {
            convertView = mViewCache.removeFirst();
            fillInQuestionViewHolder = (FillInQuestionAdapter.FillInQuestionViewHolder)convertView.getTag();
        }
        holder = fillInQuestionViewHolder;
        fillInQuestionViewHolder.questionBody.setText(""+(position+1)+". "+datas.get(position).getBody());
        if (hasShowAnswer.get(position)) {
            fillInQuestionViewHolder.questionAnswer.setText(datas.get(position).getAnswer().getContent());
        }else {
            fillInQuestionViewHolder.questionAnswer.setText("");
        }
        container.addView(convertView ,ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT );
        return convertView;
    }

    public final class ViewHolder {
        public TextView accuracy;
        public TextView title;
        public TextView countToday;
        public TextView countEveryday;
        public Button fillInBtn;
        public Button tfBtn;
        public Button sglChoBtn;
        public Button mulchoBtn;
        public Button discBtn;
    }
}
