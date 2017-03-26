package com.zgq.wokao.ui.fragment.impl;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.zgq.wokao.R;
import com.zgq.wokao.action.paper.impl.PaperAction;
import com.zgq.wokao.model.paper.info.IPaperInfo;
import com.zgq.wokao.model.viewdate.QstData;
import com.zgq.wokao.model.viewdate.ScheduleData;
import com.zgq.wokao.ui.adapter.SchedulePagerAdapter;
import com.zgq.wokao.ui.fragment.BaseFragment;
import com.zgq.wokao.ui.presenter.impl.SchedulePresenter;
import com.zgq.wokao.ui.util.DialogUtil;
import com.zgq.wokao.ui.view.IScheduleView;
import com.zgqview.accuracy.ScheduleInfoView;
import com.zgqview.accuracy.ScheduleInfoView.Status.*;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.zgqview.accuracy.ScheduleInfoView.Status.BOTTOM;

public class ScheduleFragment extends BaseFragment implements IScheduleView, View.OnClickListener{

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    @BindView(R.id.schedule_pager)
    ViewPager viewPager;
    private View rootView;
    @BindView(R.id.schedule_info)
    ScheduleInfoView scheduleInfoView;

    private String mParam1;
    private String mParam2;

    private OnScheduleFragmentListener mListener;

    private SchedulePresenter presenter = new SchedulePresenter(this);

    private Status status = Status.SURVEY;

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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView =  inflater.inflate(R.layout.fragment_schedule, container, false);
        ButterKnife.bind(this,rootView);
        //ObjectAnimator.ofFloat(topLayout,"scaleY",0,500).setDuration(0).start();
        presenter.setViewPager();
        return rootView;
    }

    private void initViewPager(){
        
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
    public void setListener() {

    }

    @Override
    public void setViewPager(ArrayList<ScheduleData> scheduleDatas, ArrayList<ArrayList<QstData>> qstDataLists) {
        if (viewPager.getAdapter() == null) {
            viewPager.setAdapter(new SchedulePagerAdapter(scheduleDatas, qstDataLists,
                    new SchedulePagerAdapter.OnViewClickListener() {
                        @Override
                        public void onClickTopLayout(int position) {
                            if (status == Status.SURVEY){
                                showDetail();
                            }
                        }
                    }));
        }else {
            ((SchedulePagerAdapter)viewPager.getAdapter()).setScheduleDatas(scheduleDatas);
            ((SchedulePagerAdapter)viewPager.getAdapter()).setQstDatasList(qstDataLists);
        }
        viewPager.getAdapter().notifyDataSetChanged();
        viewPager.addOnPageChangeListener(new SchedulePageChangeListener());
    }

    @Override
    public void notifyDataChanged() {
        presenter.notifyDataChanged();
    }

    @Override
    public void showDetail() {
        scheduleInfoView.showTop();
        ObjectAnimator.ofFloat(viewPager,"translationY",-500).setDuration(300).start();
    }

    @Override
    public void hideDetail() {
        scheduleInfoView.showBottom();
        ObjectAnimator.ofFloat(viewPager,"translationY",0,500).setDuration(300).start();
    }

    @Override
    public void scheduleInfoChangeData(ScheduleData data) {
        scheduleInfoView.changeContent(data.getAccuracy(),String.valueOf(data.getCountToday())
                ,String.valueOf(data.getCountEveryday()));
    }

    private boolean showFullViewPager = false;

    @Override
    public void changeViewPagerStatus(boolean showFullView) {

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.top_layout:
                if (scheduleInfoView.getStatus() == BOTTOM){
                    showDetail();
                }
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

    public class SchedulePageChangeListener implements ViewPager.OnPageChangeListener{

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            presenter.scheduleInfoChangeData(position);
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }

    public enum Status{
        SURVEY,DETAIL;
    }
}
