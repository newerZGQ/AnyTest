package com.zgq.wokao.ui.fragment.impl;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.LinearLayout;

import com.zgq.wokao.R;
import com.zgq.wokao.Util.LogUtil;
import com.zgq.wokao.model.paper.QuestionType;
import com.zgq.wokao.model.viewdate.QstData;
import com.zgq.wokao.model.viewdate.ScheduleData;
import com.zgq.wokao.ui.activity.AnswerStudyActivity;
import com.zgq.wokao.ui.adapter.SchedulePagerAdapter;
import com.zgq.wokao.ui.fragment.BaseFragment;
import com.zgq.wokao.ui.presenter.impl.SchedulePresenter;
import com.zgq.wokao.ui.view.IHomeView;
import com.zgq.wokao.ui.view.IScheduleView;
import com.zgq.wokao.ui.widget.ScheduleInfoView;
import com.zgq.wokao.ui.widget.TaskSettingLayout;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ScheduleFragment extends BaseFragment implements IScheduleView, View.OnClickListener
        , TaskSettingLayout.OnTaskSettingListener {
    public static final String TAG = "ScheduleFragment";

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    @BindView(R.id.schedule_pager)
    ViewPager viewPager;
    private View rootView;
    @BindView(R.id.schedule_info_view)
    ScheduleInfoView scheduleInfoView;
    @BindView(R.id.no_schedule_layout)
    ViewStub noContentStub;
    @BindView(R.id.schedule_info_layout)
    LinearLayout infoLayout;
    @BindView(R.id.task_setting_layout)
    TaskSettingLayout taskSettingLayout;

    LinearLayout noContentLayout;

    private String mParam1;
    private String mParam2;

    private OnScheduleFragmentListener mListener;

    private SchedulePresenter presenter;

    private int currentPosition;

    public ScheduleFragment() {
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
        presenter = new SchedulePresenter(this, getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_schedule, container, false);
        ButterKnife.bind(this, rootView);
        scheduleInfoView.setOnClickListener(this);
        infoLayout.setOnClickListener(this);
        taskSettingLayout.setOnTaskSettingListener(this);
        setViewPager(presenter.getScheduleDatas());
        presenter.checkSchedulesSize();
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        viewPager.setCurrentItem(currentPosition);
        presenter.scheduleInfoChangeData(currentPosition);
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
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

    public void setViewPager(ArrayList<ScheduleData> scheduleDatas) {
        if (scheduleDatas == null) {
            return;
        }
        viewPager.post(new Runnable() {
            @Override
            public void run() {
                scheduleInfoView.showBottom(0);
                ((SchedulePagerAdapter) viewPager.getAdapter()).changeStatus(SchedulePagerAdapter.Status.SHOWADDTIME);
            }
        });
        if (viewPager.getAdapter() == null) {
            viewPager.setAdapter(new SchedulePagerAdapter(getContext(), scheduleDatas,
                    new SchedulePagerAdapter.OnViewClickListener() {
                        @Override
                        public void onClickTopLayout(int position) {
                                mListener.goQuestionsList("");
                        }

                        @Override
                        public void onClickQuestionType(String paperId, QuestionType type) {
                            startStudy(paperId,type.getIndex(),0);
                        }

                        @Override
                        public void onClickSpeQuestion(String paperId, QuestionType type, int questionIndex) {
                            startStudy(paperId,type.getIndex(),questionIndex);
                        }
                    }));

            viewPager.addOnPageChangeListener(new SchedulePageChangeListener());
        } else {
            ((SchedulePagerAdapter) viewPager.getAdapter()).setScheduleDatas(scheduleDatas);
            viewPager.getAdapter().notifyDataSetChanged();
        }
    }

    @Override
    public void notifyDataChanged() {
        presenter.notifyDataChanged();
        presenter.checkSchedulesSize();
    }

    private void startStudy(String paperId, int type, int qstNum){
        Intent intent = new Intent(getActivity(),AnswerStudyActivity.class);
        if (paperId != null && !paperId.equals("")) {
            intent.putExtra("paperId", paperId);
            intent.putExtra("qstType", type);
            intent.putExtra("qstNum",qstNum);
        }else {
            return;
        }
        getActivity().startActivity(intent);
    }

    @Override
    public void scheduleInfoChangeData(ScheduleData data) {
        scheduleInfoView.changeContent(data.getAccuracy(), String.valueOf(data.getCountToday())
                , String.valueOf(data.getCountEveryday()));
    }

    @Override
    public void onEmptyPapers() {
        viewPager.setVisibility(View.GONE);
        scheduleInfoView.setVisibility(View.GONE);
        if (noContentLayout == null) {
            noContentStub.inflate();
            noContentLayout = (LinearLayout) rootView.findViewById(R.id.no_schedule_layout);
        } else {
            noContentLayout.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onNoneEmptyPapers() {
        viewPager.setVisibility(View.VISIBLE);
        scheduleInfoView.setVisibility(View.VISIBLE);
        if (noContentLayout != null) {
            noContentLayout.setVisibility(View.GONE);
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.schedule_info_view:
                Log.d(LogUtil.PREFIX, TAG + " click schedule info view");
                break;
            case R.id.schedule_info_layout:
                Log.d(TAG,"click inof layo");
                taskSettingLayout.show();
            default:
                break;
        }
    }

    @Override
    public void onHide() {
        ((IHomeView) getActivity()).setViewPagerScrollble(true);
    }

    @Override
    public void onshow() {
        ((IHomeView) getActivity()).setViewPagerScrollble(false);
    }

    @Override
    public void onTaskSelected(int task) {
        Log.d(TAG,"task = " + task);
        scheduleInfoView.changDailyCount(task,200);
        presenter.setDailyCount(currentPosition,task);
    }

    /**
     * This interface must be implemented by activities that contain this
     */
    public interface OnScheduleFragmentListener {
        void goQuestionsList(String paperId);
    }

    public class SchedulePageChangeListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        }

        @Override
        public void onPageSelected(int position) {
            currentPosition = position;
            presenter.scheduleInfoChangeData(position);
            ((SchedulePagerAdapter) viewPager.getAdapter()).changeStatus(((SchedulePagerAdapter) viewPager.getAdapter()).getStatus());
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }
}
