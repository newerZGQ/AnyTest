package com.zgq.wokao.ui.adapter;

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
    private LinkedList<View> mViewCache = new LinkedList<>();
    private LayoutInflater layoutInflater = LayoutInflater.from(ContextUtil.getContext());

    public SchedulePagerAdapter(ArrayList<ScheduleData> scheduleDatas){
        this.scheduleDatas = scheduleDatas;
    }

    public ArrayList<ScheduleData> getScheduleDatas() {
        return scheduleDatas;
    }

    public void setScheduleDatas(ArrayList<ScheduleData> scheduleDatas) {
        this.scheduleDatas = scheduleDatas;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        View contentView = (View) object;
        container.removeView(contentView);
        this.mViewCache.add(contentView);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        return getScheduleView(container,position);
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
        return view == object;
    }

    public View getScheduleView(ViewGroup container, int position){
        ViewHolder holder = null;
        View convertView = null;
        if(mViewCache.size() == 0){
            convertView = this.layoutInflater.inflate(R.layout.viewpager_schedule_item , null ,false);
            TextView accuracy = (TextView)convertView.findViewById(R.id.accuracy);
            TextView title = (TextView)convertView.findViewById(R.id.paper_title);
            TextView countToday = (TextView)convertView.findViewById(R.id.count_today);
            TextView countEveryday = (TextView)convertView.findViewById(R.id.count_everyday);
            Button fillInBtn = (Button) convertView.findViewById(R.id.fillIn_Btn);
            Button tfBtn = (Button) convertView.findViewById(R.id.tf_Btn);
            Button sglChoBtn = (Button) convertView.findViewById(R.id.sglcho_Btn);
            Button mulchoBtn = (Button) convertView.findViewById(R.id.mulcho_Btn);
            Button discBtn = (Button) convertView.findViewById(R.id.disc_Btn);
            holder = new ViewHolder();
            holder.accuracy = accuracy;
            holder.title = title;
            holder.countToday = countToday;
            holder.countEveryday = countEveryday;
            holder.fillInBtn = fillInBtn;
            holder.tfBtn = tfBtn;
            holder.sglChoBtn = sglChoBtn;
            holder.mulchoBtn = mulchoBtn;
            holder.discBtn = discBtn;
            convertView.setTag(holder);
        }else {
            convertView = mViewCache.removeFirst();
            holder = (ViewHolder) convertView.getTag();
        }
        holder.accuracy.setText("64%");
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
