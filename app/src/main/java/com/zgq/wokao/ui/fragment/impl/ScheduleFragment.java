package com.zgq.wokao.ui.fragment.impl;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.LinearLayout;

import com.zgq.wokao.R;
import com.zgq.wokao.model.paper.QuestionType;
import com.zgq.wokao.model.viewdate.ScheduleData;
import com.zgq.wokao.ui.activity.AnswerStudyActivity;
import com.zgq.wokao.ui.adapter.SchedulePagerAdapter;
import com.zgq.wokao.ui.fragment.BaseFragment;
import com.zgq.wokao.ui.presenter.impl.SchedulePresenter;
import com.zgq.wokao.ui.view.IHomeView;
import com.zgq.wokao.ui.view.IScheduleView;
import com.zgq.wokao.ui.widget.ScheduleInfoView;
import com.zgq.wokao.ui.widget.TaskSettingLayout;

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
        initViewPager();
        updateScheduleInfo(presenter.getScheduleInfo(0),false);
        checkPaperCount();
        return rootView;
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
    }

    @Override
    public boolean onActivityBackPress() {
        return false;
    }

    private void initViewPager() {
        viewPager.setAdapter(new SchedulePagerAdapter(getContext(), presenter.getScheduleDatas(),
                new SchedulePagerAdapter.OnViewClickListener() {
                    @Override
                    public void onClickTopLayout(int position) {
                        mListener.goQuestionsList(presenter.getScheduleDatas().get(position).getPaperId());
                    }

                    @Override
                    public void onClickStartBtn(int position, String paperId) {
                        startStudy(paperId, presenter.getLastStudyType(paperId), presenter.getLastStudyPos(paperId));
                    }
                }));
        viewPager.addOnPageChangeListener(new SchedulePageChangeListener());
    }

    private void startStudy(String paperId, QuestionType type, int qstNum) {
        Intent intent = new Intent(getActivity(), AnswerStudyActivity.class);
        if (paperId != null && !paperId.equals("")) {
            intent.putExtra("paperId", paperId);
            intent.putExtra("qstType", (Parcelable) type);
            intent.putExtra("qstNum", qstNum);
        } else {
            return;
        }
        getActivity().startActivity(intent);
    }

    private void updateScheduleInfo(ScheduleData data, boolean withAnimator) {
        if (withAnimator) {
            scheduleInfoView.updateWithAnimator(data.getAccuracy(), String.valueOf(data.getCountToday())
                    , String.valueOf(data.getCountEveryday()));
        }else {
            scheduleInfoView.updateImmediate(data.getAccuracy(), String.valueOf(data.getCountToday())
                    , String.valueOf(data.getCountEveryday()));
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.schedule_info_view:
                break;
            case R.id.schedule_info_layout:
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
        scheduleInfoView.changDailyCount(task, 200);
        presenter.setDailyCount(currentPosition, task);
    }

    private void checkPaperCount() {
        if (presenter.getPaperCount() == 0) {
            onEmptyPapers();
        } else {
            onNoneEmptyPapers();
        }
    }

    private void onEmptyPapers() {
        viewPager.setVisibility(View.GONE);
        scheduleInfoView.setVisibility(View.GONE);
        if (noContentLayout == null) {
            noContentStub.inflate();
            noContentLayout = (LinearLayout) rootView.findViewById(R.id.no_schedule_layout);
        } else {
            noContentLayout.setVisibility(View.VISIBLE);
        }
    }

    private void onNoneEmptyPapers() {
        viewPager.setVisibility(View.VISIBLE);
        scheduleInfoView.setVisibility(View.VISIBLE);
        if (noContentLayout != null) {
            noContentLayout.setVisibility(View.GONE);
        }
    }

    @Override
    public void onPaperDataChanged() {
        presenter.updateDatas();
        checkPaperCount();
        ((SchedulePagerAdapter) viewPager.getAdapter()).setScheduleDatas(presenter.getScheduleDatas());
        viewPager.getAdapter().notifyDataSetChanged();
        viewPager.setCurrentItem(0);
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
            updateScheduleInfo(presenter.getScheduleInfo(position),true);
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }
}
