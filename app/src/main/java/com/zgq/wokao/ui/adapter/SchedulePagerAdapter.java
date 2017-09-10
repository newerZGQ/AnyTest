package com.zgq.wokao.ui.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zgq.wokao.R;
import com.zgq.wokao.Util.ContextUtil;
import com.zgq.wokao.Util.FontsUtil;
import com.zgq.wokao.model.viewdate.ScheduleData;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;


/**
 * Created by zgq on 2017/3/5.
 */

public class SchedulePagerAdapter extends PagerAdapter {

    public static final String TAG = "SchedulePagerAdapter";

    private Context context;

    private ArrayList<ScheduleData> scheduleDatas;

    //缓存 复用
    private LinkedList<View> mViewCache = new LinkedList<>();
    private LayoutInflater layoutInflater = LayoutInflater.from(ContextUtil.getContext());

    private OnViewClickListener listener;

    private SchedulePagerAdapter(){}

    public SchedulePagerAdapter(Context context,ArrayList<ScheduleData> scheduleDatas,
                                OnViewClickListener listener){
        this.scheduleDatas = scheduleDatas;
        this.listener = listener;
        this.context = context;
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

    public View getScheduleView(ViewGroup container, final int position){
        ViewHolder holder = null;
        View convertView = null;
        if(mViewCache.size() == 0){
            convertView = this.layoutInflater.inflate(R.layout.viewpager_schedule_item , null ,false);
            LinearLayout topLayout = (LinearLayout) convertView.findViewById(R.id.top_layout);
            TextView title = (TextView)convertView.findViewById(R.id.question_type);
            title.setTypeface(FontsUtil.getSans_serif_thin());
            TextView addTime = (TextView)convertView.findViewById(R.id.add_time);
            Button startBtn = (Button) convertView.findViewById(R.id.start_study);
            holder = new ViewHolder();
            holder.topLayout = topLayout;
            holder.title = title;
            holder.addTime = addTime;
            holder.startBtn = startBtn;
            convertView.setTag(holder);
        }else {
            convertView = mViewCache.removeFirst();
            holder = (ViewHolder) convertView.getTag();
        }
        holder.topLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClickTopLayout(position);
            }
        });
        holder.title.setText(scheduleDatas.get(position).getPaperTitle());
        holder.addTime.setText(scheduleDatas.get(position).getAddTime());
        holder.startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onClickStartBtn(position, scheduleDatas.get(position).getPaperId());
            }
        });

        ArrayList<Integer> models = new ArrayList<>();
        for(int i = 0; i < 5; ++i)
        {
            Random random = new Random();
            random.setSeed(i);
            int color = Color.argb(255, random.nextInt(255), random.nextInt(255), random.nextInt(255));
            models.add(color);
        }

        container.addView(convertView ,ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT );
        return convertView;
    }


    public final class ViewHolder {
        public LinearLayout topLayout;
        public TextView title;
        public TextView addTime;
        public Button startBtn;
    }

    public interface OnViewClickListener{
        void onClickTopLayout(int position);
        void onClickStartBtn(int position, String paperId);
    }

}
