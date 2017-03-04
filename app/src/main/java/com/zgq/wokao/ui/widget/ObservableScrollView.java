package com.zgq.wokao.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

import com.ecloud.pulltozoomview.PullToZoomScrollViewEx;

/**
 * Created by zgq on 16-7-16.
 */
public class ObservableScrollView extends PullToZoomScrollViewEx {
    private MyScrollViewListener scrollViewListener = null;

    private ScrollView rootview;

    public ObservableScrollView(Context context) {
        super(context);
    }
    public ObservableScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setScrollViewListener(MyScrollViewListener scrollViewListener) {
        this.scrollViewListener = scrollViewListener;
    }

    public interface MyScrollViewListener {
        void onScrollChanged();
    }

    public ScrollView getRootView(){
        return rootview;
    }

    @Override
    protected ScrollView createRootView(Context context, AttributeSet attrs) {
        rootview = super.createRootView(context, attrs);
        return super.createRootView(context, attrs);
    }

    float initY = 0;
//    @Override
//    public boolean onInterceptTouchEvent(MotionEvent event) {
//
//        switch (event.getAction()){
//            case MotionEvent.ACTION_DOWN:
//                initY = event.getY();
//                break;
//            case MotionEvent.ACTION_MOVE:
//                float delta = event.getY() - initY;
//                if (Math.abs(delta)>5) scrollViewListener.onScrollChanged();
//        }
//        return super.onInterceptTouchEvent(event);
//    }
}
