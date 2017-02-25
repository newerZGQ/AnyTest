package com.zgq.wokao.ui.fragment;

import android.app.Fragment;
import android.content.Context;
import android.view.LayoutInflater;

/**
 * Created by zgq on 2017/2/11.
 */

public abstract class BaseFragment extends Fragment implements IFragment {

    private Context context;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

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
