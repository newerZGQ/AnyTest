package com.iqiyi.vr.assistant.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.github.ybq.android.spinkit.SpinKitView;
import com.iqiyi.vr.assistant.R;
import com.iqiyi.vr.assistant.api.bean.app.CaptureModel;
import com.iqiyi.vr.assistant.util.ImageLoader;

import java.util.Collections;
import java.util.List;

import lombok.Setter;
import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * Created by wangyancong on 2017/9/5.
 * 图片浏览适配器
 */

public class PhotoPagerAdapter extends PagerAdapter {

    private List<CaptureModel> photoList;
    private Context context;
    @Setter
    private OnTapListener tapListener;

    public PhotoPagerAdapter(Context context) {
        this.context = context;
        this.photoList = Collections.EMPTY_LIST;
    }

    public void updateData(List<CaptureModel> photoList) {
        this.photoList = photoList;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return photoList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.adapter_poster_pager, null, false);
        final PhotoView photo = view.findViewById(R.id.iv_photo);
        final SpinKitView loadingView = view.findViewById(R.id.loading_view);
        final TextView tvReload = view.findViewById(R.id.tv_reload);

        final RequestListener<String, GlideDrawable> requestListener
                = new RequestListener<String, GlideDrawable>() {
            @Override
            public boolean onException(Exception e, String model,
                                       Target<GlideDrawable> target, boolean isFirstResource) {
                loadingView.setVisibility(View.GONE);
                tvReload.setVisibility(View.VISIBLE);
                return false;
            }

            @Override
            public boolean onResourceReady(GlideDrawable resource,
                                           String model, Target<GlideDrawable> target,
                                           boolean isFromMemoryCache, boolean isFirstResource) {
                loadingView.setVisibility(View.GONE);
                tvReload.setVisibility(View.GONE);
                photo.setImageDrawable(resource);
                return true;
            }
        };
        ImageLoader.loadCenterCrop(context,
                photoList.get(position % photoList.size()).getMedia_url(),
                photo, requestListener);
        photo.setOnPhotoTapListener(new PhotoViewAttacher.OnPhotoTapListener() {
            @Override
            public void onPhotoTap(View view, float x, float y) {
                if (tapListener != null) {
                    tapListener.onPhotoClick();
                }
            }
        });
        tvReload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tvReload.setVisibility(View.GONE);
                loadingView.setVisibility(View.VISIBLE);
                ImageLoader.loadCenterCrop(context,
                        photoList.get(position % photoList.size()).getMedia_url(),
                        photo, requestListener);
            }
        });

        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    public interface OnTapListener {
        void onPhotoClick();
    }
}
