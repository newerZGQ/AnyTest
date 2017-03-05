package com.zgq.wokao.ui.fragment.impl;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.zgq.wokao.R;
import com.zgq.wokao.action.paper.impl.PaperAction;
import com.zgq.wokao.model.paper.info.IPaperInfo;
import com.zgq.wokao.model.viewdate.ScheduleData;
import com.zgq.wokao.ui.fragment.BaseFragment;
import com.zgq.wokao.ui.presenter.impl.SchedulePresenter;
import com.zgq.wokao.ui.util.DialogUtil;
import com.zgq.wokao.ui.view.IScheduleView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ScheduleFragment extends BaseFragment implements IScheduleView, View.OnClickListener{

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    @BindView(R.id.schedule_pager)
    ViewPager viewPager;
    @BindView(R.id.schedule_start)
    Button startBtn;
    private View rootView;

    private String mParam1;
    private String mParam2;

    private ArrayList<IPaperInfo> schedPaperInfos = new ArrayList<>();


//    private MyHandler myHandler;

    private DialogUtil.Listener marketListener;

    private OnScheduleFragmentListener mListener;

    private SchedulePresenter presenter = new SchedulePresenter(this);

    public ScheduleFragment() {}

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
//        initData();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView =  inflater.inflate(R.layout.fragment_schedule, container, false);
        ButterKnife.bind(this,rootView);
        return rootView;
    }

//    public void initData() {
//        getRecyclerViewData();
//        myHandler = new MyHandler(this);
//    }

    private void getRecyclerViewData() {
        schedPaperInfos = (ArrayList<IPaperInfo>) PaperAction.getInstance().getPaperInfosInSchdl();
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

    @Override
    public void onStartBtnClick() {
        presenter.onStartBtnClick();
    }

    @Override
    public void setListener() {
        startBtn.setOnClickListener(this);
    }

    @Override
    public void setViewPager(ArrayList<ScheduleData> scheduleDatas) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.schedule_start:
                presenter.onStartBtnClick();
                break;
            default:
                break;
        }
    }

    /**
     * This interface must be implemented by activities that contain this
     */
    public interface OnScheduleFragmentListener {
        void onFragmentInteraction(Uri uri);
        void goQuestionsList(String paperId);
    }

//    static class MyHandler extends Handler {
//        WeakReference<ScheduleFragment> mWeakActivity;
//
//        public MyHandler(ScheduleFragment fragment) {
//            mWeakActivity = new WeakReference<ScheduleFragment>(fragment);
//        }
//
//        @Override
//        public void handleMessage(Message msg) {
//            switch (msg.what) {
//                case 0X1111:
//                    Toast.makeText(mWeakActivity.get().getActivity(),"解析成功",Toast.LENGTH_SHORT).show();
//                    mWeakActivity.get().updatePaperInfos();
//                    break;
//                case 0X1112:
//                    Toast.makeText(mWeakActivity.get().getActivity(),"解析错误，请检查文档标题和作者",Toast.LENGTH_SHORT).show();
//                    break;
//                case 0X1113:
//                    Toast.makeText(mWeakActivity.get().getActivity(),"解析错误，请检查文档格式",Toast.LENGTH_SHORT).show();
//                    break;
//            }
//            super.handleMessage(msg);
//        }
//    }
}