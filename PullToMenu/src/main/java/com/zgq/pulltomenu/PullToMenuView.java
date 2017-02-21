package com.zgq.pulltomenu;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.text.Layout;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

/**
 * Created by zgq on 2017/2/21.
 */

public class PullToMenuView extends ScrollView {
    private Context context;
    private View mainLayout;
    private View menuLayout;
    private View mainView;
    private View menuView;
    private int mainLyHeight;
    private int menulyHeight;

    public PullToMenuView(Context context) {
        super(context);
        this.context = context;
        init(null);
    }

    public PullToMenuView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init(attrs);
    }

    public PullToMenuView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        init(attrs);
    }

    public PullToMenuView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.context = context;
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        if (attrs != null) {
            TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.PullToMenuView);
            int mainLayoutId = typedArray.getResourceId(R.styleable.PullToMenuView_MainLayout, 0);
            int menuLayoutId = typedArray.getResourceId(R.styleable.PullToMenuView_MenuLayout, 0);
            menuLayout = LayoutInflater.from(context).inflate(menuLayoutId, this);
//            mainLayout = LayoutInflater.from(context).inflate(mainLayoutId, this);
        }
    }


    public void slideToBottom() {

    }

    public void slideToTop() {

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
//        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
//
//        heightSize = (int) (heightSize+menuLayout.getHeight());
//
//        setMeasuredDimension(widthSize,heightSize);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
//        getLyHeight();
//        Log.d("---->>",""+menulyHeight);
    }



    private void getLyHeight() {
        if (mainLayout != null) {
            mainLyHeight = mainLayout.getMeasuredHeight();
        }
        if (menuLayout != null) {
            menulyHeight = menuLayout.getMeasuredHeight();
        }
    }


    public void initPosition() {
        Log.d("---->>",""+menulyHeight);
        ObjectAnimator.ofFloat(this, "translationY", -50).
                setDuration(0).start();

    }

}
