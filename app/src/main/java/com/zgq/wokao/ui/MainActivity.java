package com.zgq.wokao.ui;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.view.ActionMode;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.support.v7.widget.RecyclerView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;
import com.zgq.wokao.R;
import com.zgq.wokao.Util.DrawableUtil;
import com.zgq.wokao.data.ExamPaperInfo;
import com.zgq.wokao.data.NormalExamPaper;
import com.zgq.wokao.data.RealmDataProvider;
import com.zgq.wokao.view.RotateTextView;
import com.zgq.wokao.Util.DateUtil;

import java.io.File;
import java.lang.ref.WeakReference;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.qqtheme.framework.picker.FilePicker;
import cn.qqtheme.framework.util.StorageUtils;
import io.realm.Realm;
import jp.wasabeef.recyclerview.animators.FadeInAnimator;
import jp.wasabeef.recyclerview.animators.SlideInLeftAnimator;

public class MainActivity extends AppCompatActivity {
    //paper信息列表
    @BindView(R.id.recy_exam_paper)
    RecyclerView examListView;
    PaperInfoAdapter adapter;

    private Realm realm = Realm.getDefaultInstance();
    private ArrayList<ExamPaperInfo> paperInfos = new ArrayList<>();
    private ArrayList<NormalExamPaper> papers = new ArrayList<>();

    private ActionMode.Callback callback;
    private boolean actionModeIsActive = false;

    private MyHandler myHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initView();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.close();
    }

    private void getRecyclerViewData() {
        papers = RealmDataProvider.getAllExamPaper(realm);
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
        examListView.addItemDecoration(
                new HorizontalDividerItemDecoration.Builder(this)
                        .color(getResources().getColor(R.color.colorRecyclerViewDivider))
                        .size(getResources().getDimensionPixelSize(R.dimen.paper_recyclerview_divider_height))
                        .margin(getResources().getDimensionPixelSize(R.dimen.paper_recyclerview_divider_margin), getResources().getDimensionPixelSize(R.dimen.paper_recyclerview_divider_margin))
                        .build());
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this.getApplicationContext());
        examListView.setLayoutManager(layoutManager);
        examListView.setItemAnimator(new FadeInAnimator());
        adapter = new PaperInfoAdapter(paperInfos);
        examListView.setAdapter(adapter);

        callback = new ActionMode.Callback() {
            @Override
            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                MenuInflater inflater = mode.getMenuInflater();
                inflater.inflate(R.menu.activity_main_actionmode, menu);
                return true;
            }

            @Override
            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                return false;
            }

            @Override
            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                switch (item.getItemId()){
                    case R.id.delete_menu:
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
                    case R.id.set_star_menu:
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
                return false;
            }

            @Override
            public void onDestroyActionMode(ActionMode mode) {
                adapter.releaseSelectedView();
                cancelMyActionMode();
            }
        };
    }

    private void startMyActionMode() {
        startActionMode(callback);
        actionModeIsActive = true;
    }

    private void cancelMyActionMode() {
        actionModeIsActive = false;
    }

    private boolean getActionModeState() {
        return actionModeIsActive;
    }

    public void updatePaperInfos() {
        getRecyclerViewData();
        adapter.initData();
        adapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_activity_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add_paper_menu:
                FilePicker picker = new FilePicker(MainActivity.this, FilePicker.FILE);
                picker.setShowHideDir(false);
                picker.setRootPath(StorageUtils.getRootPath(MainActivity.this) + "Download/");
                picker.setOnFilePickListener(new FilePicker.OnFilePickListener() {
                    @Override
                    public void onFilePicked(String currentPath) {
                        RealmDataProvider.parseTxt2Realm(new File(currentPath), new File(StorageUtils.getRootPath(MainActivity.this) + "wokao/tmp.xml"), realm, myHandler);
                    }
                });
                picker.show();
                break;
            case R.id.share_app_menu:
                break;
            case R.id.setting_app_menu:
                break;
        }
        return true;
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {

        }
        return super.onKeyDown(keyCode, event);
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
                    mWeakActivity.get().updatePaperInfos();
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
                    holder1.paperLabel.setBackground(getResources().getDrawable(R.drawable.dowm));
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
                    intent.putExtra("paperAuthorAndDate", info.getAuthor());
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
                lastDate = DateUtil.getSimpleDate(lastDate);
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
                paperLabel = (RotateTextView) itemView.findViewById(R.id.label);
                paperLabel.setClickable(true);
                paperAuthorAndDate = (TextView) itemView.findViewById(R.id.auther_and_date);
                paperStar = (TextView) itemView.findViewById(R.id.activity_main_paper_star_tv);
                groupTitle = (TextView) itemView.findViewById(R.id.group_title);
            }
        }
    }
}
