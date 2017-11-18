package com.zgq.wokaofree.ui.fragment.impl;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zgq.wokaofree.R;
import com.zgq.wokaofree.ui.fragment.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by zhangguoqiang on 2017/10/21.
 */

public class LearningFragment extends BaseFragment {

    @BindView(R.id.toolbar_back)
    TextView back;

    public LearningFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_learning, container, false);
        ButterKnife.bind(this, rootView);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });
        return rootView;
    }

    @Override
    protected void onAttachToContext(Context context) {

    }

    @Override
    public boolean onActivityBackPress() {
        return false;
    }
}
