package com.iqiyi.vr.assistant.module.store.poster;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dl7.drag.DragSlopLayout;
import com.iqiyi.vr.assistant.R;
import com.iqiyi.vr.assistant.adapter.PhotoPagerAdapter;
import com.iqiyi.vr.assistant.api.bean.app.CaptureModel;
import com.iqiyi.vr.assistant.injector.components.DaggerPosterMainComponent;
import com.iqiyi.vr.assistant.injector.modules.PosterMainModule;
import com.iqiyi.vr.assistant.module.base.BaseActivity;
import com.iqiyi.vr.assistant.module.base.IBasePresenter;
import com.iqiyi.vr.assistant.module.base.ILoadDataView;
import com.iqiyi.vr.assistant.widget.PhotoViewPager;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * Created by wangyancong on 2017/9/4.
 */

public class BigPosterActivity extends BaseActivity<IBasePresenter>
        implements ILoadDataView<List<CaptureModel>> {

    private static final String BIG_PHOTO_KEY = "BigPhotoKey";
    private static final String PHOTO_INDEX_KEY = "PhotoIndexKey";

    @BindView(R.id.vp_capture)
    PhotoViewPager vpPhoto;
    @BindView(R.id.drag_layout)
    DragSlopLayout dragLayout;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.toolbar_title)
    TextView tvTitle;
    @BindView(R.id.tip_container)
    LinearLayout tipContainer;

    @Inject
    PhotoPagerAdapter adapter;
    private List<CaptureModel> photoList;
    private int index; // 初始索引
    private boolean isHideToolbar = false; // 是否隐藏 Toolbar
    private boolean isInteract = false;    // 是否和 ViewPager 联动

    private ImageView[] tips;

    public static void launch(Context context, ArrayList<CaptureModel> datas, int index) {
        Intent intent = new Intent(context, BigPosterActivity.class);
        intent.putParcelableArrayListExtra(BIG_PHOTO_KEY, datas);
        intent.putExtra(PHOTO_INDEX_KEY, index);
        context.startActivity(intent);
        ((Activity)context).overridePendingTransition(R.anim.expand_vertical_entry, R.anim.hold);
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_poster;
    }

    @Override
    protected void initInjector() {
        photoList = getIntent().getParcelableArrayListExtra(BIG_PHOTO_KEY);
        index = getIntent().getIntExtra(PHOTO_INDEX_KEY, 0);
        DaggerPosterMainComponent.builder()
                .applicationComponent(getAppComponent())
                .posterMainModule(new PosterMainModule(this, photoList))
                .build()
                .inject(this);
    }

    @Override
    protected void initViews() {
        initToolBar(toolbar, true);
        tvTitle.setText(getString(R.string.big_photo_title));

        vpPhoto.setAdapter(adapter);
        // 设置是否 ViewPager 联动和动画
        dragLayout.interactWithViewPager(isInteract);
        dragLayout.setAnimatorMode(DragSlopLayout.FLIP_Y);
        adapter.setTapListener(new PhotoPagerAdapter.OnTapListener() {
            @Override
            public void onPhotoClick() {
                isHideToolbar = !isHideToolbar;
                if (isHideToolbar) {
                    dragLayout.startOutAnim();
                    toolbar.animate().translationY(-toolbar.getBottom()).setDuration(300);
                } else {
                    dragLayout.startInAnim();
                    toolbar.animate().translationY(0).setDuration(300);
                }
            }
        });

        initTips();

        vpPhoto.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                changeTipsState(position);
            }
        });
    }

    @Override
    protected void updateViews(boolean isRefresh) {
        presenter.getData(isRefresh);
    }

    @Override
    public void loadData(List<CaptureModel> data) {
        adapter.updateData(data);
        vpPhoto.setCurrentItem(index);
        changeTipsState(index);
    }

    private void initTips() {
        tips = new ImageView[photoList.size()];
        for (int i = 0; i < tips.length; i++) {
            ImageView imageView = new ImageView(this);
            imageView.setLayoutParams(new LinearLayout.LayoutParams(10, 10));
            tips[i] = imageView;

            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            lp.rightMargin = 10;
            lp.gravity= Gravity.CENTER;
            tipContainer.addView(imageView, lp);
        }
    }

    private void changeTipsState(int selectItem) {
        for (int i = 0; i < tips.length; i++) {
            if (i == selectItem) {
                tips[i].setImageResource(R.drawable.tip_select);
            } else {
                tips[i].setImageResource(R.drawable.tip_unselect);
            }
        }
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.hold, R.anim.zoom_out_exit);
    }
}
