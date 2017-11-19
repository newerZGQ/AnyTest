package com.zgq.wokao.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.github.ybq.android.spinkit.SpinKitView;
import com.github.ybq.android.spinkit.sprite.Sprite;
import com.zgq.wokao.R;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class EmptyLayout extends FrameLayout {

    public static final int STATUS_HIDE = 1001;
    public static final int STATUS_LOADING = 1;
    public static final int STATUS_NO_NET = 2;
    public static final int STATUS_NO_DATA = 3;
    private Context context;
    private OnRetryListener retryListener;
    private int emptyStatus = STATUS_LOADING;
    private int bgColor;

    @BindView(R.id.tv_net_error)
    TextView tvEmptyMessage;
    @BindView(R.id.rl_empty_container)
    View rlEmptyContainer;
    @BindView(R.id.empty_loading)
    SpinKitView emptyLoading;
    @BindView(R.id.empty_layout)
    FrameLayout emptyLayout;

    public EmptyLayout(@NonNull Context context) {
        this(context, null);
    }

    public EmptyLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.EmptyLayout);
        try {
            bgColor = a.getColor(R.styleable.EmptyLayout_background_color, Color.WHITE);
        } finally {
            a.recycle();
        }
        View.inflate(context, R.layout.layout_empty_loading, this);
        ButterKnife.bind(this);
        emptyLayout.setBackgroundColor(bgColor);
        _switchEmptyView();
    }

    /**
     * 隐藏视图
     */
    public void hide() {
        emptyStatus = STATUS_HIDE;
        _switchEmptyView();
    }

    /**
     * 设置状态
     *
     * @param emptyStatus
     */
    public void setEmptyStatus(@EmptyStatus int emptyStatus) {
        this.emptyStatus = emptyStatus;
        _switchEmptyView();
    }

    /**
     * 获取状态
     *
     * @return 状态
     */
    public int getEmptyStatus() {
        return emptyStatus;
    }

    /**
     * 设置异常消息
     *
     * @param msg 显示消息
     */
    public void setEmptyMessage(String msg) {
        tvEmptyMessage.setText(msg);
    }

    public void hideErrorIcon() {
        tvEmptyMessage.setCompoundDrawables(null, null, null, null);
    }

    public void setLoadingIcon(Sprite d) {
        emptyLoading.setIndeterminateDrawable(d);
    }

    /**
     * 切换视图
     */
    private void _switchEmptyView() {
        switch (emptyStatus) {
            case STATUS_LOADING:
                setVisibility(VISIBLE);
                rlEmptyContainer.setVisibility(GONE);
                emptyLoading.setVisibility(VISIBLE);
                break;
            case STATUS_NO_DATA:
            case STATUS_NO_NET:
                setVisibility(VISIBLE);
                emptyLoading.setVisibility(GONE);
                rlEmptyContainer.setVisibility(VISIBLE);
                break;
            case STATUS_HIDE:
                setVisibility(GONE);
                break;
        }
    }

    /**
     * 设置重试监听器
     *
     * @param retryListener 监听器
     */
    public void setRetryListener(OnRetryListener retryListener) {
        this.retryListener = retryListener;
    }

    @OnClick(R.id.tv_net_error)
    public void onClick() {
        if (retryListener != null) {
            retryListener.onRetry();
        }
    }

    /**
     * 点击重试监听器
     */
    public interface OnRetryListener {
        void onRetry();
    }

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({STATUS_LOADING, STATUS_NO_NET, STATUS_NO_DATA})
    public @interface EmptyStatus {
    }
}
