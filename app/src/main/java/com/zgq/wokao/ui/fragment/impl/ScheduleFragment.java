package com.zgq.wokao.ui.fragment.impl;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.gigamole.navigationtabstrip.NavigationTabStrip;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;
import com.zgq.wokao.R;
import com.zgq.wokao.Util.ContextUtil;
import com.zgq.wokao.action.paper.impl.PaperAction;
import com.zgq.wokao.model.paper.info.IPaperInfo;
import com.zgq.wokao.ui.adapter.HomePaperAdapter;
import com.zgq.wokao.ui.adapter.HomeScheduleAdapter;
import com.zgq.wokao.ui.fragment.BaseFragment;
import com.zgq.wokao.ui.util.DialogUtil;
import com.zgq.wokao.ui.widget.SlideUp;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.recyclerview.animators.FadeInAnimator;

public class ScheduleFragment extends BaseFragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    @BindView(R.id.schedule_pager)
    ViewPager viewPager;
    private View rootView;

    private String mParam1;
    private String mParam2;

    private ArrayList<IPaperInfo> schedPaperInfos = new ArrayList<>();


    private MyHandler myHandler;

    private DialogUtil.Listener marketListener;

    private OnScheduleFragmentListener mListener;

    private PaperAction paperAction = PaperAction.getInstance();

    public ScheduleFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ScheduleFragment.
     */
    public static ScheduleFragment newInstance(String param1, String param2) {
        ScheduleFragment fragment = new ScheduleFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContext(getActivity());
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        initData();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView =  inflater.inflate(R.layout.fragment_schedule, container, false);
        ButterKnife.bind(this,rootView);
        return rootView;
    }

    public void initData() {
        getRecyclerViewData();
        myHandler = new MyHandler(this);
    }

    private void getRecyclerViewData() {
        schedPaperInfos = (ArrayList<IPaperInfo>) PaperAction.getInstance().getPaperInfosInSchdl();
        Log.d("---->>","all infos"+schedPaperInfos.size());
    }

    public void notifyDataChanged(){
        getRecyclerViewData();
    }

    public void updatePaperInfos() {
        getRecyclerViewData();
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }
    @Override
    protected void onAttachToContext(Context context) {
        if (context instanceof OnScheduleFragmentListener) {
            mListener = (OnScheduleFragmentListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnScheduleFragmentListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public boolean onActivityBackPress() {
        return false;
    }

    /**
     * This interface must be implemented by activities that contain this
     */
    public interface OnScheduleFragmentListener {
        void onFragmentInteraction(Uri uri);
        void goSearch();
        void goQuestionsList(String paperId);
    }

    static class MyHandler extends Handler {
        WeakReference<ScheduleFragment> mWeakActivity;

        public MyHandler(ScheduleFragment fragment) {
            mWeakActivity = new WeakReference<ScheduleFragment>(fragment);
        }

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0X1111:
                    Toast.makeText(mWeakActivity.get().getActivity(),"解析成功",Toast.LENGTH_SHORT).show();
                    mWeakActivity.get().updatePaperInfos();
                    break;
                case 0X1112:
                    Toast.makeText(mWeakActivity.get().getActivity(),"解析错误，请检查文档标题和作者",Toast.LENGTH_SHORT).show();
                    break;
                case 0X1113:
                    Toast.makeText(mWeakActivity.get().getActivity(),"解析错误，请检查文档格式",Toast.LENGTH_SHORT).show();
                    break;
            }
            super.handleMessage(msg);
        }
    }
}
