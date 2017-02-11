package com.zgq.wokao.ui;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.nbsp.materialfilepicker.MaterialFilePicker;
import com.nbsp.materialfilepicker.ui.FilePickerActivity;
import com.umeng.analytics.MobclickAgent;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;
import com.zgq.wokao.R;
import com.zgq.wokao.Util.DrawableUtil;
import com.zgq.wokao.Util.NormalExamPaperUtil;
import com.zgq.wokao.Util.StringUtil;
import com.zgq.wokao.model.paper.ExamPaperInfo;
import com.zgq.wokao.model.paper.NormalExamPaper;
import com.zgq.wokao.data.realm.Paper.PaperDataProvider;
import com.zgq.wokao.setting.MarketChecker;
import com.zgq.wokao.ui.util.DialogUtil;
import com.zgq.wokao.view.RotateTextView;

import java.io.File;
import java.lang.ref.WeakReference;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.qqtheme.framework.util.StorageUtils;
import io.realm.Realm;
import jp.wasabeef.recyclerview.animators.FadeInAnimator;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    //paper信息列表
    @BindView(R.id.recy_exam_paper)
    RecyclerView examListView;
    PaperInfoAdapter adapter;
    @BindView(R.id.toolbar_layout)
    LinearLayout toolbarLayout;
    @BindView(R.id.toolbar_add)
    TextView toolbarAdd;
    @BindView(R.id.toolbar_more)
    TextView toolbarMore;
    @BindView(R.id.toolbar_search)
    TextView toolbarSearch;
    @BindView(R.id.toolbar_delete)
    TextView toolbarDelete;
    @BindView(R.id.toolbar_setstar)
    TextView toolbarSetStar;
    @BindView(R.id.toolbar_back)
    TextView toolbarBack;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar_background)
    View toolbarBackground;
    @BindView(R.id.toolbar_share)
    TextView toolbarShare;
    @BindView(R.id.toolbar_teach)
    TextView toolbarSetting;
    @BindView(R.id.toolbar_cloud)
    TextView toolbarCloud;
    @BindView(R.id.toolbar_overflow)
    LinearLayout toolbarOverFlow;



    private Realm realm = Realm.getDefaultInstance();
    private ArrayList<ExamPaperInfo> paperInfos = new ArrayList<>();
    private ArrayList<NormalExamPaper> papers = new ArrayList<>();

    private boolean actionModeIsActive = false;

    private MyHandler myHandler;

    private DialogUtil.Listener marketListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initView();
    }

    @Override
    protected void onStart() {
        super.onStart();
        updatePaperInfos();
    }

    @Override
    public void onResume() {
        super.onResume();
        checkMarket();
        MobclickAgent.onResume(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.close();
    }

    private void getRecyclerViewData() {
        papers = (ArrayList<NormalExamPaper>) PaperDataProvider.getInstance().getAllPaper();
        NormalExamPaperUtil.sortPapers(papers);
        paperInfos.clear();
        for (NormalExamPaper paper : papers) {
            paperInfos.add(paper.getPaperInfo());
        }
    }


    public void initData() {
        getRecyclerViewData();
        myHandler = new MyHandler(this);
    }

    public void initView() {
        toolbarSetStar.setOnClickListener(this);
        toolbarDelete.setOnClickListener(this);
        toolbarMore.setOnClickListener(this);
        toolbarSearch.setOnClickListener(this);
        toolbarAdd.setOnClickListener(this);
        toolbarBack.setOnClickListener(this);
        toolbarTitle.setOnClickListener(this);
        toolbarBackground.setOnClickListener(this);
        toolbarSetting.setOnClickListener(this);
        toolbarCloud.setOnClickListener(this);
        toolbarShare.setOnClickListener(this);
        examListView.addItemDecoration(
                new HorizontalDividerItemDecoration.Builder(this)
                        .color(getResources().getColor(R.color.colorRecyclerViewDivider))
                        .size(getResources().getDimensionPixelSize(R.dimen.paper_recyclerview_divider_height))
                        .margin(getResources().getDimensionPixelSize(R.dimen.paper_recyclerview_divider_margin), 0)
                        .build());
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this.getApplicationContext());
        examListView.setLayoutManager(layoutManager);
        examListView.setItemAnimator(new FadeInAnimator());
        adapter = new PaperInfoAdapter(paperInfos);
        examListView.setAdapter(adapter);
    }


    private void startMyActionMode() {
        toolbarLayout.setBackgroundColor(getResources().getColor(R.color.colorActionModeActionBarBackground));
        AnimatorSet set = new AnimatorSet();
        toolbarAdd.setVisibility(View.GONE);
        toolbarMore.setVisibility(View.GONE);
        toolbarTitle.setVisibility(View.GONE);
        set.playTogether(
                ObjectAnimator.ofFloat(toolbarDelete,"scaleY",0,1),
                ObjectAnimator.ofFloat(toolbarSetStar,"scaleY",0,1),
                ObjectAnimator.ofFloat(toolbarBack,"scaleY",0,1)
        );
        set.setDuration(100).start();
        toolbarDelete.setVisibility(View.VISIBLE);
        toolbarSetStar.setVisibility(View.VISIBLE);
        toolbarBack.setVisibility(View.VISIBLE);
        actionModeIsActive = true;
    }

    private void cancelMyActionMode() {
        toolbarLayout.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        toolbarDelete.setVisibility(View.GONE);
        toolbarSetStar.setVisibility(View.GONE);
        toolbarBack.setVisibility(View.GONE);
        AnimatorSet set = new AnimatorSet();
        set.playTogether(
                ObjectAnimator.ofFloat(toolbarAdd,"scaleY",0,1),
                ObjectAnimator.ofFloat(toolbarMore,"scaleY",0,1),
                ObjectAnimator.ofFloat(toolbarTitle,"scaleY",0,1)
        );
        set.setDuration(100).start();
        toolbarAdd.setVisibility(View.VISIBLE);
        toolbarMore.setVisibility(View.VISIBLE);
        toolbarTitle.setVisibility(View.VISIBLE);
        adapter.releaseSelectedView();
        actionModeIsActive = false;
    }

    private boolean getActionModeState() {
        return actionModeIsActive;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.toolbar_search:
                searchPaper();
                break;
            case R.id.toolbar_back:
                cancelMyActionMode();
                break;
            case R.id.toolbar_add:
                new MaterialFilePicker()
                        .withActivity(this)
                        .withRequestCode(1)
                        .start();
                break;
            case R.id.toolbar_more:
                if (getOverFlowStatus()) hideOverFlow();
                else showOverFlow();
                break;
            case R.id.toolbar_teach:
                hideOverFlow();
                Intent intent = new Intent();
                intent.setClass(MainActivity.this,TutotialActivity.class);
                startActivity(intent);
                break;
            case R.id.toolbar_cloud:
                break;
            case R.id.toolbar_background:
                hideOverFlow();
                break;
            case R.id.toolbar_delete:
                realm.executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        for (int i = 0; i<adapter.isItemSelectedList.size(); i++){
                            if (adapter.isItemSelectedList.get(i)){
//                                        adapter.removeSpecificPosition(i);
                                papers.get(i).deleteFromRealm();
                            }
                        }
                    }
                });
                updatePaperInfos();
                Toast.makeText(MainActivity.this,"delete",Toast.LENGTH_SHORT).show();
                break;
            case R.id.toolbar_setstar:
                realm.executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        for (int i = 0; i<adapter.isItemSelectedList.size(); i++){
                            if (adapter.isItemSelectedList.get(i) && !paperInfos.get(i).isStared()){
                                paperInfos.get(i).setStared(true);
                            }
                        }
                    }
                });
                updatePaperInfos();
                break;
        }
    }

    private boolean overFlowStatus = false;

    private boolean getOverFlowStatus(){
        return overFlowStatus;
    }

    private void hideOverFlow(){
        overFlowStatus = false;
        toolbarOverFlow.setVisibility(View.GONE);
        toolbarBackground.setVisibility(View.GONE);
    }

    private void showOverFlow(){
        overFlowStatus = true;
        AnimatorSet set = new AnimatorSet();
        set.playTogether(ObjectAnimator.ofFloat(toolbarOverFlow,"scaleX",0,1),
                ObjectAnimator.ofFloat(toolbarOverFlow,"scaleY",0,1));
        set.setDuration(100).start();
        toolbarOverFlow.setVisibility(View.VISIBLE);
        toolbarBackground.setVisibility(View.VISIBLE);
    }

    public void updatePaperInfos() {
        getRecyclerViewData();
        adapter.initData();
        adapter.notifyDataSetChanged();
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK && actionModeIsActive) {
            cancelMyActionMode();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    private void checkMarket(){
        if (MarketChecker.checkMarket(this)){
            marketListener = new DialogUtil.Listener(){
                @Override
                public void posOnClick() {
                    MarketChecker.setState(MainActivity.this,true);
                    MarketChecker.openMarketApp(MainActivity.this);
                }

                @Override
                public void negOnClick() {

                }
            };
            DialogUtil.show(this, StringUtil.getResString(R.string.market_dialog_title),
                    StringUtil.getResString(R.string.market_dialog_message),
                    StringUtil.getResString(R.string.market_dialog_positive_button),
                    StringUtil.getResString(R.string.market_dialog_negative_button),
                    marketListener);
        }
    }

    private void searchPaper(){
        Intent intent = new Intent();
        intent.setClass(this,SearchActivity.class);
        startActivity(intent);
    }

    static class MyHandler extends Handler {
        WeakReference<MainActivity> mWeakActivity;

        public MyHandler(MainActivity activity) {
            mWeakActivity = new WeakReference<MainActivity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0X1111:
                    Toast.makeText(mWeakActivity.get(),"解析成功",Toast.LENGTH_SHORT).show();
                    mWeakActivity.get().updatePaperInfos();
                    break;
                case 0X1112:
                    Toast.makeText(mWeakActivity.get(),"解析错误，请检查文档标题和作者",Toast.LENGTH_SHORT).show();
                    break;
                case 0X1113:
                    Toast.makeText(mWeakActivity.get(),"解析错误，请检查文档格式",Toast.LENGTH_SHORT).show();
                    break;
            }
            super.handleMessage(msg);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK) {
            String filePath = data.getStringExtra(FilePickerActivity.RESULT_FILE_PATH);
            // Do anything with file
            PaperDataProvider.getInstance().parseTxt2Realm(new File(filePath), new File(StorageUtils.getRootPath(MainActivity.this) + "wokao/tmp.xml"), realm, myHandler);
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

            final PaperInfoViewHolder holder1 = (PaperInfoViewHolder) holder;
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
                        Toast.makeText(MainActivity.this,"取消收藏",Toast.LENGTH_SHORT).show();
                    }else{
                        realm.executeTransaction(new Realm.Transaction() {
                            @Override
                            public void execute(Realm realm) {
                                info.setStared(true);
                            }
                        });
                        holder1.paperStar.setBackground(getResources().getDrawable(R.drawable.active_star));
                        Toast.makeText(MainActivity.this,"已收藏",Toast.LENGTH_SHORT).show();
                    }
                }
            });
            //paperlabel的单击事件
            holder1.paperLabel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!getActionModeState())
                        startMyActionMode();
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
                    intent.setClass(MainActivity.this, QuestionsListActivity.class);
                    intent.putExtra("paperTitle", info.getTitle());
                    intent.putExtra("paperAuthor", info.getAuthor());
                    startActivity(intent);
                }
            });
            //recyclerView item长按事件
            holder1.item.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if (getActionModeState()) return true;
                    else startMyActionMode();
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
            return new PaperInfoViewHolder(view);
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
