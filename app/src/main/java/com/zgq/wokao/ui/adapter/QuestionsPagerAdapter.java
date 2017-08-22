package com.zgq.wokao.ui.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zgq.wokao.R;
import com.zgq.wokao.Util.FontsUtil;
import com.zgq.wokao.model.viewdate.QstData;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Created by zgq on 2017/8/22.
 */

public class QuestionsPagerAdapter extends PagerAdapter{

    public static final String TAG = QuestionsPagerAdapter.class.getSimpleName();

    private Context context;

    private OnViewClickListener listener;

    private ArrayList<QstData> qstDatas;

    private LayoutInflater layoutInflater;

    //缓存 复用
    private LinkedList<View> mViewCache = new LinkedList<>();

    private QuestionsPagerAdapter(){}

    public QuestionsPagerAdapter(Context context, ArrayList<QstData> qstDatas,
                           OnViewClickListener listener){
        this.context = context;
        this.listener = listener;
        this.qstDatas = qstDatas;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        View contentView = (View) object;
        container.removeView(contentView);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        return getQuestionView(container,position);
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();

    }

    @Override
    public int getCount() {
        return qstDatas.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    public View getQuestionView(ViewGroup container, final int position){
        ViewHolder holder = null;
        View convertView = null;
        if(mViewCache.size() == 0){
            convertView = this.layoutInflater.inflate(R.layout.viewpager_questions_item , null ,false);
            TextView title = (TextView)convertView.findViewById(R.id.paper_title);
            title.setTypeface(FontsUtil.getSans_serif_thin());
            holder = new ViewHolder();
            holder.title = title;
            convertView.setTag(holder);
        }else {
            convertView = mViewCache.removeFirst();
            holder = (ViewHolder) convertView.getTag();
        }
        holder.title.setText(""+qstDatas.get(position).getAccuracy());
        container.addView(convertView ,ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT );
        return convertView;
    }


    public final class ViewHolder {
        public TextView title;
    }

    public interface OnViewClickListener{
        void onClickTopLayout(int position);
    }
}
