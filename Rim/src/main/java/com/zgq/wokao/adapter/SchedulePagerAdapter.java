package com.zgq.wokao.adapter;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zgq.wokao.R;
import com.zgq.wokao.entity.paper.info.ExamPaperInfo;
import com.zgq.wokao.util.DateUtil;

import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

import javax.annotation.Nonnull;

import io.realm.RealmResults;

public class SchedulePagerAdapter extends PagerAdapter {

    public static final String TAG = SchedulePagerAdapter.class.getSimpleName();

    //缓存 复用
    private LinkedList<View> mViewCache = new LinkedList<>();

    private OnClickListener listener;

    private List<ExamPaperInfo> examPaperInfos;

    public SchedulePagerAdapter(@Nonnull List<ExamPaperInfo> examPaperInfos,
                                @Nonnull OnClickListener listener) {
        this.examPaperInfos = examPaperInfos;
        this.listener = listener;
    }

    public void replaceData(@Nonnull List<ExamPaperInfo> examPaperInfos) {
        this.examPaperInfos = examPaperInfos;
        notifyDataSetChanged();
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        return POSITION_NONE;
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
    public int getCount() {
        return examPaperInfos.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    public View getScheduleView(ViewGroup container, final int position) {
        ViewHolder holder = null;
        View convertView = null;
        if (mViewCache.size() == 0) {
            convertView = (LayoutInflater.from(container.getContext())
                    .inflate(R.layout.viewpager_schedule_item, null, false));
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            convertView = mViewCache.removeFirst();
            holder = (ViewHolder)convertView.getTag();
        }
        holder.topLayout.setOnClickListener(v -> listener.onClickTopLayout(position));
        holder.title.setText(examPaperInfos.get(position).getTitle());
        String date = "";
        date = DateUtil.toLocalDateFormat(examPaperInfos.get(position).getCreateDate());

        if (Locale.getDefault().getLanguage().equals(new Locale("zh").getLanguage())) {
            holder.addTime.setText(date + " 添加");
        } else {
            holder.addTime.setText("Added " + date);
        }
        holder.startBtn.setOnClickListener(view ->
                listener.onClickStartBtn(position, examPaperInfos.get(position).getId()));

        ObjectAnimator animator = ObjectAnimator.
                ofFloat(holder.startBtn, "alpha",
                        0.7f, 0.6f, 0.4f, 0.2f, 0f, 0.3f, 0.5f, 0.7f).
                setDuration(3000);
        animator.setRepeatCount(-1);
        animator.setRepeatMode(ValueAnimator.RESTART);
        animator.start();
        container.addView(convertView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        return convertView;
    }


    public final class ViewHolder extends RecyclerView.ViewHolder {
        public LinearLayout topLayout;
        public TextView title;
        public TextView addTime;
        public ImageView startBtn;

        public ViewHolder(View itemView) {
            super(itemView);
            topLayout =  itemView.findViewById(R.id.top_layout);
            title =  itemView.findViewById(R.id.question_type);
            addTime =  itemView.findViewById(R.id.add_time);
            startBtn =  itemView.findViewById(R.id.start_study);
        }
    }

    public interface OnClickListener {
        void onClickTopLayout(int position);

        void onClickStartBtn(int position, String paperInfoId);
    }

}

