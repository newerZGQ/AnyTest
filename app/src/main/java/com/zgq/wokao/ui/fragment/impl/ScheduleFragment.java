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

    private Status status = Status.SURVEY;

    private int slipDistance;

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
        if (savedInstanceState != null) {
            switch (savedInstanceState.getString("status")) {
                case "SURVEY":
                    status = Status.SURVEY;
                    break;
                case "DETAIL":
                    status = Status.DETAIL;
                    break;
                default:
                    break;
            }
            currentPosition = savedInstanceState.getInt("PagerPosition");
        }
        scheduleInfoView.setOnClickListener(this);
        infoLayout.setOnClickListener(this);
        taskSettingLayout.setOnTaskSettingListener(this);
        presenter.setViewPager();
        presenter.checkSchedulesSize();
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        viewPager.setCurrentItem(currentPosition);
        presenter.scheduleInfoChangeData(currentPosition);
//        scheduleInfoView.post(new Runnable() {
//            @Override
//            public void run() {
//
//            }
//        });
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        switch (status) {
            case SURVEY:
                outState.putString("status", "SURVEY");
                break;
            case DETAIL:
                outState.putString("status", "DETAIL");
                break;
        }
        outState.putInt("PagerPosition", viewPager.getCurrentItem());
    }

    private void initViewPager() {

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
        if (scheduleDatas == null) {
            return;
        }
        viewPager.post(new Runnable() {
            @Override
            public void run() {
                View tmp = viewPager.findViewById(R.id.qst_datial_cards);
                if (tmp == null) {
                    slipDistance = 0;
                } else {
                    slipDistance = tmp.getHeight();
                }
                hideDetail(0);
            }
        });
        if (viewPager.getAdapter() == null) {
            viewPager.setAdapter(new SchedulePagerAdapter(getContext(), scheduleDatas, qstDataLists,
                    new SchedulePagerAdapter.OnViewClickListener() {
                        @Override
                        public void onClickTopLayout(int position) {
                                mListener.goQuestionsList("");
//                            if (status == Status.SURVEY) {
//                                if (mListener != null){
//                                    Log.d(TAG,"mListener not null");
//                                    mListener.onShowQuestionDetail();
//                                }else{
//                                    Log.d(TAG,"mListener null");
//                                }
//                                showDetail(300);
//                            } else {
//                                if (mListener != null){
//                                    mListener.onHideQuestionDetail();
//                                }
//                                hideDetail(300);
//                            }
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
            ((SchedulePagerAdapter) viewPager.getAdapter()).setQstDatasList(qstDataLists);
            viewPager.getAdapter().notifyDataSetChanged();
        }
    }

    @Override
    public void notifyDataChanged() {
        presenter.notifyDataChanged();
        presenter.checkSchedulesSize();
    }

    @Override
    public void showDetail(int duration) {
        scheduleInfoView.showTop(duration);
        ((SchedulePagerAdapter) viewPager.getAdapter()).changeStatus(SchedulePagerAdapter.Status.SHOWSTARTBTN);
        ObjectAnimator.ofFloat(viewPager, "translationY", slipDistance, slipDistance*1/5).setDuration(duration).start();
        adjustViewPager();
        status = Status.DETAIL;
    }

    @Override
    public void hideDetail(int duration) {
        scheduleInfoView.showBottom(duration);
        ((SchedulePagerAdapter) viewPager.getAdapter()).changeStatus(SchedulePagerAdapter.Status.SHOWADDTIME);
        ObjectAnimator.ofFloat(viewPager, "translationY", 0, slipDistance).setDuration(duration).start();
        adjustViewPager();
        status = Status.SURVEY;
    }

    //调整viewpager前一夜后一页的状态为正确的
    private void adjustViewPager() {
        View tmpView = null;
        for (int i = 0; i < viewPager.getChildCount(); i++) {
            if ((tmpView = viewPager.getChildAt(i)) != null) {
                switch (((SchedulePagerAdapter) viewPager.getAdapter()).getStatus()) {
                    case SHOWSTARTBTN:
                        tmpView.findViewById(R.id.start_study).setVisibility(View.VISIBLE);
                        tmpView.findViewById(R.id.add_time).setVisibility(View.GONE);
                        tmpView.findViewById(R.id.top_layout).
                                setBackgroundColor(getContext().getResources().getColor(R.color.transparent));
                        break;
                    case SHOWADDTIME:
                        tmpView.findViewById(R.id.start_study).setVisibility(View.GONE);
                        tmpView.findViewById(R.id.add_time).setVisibility(View.VISIBLE);
                        tmpView.findViewById(R.id.top_layout).
                                setBackgroundColor(getContext().getResources().getColor(R.color.color_top_layout_background));
                        break;
                    default:
                        break;
                }
            }
        }

    }

    //调整viewpager前后两页的qstlist数据显示
//    private void adjustQstList() {
//        int position = viewPager.getCurrentItem();
//        View tmp = null;
//        if ((tmp = viewPager.getChildAt(position + 1)) != null) {
//            ((Overview) tmp.findViewById(R.id.qst_datial_cards)).getTaskStack()
//                    .notifyDataSetChanged(presenter.getQstDataByPosition(position + 1));
//        }
//        if ((tmp = viewPager.getChildAt(position - 1)) != null) {
//            ((Overview) tmp.findViewById(R.id.qst_datial_cards)).getTaskStack()
//                    .notifyDataSetChanged(presenter.getQstDataByPosition(position - 1));
//        }
//
//    }

    //重新设置当前viewpager中的qstlist
    private void setCurrentQstList() {

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
    public void changeViewPagerStatus(boolean showFullView) {

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
        void onFragmentInteraction(Uri uri);
        void goQuestionsList(String paperId);
        void onShowQuestionDetail();
        void onHideQuestionDetail();
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
            //adjustQstList();
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }

    public enum Status {
        SURVEY, DETAIL;
    }
}
