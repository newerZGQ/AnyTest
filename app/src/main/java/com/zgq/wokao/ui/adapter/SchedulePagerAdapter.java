package com.zgq.wokao.ui.adapter;

import android.animation.ObjectAnimator;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zgq.wokao.R;
import com.zgq.wokao.Util.ContextUtil;
import com.zgq.wokao.Util.FontsUtil;
import com.zgq.wokao.model.viewdate.QstData;
import com.zgq.wokao.model.viewdate.ScheduleData;
import com.zgq.wokao.ui.fragment.impl.ScheduleFragment;

import java.util.ArrayList;
import java.util.LinkedList;


/**
 * Created by zgq on 2017/3/5.
 */

public class SchedulePagerAdapter extends PagerAdapter {

    public static final String TAG = "SchedulePagerAdapter";

    private ArrayList<ScheduleData> scheduleDatas;

    private ArrayList<ArrayList<QstData>> qstDatasList = new ArrayList<>();

    //缓存 复用
    private LinkedList<View> mViewCache = new LinkedList<>();
    private LayoutInflater layoutInflater = LayoutInflater.from(ContextUtil.getContext());

    private OnViewClickListener listener;

    private View currentView;
    private View preView;
    private View nextView;

    public Status getStatus() {
        return status;
    }

    private Status status = Status.SHOWADDTIME;

    private SchedulePagerAdapter(){}

    public SchedulePagerAdapter(ArrayList<ScheduleData> scheduleDatas,
                                ArrayList<ArrayList<QstData>> qstDatasList,
                                OnViewClickListener listener){
        this.scheduleDatas = scheduleDatas;
        this.qstDatasList = qstDatasList;
        this.listener = listener;
    }

    public ArrayList<ScheduleData> getScheduleDatas() {
        return scheduleDatas;
    }

    public void setScheduleDatas(ArrayList<ScheduleData> scheduleDatas) {
        this.scheduleDatas = scheduleDatas;
    }

    public void setQstDatasList(ArrayList<ArrayList<QstData>> qstDatasList){
        this.qstDatasList = qstDatasList;
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
            TextView title = (TextView)convertView.findViewById(R.id.paper_title);
            title.setTypeface(FontsUtil.getSans_serif_thin());
            TextView addTime = (TextView)convertView.findViewById(R.id.add_time);
            Button startBtn = (Button) convertView.findViewById(R.id.start_study);
            RecyclerView qstList = (RecyclerView) convertView.findViewById(R.id.qst_datial_rl);
            holder = new ViewHolder();
            holder.topLayout = topLayout;
            holder.title = title;
            holder.addTime = addTime;
            holder.startBtn = startBtn;
            holder.qstList = qstList;
            convertView.setTag(holder);
        }else {
            convertView = mViewCache.removeFirst();
            holder = (ViewHolder) convertView.getTag();
        }
        holder.topLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "toplayout click in");
                listener.onClickTopLayout(position);
            }
        });
        holder.title.setText(scheduleDatas.get(position).getPaperTitle());
        holder.addTime.setText(scheduleDatas.get(position).getAddTime());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ContextUtil.getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        holder.qstList.setLayoutManager(linearLayoutManager);
        holder.qstList.setAdapter(new QuestionInfoAdapter(qstDatasList.get(position)));
        switch (status){
            case SHOWADDTIME:
                holder.startBtn.setVisibility(View.GONE);
                holder.addTime.setVisibility(View.VISIBLE);
                holder.topLayout.setBackgroundColor(ContextUtil.getContext().getResources().getColor(R.color.color_top_layout_background));
                break;
            case SHOWSTARTBTN:
                holder.startBtn.setVisibility(View.VISIBLE);
                holder.addTime.setVisibility(View.GONE);
                holder.topLayout.setBackgroundColor(ContextUtil.getContext().getResources().getColor(R.color.transparent));
                break;
            default:
                break;
        }
        container.addView(convertView ,ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT );
        return convertView;
    }

    @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        currentView = (View) object;
        nextView = container.getChildAt(position+1);
        super.setPrimaryItem(container, position, object);
    }

    public View getCurrentView() {
        return currentView;
    }

    public View getPreView(){
        return preView;
    }

    public View getNextView(){
        return nextView;
    }

    public void changeStatus(Status status){
        switch (status){
            case SHOWADDTIME:
                getCurrentView().findViewById(R.id.start_study).setVisibility(View.GONE);
                getCurrentView().findViewById(R.id.add_time).setVisibility(View.VISIBLE);
                getCurrentView().findViewById(R.id.top_layout).
                        setBackgroundColor(ContextUtil.getContext().getResources().getColor(R.color.color_top_layout_background));

                this.status = status;
                break;
            case SHOWSTARTBTN:
                getCurrentView().findViewById(R.id.start_study).setVisibility(View.VISIBLE);
                getCurrentView().findViewById(R.id.add_time).setVisibility(View.GONE);
                getCurrentView().findViewById(R.id.top_layout).
                        setBackgroundColor(ContextUtil.getContext().getResources().getColor(R.color.transparent));
                this.status = status;
                break;
            default:
                break;
        }
    }

    public final class ViewHolder {
        public LinearLayout topLayout;
        public TextView title;
        public TextView addTime;
        public Button startBtn;
        public RecyclerView qstList;
    }

    public interface OnViewClickListener{
        public void onClickTopLayout(int position);
    }

    public enum Status{
        SHOWSTARTBTN,SHOWADDTIME;
    }

}
