package com.zgq.cardview;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by zgq on 2017/5/29.
 */

public class CardView extends RelativeLayout implements View.OnClickListener {
    private static final String TAG = CardView.class.getSimpleName();

    private Context context;
    private View rootView;
    private View topSignalLayout;
    private LinearLayout topContentLayout;
    private TextView topTitle;
    private TextView topHistory;
    private TextView topAccuracy;
    private TextView topFallable;
    private Button fallable_1;
    private Button fallable_2;
    private Button fallable_3;
    private TextView starCount;
    private TextView btmTitle;
    private TextView btmAccuracy;
    private Button start;
    private RelativeLayout btmLayout;
    private RelativeLayout btmContentLayout;

    private RelativeLayout topTitleLayout;
    private LinearLayout topHistoryLayout;
    private LinearLayout topAccuracyLayout;
    private LinearLayout topFallableLayout;

    private int slideHeight;
    private int duration = 200;

    private static int signalSide = 0;
    private static int detailSide = 1;
    private int currentSide = signalSide;

    private int startBtnWidth;
    private int startBtnTransDis;


    private OnCardViewListener listener;

    public CardView(Context context) {
        super(context);
        this.context = context;
        init();
    }

    public CardView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }

    public CardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        init();
    }

    private void init() {
        initView();
        setListener();
    }

    private void initView() {
        rootView = (View) LayoutInflater.from(context).inflate(R.layout.cardview_layout, this);
        topSignalLayout = rootView.findViewById(R.id.cardview_top_signal);
        topContentLayout = (LinearLayout) rootView.findViewById(R.id.cardview_top_content);
        topTitle = (TextView) rootView.findViewById(R.id.cardview_title_top);
        topHistory = (TextView) rootView.findViewById(R.id.cardview_history_top);
        topAccuracy = (TextView) rootView.findViewById(R.id.cardview_accuracy_top);
        fallable_1 = (Button) rootView.findViewById(R.id.fallable_1);
        fallable_2 = (Button) rootView.findViewById(R.id.fallable_2);
        fallable_3 = (Button) rootView.findViewById(R.id.fallable_3);
        starCount = (TextView) rootView.findViewById(R.id.star_count);
        btmTitle = (TextView) rootView.findViewById(R.id.cardview_title_btm);
        btmAccuracy = (TextView) rootView.findViewById(R.id.cardview_accuracy_btm);
        start = (Button) rootView.findViewById(R.id.cardview_start);
        btmContentLayout = (RelativeLayout) rootView.findViewById(R.id.cardview_content_btm);
        btmLayout = (RelativeLayout) rootView.findViewById(R.id.cardview_btm_layout);

        topTitleLayout = (RelativeLayout) rootView.findViewById(R.id.top_title_layout);
        topHistoryLayout = (LinearLayout) rootView.findViewById(R.id.top_history_layout);
        topAccuracyLayout = (LinearLayout) rootView.findViewById(R.id.top_accuracy_layout);
        topFallableLayout = (LinearLayout) rootView.findViewById(R.id.top_fallable_layout);
    }

    private void setListener() {
        topSignalLayout.setOnClickListener(this);
        topContentLayout.setOnClickListener(this);
        fallable_1.setOnClickListener(this);
        fallable_2.setOnClickListener(this);
        fallable_3.setOnClickListener(this);
        start.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.cardview_top_signal){
            changeSide(detailSide);
        }
        if (view.getId() == R.id.cardview_top_content){
            changeSide(signalSide);
        }
        if (view.getId() == R.id.cardview_start){
            if (listener != null) listener.onClickStart();
        }
    }

    private void changeSide(int side) {
        animateTopSignal(side);
        showTopContent();
        animateBtmContent(side);
        currentSide = side;
    }

    private void animateTopSignal(int side) {
        int distance = 0;
        if (side == detailSide) {
            if (slideHeight == 0) {
                slideHeight = topSignalLayout.getHeight() - 3;
            }
            distance = slideHeight;
            currentSide = detailSide;
        } else if (side == signalSide) {
            currentSide = signalSide;
        }
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(topSignalLayout, "translationY", distance)
                .setDuration(duration);
        objectAnimator.start();
    }

    private void animateBtmContent(int side) {
        float contentStartScale = 0f;
        float contentEndScale = 1f;

        startBtnWidth = start.getWidth();
        int startStartBtnWidth = 0;
        int endStartBtnWidth = 0;

        startBtnTransDis = btmLayout.getWidth() / 2 - start.getLeft() + start.getWidth()/2;
        float startTransValue_1 = 0;
        float startTransValue_2 = 0;

        if (side == detailSide) {
            contentStartScale = 1f;
            contentEndScale = 0f;

            startStartBtnWidth = startBtnWidth;
            endStartBtnWidth = startBtnWidth * 3;

            startTransValue_1 = 0;
            startTransValue_2 = startBtnTransDis;

        } else if (side == signalSide) {
            contentStartScale = 0f;
            contentEndScale = 1f;

            startStartBtnWidth = startBtnWidth * 3;
            endStartBtnWidth = startBtnWidth;

            startTransValue_1 = startBtnTransDis;
            startTransValue_2 = 0;
        }

        ObjectAnimator animator = ObjectAnimator.ofFloat(btmContentLayout, "scaleX", contentStartScale, contentEndScale).setDuration(duration);
        btmContentLayout.setPivotX(0);

        ValueAnimator animator1 = ValueAnimator.ofInt(startStartBtnWidth, endStartBtnWidth).setDuration(duration);
        animator1.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                int width = (int) valueAnimator.getAnimatedValue();
                ViewGroup.LayoutParams params = start.getLayoutParams();
                params.width = width;
                start.setLayoutParams(params);
            }
        });

        ObjectAnimator animator2 = ObjectAnimator.ofFloat(start, "translationX", startTransValue_1, startTransValue_2).setDuration(duration);
        AnimatorSet set = new AnimatorSet();
        set.playTogether(
                animator,
                animator1,
                animator2
        );
        set.start();
    }

    private void showTopContent() {
        topContentLayout.setVisibility(INVISIBLE);
        int distance = topTitleLayout.getWidth();
        ObjectAnimator objectAnimator_1 = ObjectAnimator.ofFloat(topTitleLayout, "translationX", distance, 0).setDuration(duration);
        objectAnimator_1.setStartDelay(0);

        objectAnimator_1.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                if (valueAnimator.getCurrentPlayTime() <= 50) {
                    topContentLayout.setVisibility(VISIBLE);
                }
            }
        });

        ObjectAnimator objectAnimator_2 = ObjectAnimator.ofFloat(topHistoryLayout, "translationX", distance, 0).setDuration(duration);
        objectAnimator_1.setStartDelay(50);
        ObjectAnimator objectAnimator_3 = ObjectAnimator.ofFloat(topAccuracyLayout, "translationX", distance, 0).setDuration(duration);
        objectAnimator_1.setStartDelay(100);
        ObjectAnimator objectAnimator_4 = ObjectAnimator.ofFloat(topFallableLayout, "translationX", distance, 0).setDuration(duration);
        objectAnimator_1.setStartDelay(150);

        AnimatorSet set = new AnimatorSet();
        set.playTogether(
                objectAnimator_1,
                objectAnimator_2,
                objectAnimator_3,
                objectAnimator_4
        );
        set.start();
    }

    public void setListener(OnCardViewListener listener) {
        this.listener = listener;
    }

    public void setTopTitle(String title){
        topTitle.setText(title);
    }

    public void setBtmTitle(String title){
        btmTitle.setText(title);
    }

    public void setTopHistory(String history){
        topHistory.setText(history);
    }

    public void setTopAccuracy(String accuracy){
        topAccuracy.setText(accuracy);
    }

    public void setFallables(String var1, String var2, String var3){
        fallable_1.setText(var1);
        fallable_2.setText(var2);
        fallable_3.setText(var3);
    }

    public void setBtmAccuracy(String accuracy){
        btmAccuracy.setText(accuracy);
    }
    public interface OnCardViewListener {
        public void onClickStart();

        public void onClickFallable(int index, String text);
    }
}
