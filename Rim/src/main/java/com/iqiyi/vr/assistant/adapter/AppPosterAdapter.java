package com.iqiyi.vr.assistant.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.iqiyi.vr.assistant.R;
import com.iqiyi.vr.assistant.api.bean.app.CaptureModel;
import com.iqiyi.vr.assistant.module.store.poster.BigPosterActivity;
import com.iqiyi.vr.assistant.util.DefIconFactory;
import com.iqiyi.vr.assistant.util.ImageLoader;
import com.iqiyi.vr.recyclerviewhelper.adapter.BaseQuickAdapter;
import com.iqiyi.vr.recyclerviewhelper.adapter.BaseViewHolder;

import java.util.ArrayList;

/**
 * Created by wangyancong on 2017/9/4.
 * <p>
 * 应用截图适配器
 */

public class AppPosterAdapter extends BaseQuickAdapter<CaptureModel> {
    /**
     * 图片宽度
     */
    private int photoWidth;

    public AppPosterAdapter(Context context) {
        super(context);
        int widthPixels = context.getResources().getDisplayMetrics().widthPixels;
        int marginPixels = context.getResources().getDimensionPixelOffset(R.dimen.capture_margin_width);
        photoWidth = widthPixels / 2 - marginPixels;
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.adapter_poster_list;
    }

    @Override
    protected void convert(final BaseViewHolder holder, CaptureModel item) {
        final ImageView ivCapture = holder.getView(R.id.iv_capture);
        final ViewGroup.LayoutParams params = ivCapture.getLayoutParams();
        params.width = photoWidth;
        ivCapture.setLayoutParams(params);
        ImageLoader.loadFitCenter(context, item.getMedia_url(),
                ivCapture, DefIconFactory.provideIcon());

        holder.getConvertView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BigPosterActivity.launch(context, (ArrayList<CaptureModel>) getData(),
                        holder.getAdapterPosition());
            }
        });
    }
}
