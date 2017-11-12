package com.iqiyi.vr.assistant.adapter;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.flyco.labelview.LabelView;
import com.iqiyi.vr.assistant.R;
import com.iqiyi.vr.assistant.api.bean.app.AppModel;
import com.iqiyi.vr.assistant.module.store.detail.AppDetailActivity;
import com.iqiyi.vr.assistant.module.store.pay.AppPayActivity;
import com.iqiyi.vr.assistant.util.DefIconFactory;
import com.iqiyi.vr.assistant.util.ImageLoader;
import com.iqiyi.vr.assistant.util.MeasureUtil;
import com.iqiyi.vr.assistant.widget.RippleView;
import com.iqiyi.vr.recyclerviewhelper.adapter.BaseQuickAdapter;
import com.iqiyi.vr.recyclerviewhelper.adapter.BaseViewHolder;

/**
 * Created by wangyancong on 2017/8/31.
 * <p>
 * 商店列表适配器
 */

public class AppListAdapter extends BaseQuickAdapter<AppModel> {

    public AppListAdapter(Context context) {
        super(context);
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.adapter_app_list;
    }

    @Override
    protected void convert(BaseViewHolder holder, final AppModel item) {
        ImageView appCapture = holder.getView(R.id.iv_capture);
        ImageLoader.loadFitCenter(context, item.getApp_logo(),
                appCapture, DefIconFactory.provideIcon());

        ImageView appIcon = holder.getView(R.id.iv_icon);
        ImageLoader.loadCenterCrop(context, item.getApp_logo(),
                appIcon, DefIconFactory.provideIcon());

        holder.setText(R.id.tv_name, item.getApp_name())
                .setText(R.id.tv_type, getType(item.getApp_type()))
                .setText(R.id.tv_size, MeasureUtil.convertSize(item.getApp_package_size())
                        + context.getString(R.string.size_m));
        //设置标签
        LabelView labelView = holder.getView(R.id.label_view);
        labelView.setVisibility(View.VISIBLE);
        labelView.setText("最新推荐");

        Button appPay = holder.getView(R.id.btn_pay);
        setPayState(appPay, item.getApp_state(), item.getApp_price());
        holder.setOnClickListener(R.id.btn_pay, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppPayActivity.launchForResult(context, item.getQipu_id());
            }
        });

        //波纹效果
        RippleView rippleLayout = holder.getView(R.id.item_ripple);
        rippleLayout.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {
            @Override
            public void onComplete(RippleView rippleView) {
                AppDetailActivity.launch(context, item.getQipu_id(), item.getApp_state(),
                        item.getApp_package_name(), item.getLatest_version());
            }
        });
    }

    private String getType(byte type) {
        switch (type) {
            case 0:
                return context.getString(R.string.type_game);
            default:
                return context.getString(R.string.type_app);
        }
    }

    private void setPayState(Button button, int state, double price) {
        switch (state) {
            case 0:
                button.setText(String.valueOf(price/100));
                break;
            default:
                button.setText(context.getString(R.string.wait_pay_state));
                break;
        }
    }
}
