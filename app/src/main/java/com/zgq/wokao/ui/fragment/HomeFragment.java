package com.zgq.wokao.ui.fragment;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;
import com.zgq.wokao.R;
import com.zgq.wokao.Util.DrawableUtil;
import com.zgq.wokao.Util.NormalExamPaperUtil;
import com.zgq.wokao.data.realm.Paper.PaperDataProvider;
import com.zgq.wokao.model.paper.ExamPaperInfo;
import com.zgq.wokao.model.paper.NormalExamPaper;
import com.zgq.wokao.ui.HomeActivity;
import com.zgq.wokao.ui.MainActivity;
import com.zgq.wokao.ui.QuestionsListActivity;
import com.zgq.wokao.ui.util.DialogUtil;
import com.zgq.wokao.ui.view.RotateTextView;
import com.zgq.wokao.ui.view.SlideUp;

import org.w3c.dom.Text;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.zip.Inflater;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import jp.wasabeef.recyclerview.animators.FadeInAnimator;

public class HomeFragment extends BaseFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private View rootView;
    private RelativeLayout mainLayout;
    private RelativeLayout menuLayout;
    public Button menuBtn;
    private Button searchBtn;
    private SlideUp slideUp;
    private TextView titleTv;
    private RecyclerView paperListView;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private Realm realm = Realm.getDefaultInstance();
    private ArrayList<ExamPaperInfo> paperInfos = new ArrayList<>();
    private ArrayList<NormalExamPaper> papers = new ArrayList<>();

    private PaperInfoAdapter adapter;

    private MyHandler myHandler;

    private DialogUtil.Listener marketListener;

    private OnFragmentInteractionListener mListener;

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
    // TODO: Rename and change types and number of parameters
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
        papers = (ArrayList<NormalExamPaper>) PaperDataProvider.getInstance().getAllPaper();
        NormalExamPaperUtil.sortPapers(papers);
        paperInfos.clear();
        for (NormalExamPaper paper : papers) {
            paperInfos.add(paper.getPaperInfo());
        }
    }

    private View initView(LayoutInflater inflater, ViewGroup container,
                          Bundle savedInstanceState){
        rootView =  inflater.inflate(R.layout.fragment_home, container, false);
        mainLayout = (RelativeLayout) rootView.findViewById(R.id.main_layout);
        menuLayout = (RelativeLayout) rootView.findViewById(R.id.menu_layout);
        menuBtn = (Button) rootView.findViewById(R.id.toolbar_menu);
        searchBtn = (Button) rootView.findViewById(R.id.toolbar_search);
        titleTv = (TextView) rootView.findViewById(R.id.toolbar_title);
        paperListView = (RecyclerView) rootView.findViewById(R.id.recy_exam_paper);
        initSlideUp();
        initListener();
        initPaperListView();
        return rootView;
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

    private void initPaperListView(){
        paperListView.addItemDecoration(
                new HorizontalDividerItemDecoration.Builder(getActivity())
                        .color(getResources().getColor(R.color.colorRecyclerViewDivider))
                        .size(getResources().getDimensionPixelSize(R.dimen.paper_recyclerview_divider_height))
                        .margin(getResources().getDimensionPixelSize(R.dimen.paper_recyclerview_divider_margin), 0)
                        .build());
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        paperListView.setLayoutManager(layoutManager);
        paperListView.setItemAnimator(new FadeInAnimator());
        adapter = new PaperInfoAdapter(paperInfos);
        paperListView.setAdapter(adapter);
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
        adapter.initData();
        adapter.notifyDataSetChanged();
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
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
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
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
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

    //recyclerView adapter
    public class PaperInfoAdapter extends RecyclerView.Adapter {
        private ArrayList<ExamPaperInfo> paperInfos = null;
        private ArrayList<Boolean> isItemSelectedList = new ArrayList<>();
        private ArrayList<Integer> colorlabelList = null;
        private final int GROUPTYPE = 1;
        private final int ITEMTYPE = 2;
        private int viewType = GROUPTYPE;

        public PaperInfoAdapter(ArrayList<ExamPaperInfo> paperInfos) {
            this.paperInfos = paperInfos;
            initData();
        }

        private void initData() {
            initIsItemSelectedList();
            initColorLabelList();
        }

        private void initIsItemSelectedList(){
            isItemSelectedList.clear();
            for (int i = 0; i < paperInfos.size(); i++) {
                isItemSelectedList.add(false);
            }
        }

        private void initColorLabelList(){
            colorlabelList = DrawableUtil.getCircleDrawableSet(paperInfos.size());
        }


        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

            if (paperInfos.size() == 0 || paperInfos == null) return;

            final PaperInfoAdapter.PaperInfoViewHolder holder1 = (PaperInfoViewHolder) holder;
            final ExamPaperInfo info = (ExamPaperInfo) paperInfos.get(holder1.getAdapterPosition());
            //设置paperlabel的正面以及反面
            holder1.paperLabel.setSidesStyle(new RotateTextView.UpAndDownSideStyle() {
                @Override
                public void setUpSideStyle() {
                    holder1.paperLabel.setText(info.getTitle().substring(0, 1));
                    holder1.paperLabel.setBackground(getResources().getDrawable(colorlabelList.get(position)));
                    holder1.item.setBackgroundColor(getResources().getColor(R.color.colorWhite));
                }

                @Override
                public void setDownSideStyle() {
                    holder1.paperLabel.setText("");
                    holder1.paperLabel.setBackground(getResources().getDrawable(R.drawable.circle_background_downside_right_icon));
                    holder1.item.setBackgroundColor(getResources().getColor(R.color.colorRecyclerViewItemSelectedBackGround));
                }
            });
            //title 以及label的初始显示
            if (info.getTitle() != null) {
                holder1.paperName.setText(info.getTitle());
            }
            if (!isItemSelectedList.get(position)) {
                holder1.paperLabel.setCurrentSide(RotateTextView.UPSIDE);
                holder1.paperLabel.setText(info.getTitle().substring(0, 1));
                holder1.paperLabel.setBackground(getResources().getDrawable(colorlabelList.get(position)));
                holder1.item.setBackgroundColor(getResources().getColor(R.color.colorWhite));
            } else {
                holder1.paperLabel.setCurrentSide(RotateTextView.DOWNSIDE);
                holder1.paperLabel.setText("√");
                holder1.paperLabel.setBackground(getResources().getDrawable(R.drawable.circle_background_downside));
                holder1.item.setBackgroundColor(getResources().getColor(R.color.colorRecyclerViewItemSelectedBackGround));
            }
            //作者信息初始显示
            if (info.getAuthor() != null) {
                holder1.paperAuthorAndDate.setText(getAuthorAndData(info));
            }
            //是否收藏信息显示
            if (!info.isStared()) {
                holder1.paperStar.setBackground(getResources().getDrawable(R.drawable.inactive_star));
            } else {
                holder1.paperStar.setBackground(getResources().getDrawable(R.drawable.active_star));
            }
            holder1.paperStar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (info.isStared()){
                        realm.executeTransaction(new Realm.Transaction() {
                            @Override
                            public void execute(Realm realm) {
                                info.setStared(false);
                            }
                        });
                        holder1.paperStar.setBackground(getResources().getDrawable(R.drawable.inactive_star));
                        Toast.makeText(getActivity(),"取消收藏",Toast.LENGTH_SHORT).show();
                    }else{
                        realm.executeTransaction(new Realm.Transaction() {
                            @Override
                            public void execute(Realm realm) {
                                info.setStared(true);
                            }
                        });
                        holder1.paperStar.setBackground(getResources().getDrawable(R.drawable.active_star));
                        Toast.makeText(getActivity(),"已收藏",Toast.LENGTH_SHORT).show();
                    }
                }
            });
            //paperlabel的单击事件
            holder1.paperLabel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    holder1.paperLabel.changeSide();
                    if ((holder1.paperLabel).getCurrentSide() == RotateTextView.UPSIDE){
                        isItemSelectedList.set(position, false);
                    }else{
                        isItemSelectedList.set(position, true);
                    }
                }
            });
            //recyclerView item单击事件
            holder1.item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.setClass(getActivity(), QuestionsListActivity.class);
                    intent.putExtra("paperTitle", info.getTitle());
                    intent.putExtra("paperAuthor", info.getAuthor());
                    startActivity(intent);
                }
            });
            //recyclerView item长按事件
            holder1.item.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    holder1.paperLabel.changeSide();
                    if ((holder1.paperLabel).getCurrentSide() == RotateTextView.UPSIDE){
                        isItemSelectedList.set(position, false);
                    }else{
                        isItemSelectedList.set(position, true);
                    }
                    return true;
                }
            });

            switch (holder.getItemViewType()) {
                case ITEMTYPE:
                    break;
                case GROUPTYPE:
                    holder1.groupTitle.setVisibility(View.VISIBLE);
                    if (position == 0) {
                        holder1.groupTitle.setText("最近");
                    }else{
                        holder1.groupTitle.setText("其他");
                    }
                    break;
            }
        }

        @Override
        public int getItemViewType(int position) {
            if (position == 0 || position == 3) {
                return GROUPTYPE;
            } else {
                return ITEMTYPE;
            }
        }

        @Override
        public int getItemCount() {
            return paperInfos.size();
        }



        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_main_recyclerview_item, parent, false);
            return new PaperInfoAdapter.PaperInfoViewHolder(view);
        }

        public void removeSpecificPosition(int position){
            paperInfos.remove(position);
            isItemSelectedList.remove(position);
            colorlabelList.remove(position);
            notifyItemRemoved(position);
        }

        private void releaseSelectedView() {
            for (int i = 0; i< isItemSelectedList.size(); i++){
                isItemSelectedList.set(i,false);
            }
            notifyDataSetChanged();
        }

        private String getAuthorAndData(ExamPaperInfo info) {
            String author = info.getAuthor();
            String lastDate = info.getLastStudyDate();
            if (author == null || author.equals("")) author = "      ";
            if (lastDate == null || lastDate.equals("")) {
                lastDate = "未学习";
            } else {
            }
            return author;
        }


        public class PaperInfoViewHolder extends RecyclerView.ViewHolder {
            public TextView paperName;
            public RotateTextView paperLabel;
            public TextView paperAuthorAndDate;
            public RelativeLayout item;
            public TextView paperStar;
            public TextView groupTitle;

            public PaperInfoViewHolder(View itemView) {
                super(itemView);
                item = (RelativeLayout) itemView.findViewById(R.id.list_item);
                paperName = (TextView) itemView.findViewById(R.id.paper_name);
                paperLabel = (RotateTextView) itemView.findViewById(R.id.question_label);
                paperLabel.setClickable(true);
                paperAuthorAndDate = (TextView) itemView.findViewById(R.id.auther);
                paperStar = (TextView) itemView.findViewById(R.id.activity_main_paper_star_tv);
                groupTitle = (TextView) itemView.findViewById(R.id.group_title);
            }
        }
    }


}
