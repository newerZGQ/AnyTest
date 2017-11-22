package com.zgq.wokao.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xw.repo.BubbleSeekBar;
import com.zgq.wokao.R;

/**
 * Created by zgq on 2017/5/20.
 */

public class TaskSettingLayout extends LinearLayout {
    private View rootView;
    private Context context;
    private BubbleSeekBar seekBar;
    private OnTaskSettingListener listener = new OnTaskSettingListener() {
        @Override
        public void onHide() {

        }

        @Override
        public void onshow() {

        }

        @Override
        public void onTaskSelected(int task) {

        }
    };

    public TaskSettingLayout(Context context) {
        super(context);
        this.context = context;
        init();
    }

    public TaskSettingLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }

    public TaskSettingLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        init();
    }

    private void init() {
        rootView = LayoutInflater.from(context).inflate(R.layout.fragment_schedule_task_setting_layout, this);
        seekBar = (BubbleSeekBar) rootView.findViewById(R.id.seekbar);
        seekBar.setOnProgressChangedListener(new BubbleSeekBar.OnProgressChangedListener() {
            @Override
            public void onProgressChanged(int progress, float progressFloat) {

            }

            @Override
            public void getProgressOnActionUp(int progress, float progressFloat) {
                rootView.setVisibility(GONE);
                listener.onTaskSelected(progress);
            }

            @Override
            public void getProgressOnFinally(int progress, float progressFloat) {

            }
        });

        rootView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                rootView.setVisibility(GONE);
                listener.onHide();
            }
        });

        rootView.setVisibility(GONE);
    }

    public void show() {
        rootView.setVisibility(VISIBLE);
        listener.onshow();
    }

    public void setOnTaskSettingListener(OnTaskSettingListener listener) {
        this.listener = listener;
    }

    public interface OnTaskSettingListener {
        void onHide();

        void onshow();

        void onTaskSelected(int task);
    }
}
