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
import com.zgq.wokao.Util.NormalExamPaperUtil;
import com.zgq.wokao.data.realm.Paper.PaperDataProvider;
import com.zgq.wokao.model.paper.ExamPaperInfo;
import com.zgq.wokao.model.paper.NormalExamPaper;
import com.zgq.wokao.ui.adapter.HomePaperAdapter;
import com.zgq.wokao.ui.adapter.HomeScheduleAdapter;
import com.zgq.wokao.ui.fragment.BaseFragment;
import com.zgq.wokao.ui.util.DialogUtil;
import com.zgq.wokao.ui.view.SlideUp;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import jp.wasabeef.recyclerview.animators.FadeInAnimator;

public class HomeFragment extends BaseFragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private View rootView;
    private RelativeLayout mainLayout;
    private RelativeLayout menuLayout;
    public Button menuBtn;
    private Button searchBtn;
    private SlideUp slideUp;
    private TextView titleTv;
    private RecyclerView paperList;
    private RecyclerView scheduleList;
    private NavigationTabStrip tabStrip;
    private ViewPager viewPager;

    private String mParam1;
    private String mParam2;

    private Realm realm = Realm.getDefaultInstance();
    private ArrayList<ExamPaperInfo> allPaperInfos = new ArrayList<>();
    private ArrayList<ExamPaperInfo> schedPaperInfos = new ArrayList<>();


    private MyHandler myHandler;

    private DialogUtil.Listener marketListener;

    private OnHomeFragmentListener mListener;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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
        return initView(inflater,container,savedInstanceState);
    }

    public void initData() {
        getRecyclerViewData();
        myHandler = new MyHandler(this);
    }

    private void getRecyclerViewData() {
        allPaperInfos = (ArrayList<ExamPaperInfo>) PaperDataProvider.getInstance().getAllPaperInfo();
        schedPaperInfos = (ArrayList<ExamPaperInfo>) PaperDataProvider.getInstance().getSchedulePapers();
        Log.d("---->>paapers",""+allPaperInfos.size());
    }

    private View initView(LayoutInflater inflater, ViewGroup container,
                          Bundle savedInstanceState){
        rootView =  inflater.inflate(R.layout.fragment_home, container, false);
        mainLayout = (RelativeLayout) rootView.findViewById(R.id.main_layout);
        menuLayout = (RelativeLayout) rootView.findViewById(R.id.menu_layout);
        menuBtn = (Button) rootView.findViewById(R.id.toolbar_menu);
        searchBtn = (Button) rootView.findViewById(R.id.toolbar_search);
        titleTv = (TextView) rootView.findViewById(R.id.toolbar_title);
        tabStrip = (NavigationTabStrip) rootView.findViewById(R.id.home_tab);
        viewPager = (ViewPager) rootView.findViewById(R.id.home_pager);
        initTabStrip();
        initSlideUp();
        initListener();
        initViewPager();

        return rootView;
    }

    private void initTabStrip(){
        tabStrip.setTitles("试卷", "日程");
        tabStrip.setTabIndex(0, true);
        tabStrip.setTitleSize(40);
        tabStrip.setStripColor(Color.RED);
        tabStrip.setStripWeight(10);
        tabStrip.setStripFactor(0.5f);
        tabStrip.setStripType(NavigationTabStrip.StripType.LINE);
        tabStrip.setStripGravity(NavigationTabStrip.StripGravity.BOTTOM);
        tabStrip.setTypeface("fonts/typeface.ttf");
        tabStrip.setCornersRadius(3);
        tabStrip.setAnimationDuration(300);
        tabStrip.setInactiveColor(Color.GRAY);
        tabStrip.setActiveColor(Color.WHITE);
    }

    private void initSlideUp(){
        slideUp = new SlideUp.Builder(menuLayout)
                .withListeners(new SlideUp.Listener(){
                    @Override
                    public void onSlide(float percent) {
                        if (percent == 0.0) return;
                        ObjectAnimator.ofFloat(mainLayout, "translationY", menuLayout.getHeight()*(1-percent/100)).
                                setDuration(0).start();
                    }

                    @Override
                    public void onVisibilityChanged(int visibility) {
                        if (visibility == View.GONE){
                            titleTv.setText("试卷工厂");
                        }else{
                            titleTv.setText("设置");
                        }
                    }
                })
                .withStartState(SlideUp.State.HIDDEN)
                .withStartGravity(Gravity.TOP)
                .build();
    }

    private void initViewPager(){
        final List<View> viewList = new ArrayList<>();
        View papersPage = getInflater().inflate(R.layout.viewpager_layout,null);
        View schedulePage = getInflater().inflate(R.layout.viewpager_layout,null);
        viewList.add(papersPage);
        viewList.add(schedulePage);

        paperList = (RecyclerView) papersPage.findViewById(R.id.recycler_view);
        paperList.addItemDecoration(
                new HorizontalDividerItemDecoration.Builder(getActivity())
                        .color(getResources().getColor(R.color.colorRecyclerViewDivider))
                        .size(getResources().getDimensionPixelSize(R.dimen.paper_recyclerview_divider_height))
                        .margin(getResources().getDimensionPixelSize(R.dimen.paper_recyclerview_divider_margin), 0)
                        .build());
        RecyclerView.LayoutManager papersLM = new LinearLayoutManager(ContextUtil.getContext());
        paperList.setLayoutManager(papersLM);
        paperList.setItemAnimator(new FadeInAnimator());
        paperList.setAdapter(new HomePaperAdapter(allPaperInfos, new HomePaperAdapter.PaperAdapterListener() {
            @Override
            public void onStared(String paperId, boolean isStared) {

            }

            @Override
            public void onItemClick(int position) {

            }

            @Override
            public void onItemLongClick(int position) {

            }
        }));

        scheduleList = (RecyclerView) schedulePage.findViewById(R.id.recycler_view);
        scheduleList.addItemDecoration(new HorizontalDividerItemDecoration.Builder(getActivity())
                .color(getResources().getColor(R.color.colorRecyclerViewDivider))
                .size(getResources().getDimensionPixelSize(R.dimen.paper_recyclerview_divider_height))
                .margin(getResources().getDimensionPixelSize(R.dimen.paper_recyclerview_divider_margin), 0)
                .build());
        RecyclerView.LayoutManager scheduleLM = new LinearLayoutManager(ContextUtil.getContext());
        scheduleList.setLayoutManager(scheduleLM);
        scheduleList.setItemAnimator(new FadeInAnimator());
        scheduleList.setAdapter(new HomeScheduleAdapter(allPaperInfos,new HomeScheduleAdapter.ScheduleAdapterListener(){

            @Override
            public void onStatusChanged(boolean isOpened) {

            }

            @Override
            public void onDailyCountChanged(int oldCount, int newCount) {

            }

            @Override
            public void onItemClick(int position) {

            }

            @Override
            public void onItemLongClick(int position) {

            }
        }));

        PagerAdapter adapter = new PagerAdapter() {
            @Override
            public int getCount() {
                return viewList.size();
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }


            @Override
            public void destroyItem(ViewGroup container, int position,
                                    Object object) {
                // TODO Auto-generated method stub
                container.removeView(viewList.get(position));
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                // TODO Auto-generated method stub
                container.addView(viewList.get(position));
                return viewList.get(position);
            }
        };

        viewPager.setAdapter(adapter);
        tabStrip.setViewPager(viewPager);
    }

    private void initListener(){
        menuBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (slideUp.isVisible()) {
                    slideUp.hide();
                }else {
                    slideUp.show();
                }
            }
        });
    }

    public void updatePaperInfos() {
        getRecyclerViewData();
        paperList.getAdapter().notifyDataSetChanged();
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnHomeFragmentListener) {
            mListener = (OnHomeFragmentListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnHomeFragmentListener");
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
    public interface OnHomeFragmentListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    static class MyHandler extends Handler {
        WeakReference<HomeFragment> mWeakActivity;

        public MyHandler(HomeFragment fragment) {
            mWeakActivity = new WeakReference<HomeFragment>(fragment);
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
