package com.zgq.wokaofree.ui.adapter;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zgq.wokaofree.R;
import com.zgq.wokaofree.Util.ContextUtil;
import com.zgq.wokaofree.Util.DateUtil;
import com.zgq.wokaofree.model.viewdate.ScheduleData;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Locale;


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

    private SchedulePagerAdapter() {
    }

    public SchedulePagerAdapter(Context context, ArrayList<ScheduleData> scheduleDatas,
                                OnViewClickListener listener) {
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
        return getScheduleView(container, position);
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

    public View getScheduleView(ViewGroup container, final int position) {
        ViewHolder holder = null;
        View convertView = null;
        if (mViewCache.size() == 0) {
            convertView = this.layoutInflater.inflate(R.layout.viewpager_schedule_item, null, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
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
        String date = "";
        try {
            date = DateUtil.getYYYY_MM_DD(scheduleDatas.get(position).getAddTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (Locale.getDefault().getLanguage().equals(new Locale("zh").getLanguage())){
            holder.addTime.setText(date + " 添加");
        }else{
            holder.addTime.setText("Added " + date);
        }
        holder.startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onClickStartBtn(position, scheduleDatas.get(position).getPaperId());
            }
        });

        ObjectAnimator animator = ObjectAnimator.
                ofFloat(holder.startBtn,"alpha",0.7f,0.6f,0.4f,0.2f,0f,0.3f,0.5f,0.7f).
                setDuration(3000);
        animator.setRepeatCount(-1);
        animator.setRepeatMode(ValueAnimator.RESTART);
        animator.start();
        container.addView(convertView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        return convertView;
    }


    public final class ViewHolder extends RecyclerView.ViewHolder{
        public LinearLayout topLayout;
        public TextView title;
        public TextView addTime;
        public ImageView startBtn;
        public ObjectAnimator animator;

        public ViewHolder(View itemView) {
            super(itemView);
            topLayout = (LinearLayout) itemView.findViewById(R.id.top_layout);
            title = (TextView) itemView.findViewById(R.id.question_type);
            addTime = (TextView) itemView.findViewById(R.id.add_time);
            startBtn = (ImageView) itemView.findViewById(R.id.start_study);
        }
    }

    public interface OnViewClickListener {
        void onClickTopLayout(int position);

        void onClickStartBtn(int position, String paperId);
    }

}
