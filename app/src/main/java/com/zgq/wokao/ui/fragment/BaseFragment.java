package com.zgq.wokao.ui.fragment;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;

/**
 * Created by zgq on 2017/2/11.
 */

public abstract class BaseFragment extends Fragment implements IFragment {

    private Context context;
    @TargetApi(23)
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        onAttachToContext(context);
    }

    /*
 * Deprecated on API 23
 * Use onAttachToContext instead
 */
    @SuppressWarnings("deprecation")
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            onAttachToContext(activity);
        }
    }

    protected abstract void onAttachToContext(Context context);

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void setContext(Context context) {
        this.context = context;
    }

    @Override
    public LayoutInflater getInflater() {
        return LayoutInflater.from(context);
    }

    public abstract boolean onActivityBackPress();

}
