package com.zgq.wokao.ui.fragment;

import android.app.Fragment;
import android.content.Context;

/**
 * Created by zgq on 2017/2/11.
 */

public abstract class BaseFragment extends Fragment {


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    public abstract boolean onActivityBackPress();

}
