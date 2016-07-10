package com.zgq.wokao.ui;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;
import com.zgq.wokao.R;
import com.zgq.wokao.Util.DrawableUtil;
import com.zgq.wokao.data.ExamPaperInfo;
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

public class MainActivity extends AppCompatActivity {
    //paper信息列表
    @BindView(R.id.recy_exam_paper)
    RecyclerView examListView;
    PaperInfoAdapter adapter;

    private Realm realm = Realm.getDefaultInstance();
    private ArrayList<Object> paperInfos = new ArrayList<>();

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
        for (ExamPaperInfo info : RealmDataProvider.getAllExamPaperInfo(realm)) {
            paperInfos.add(info);
        }
        paperInfos.add(3, "其余");
        paperInfos.add(0, "最近");
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
                        .margin(getResources().getDimensionPixelSize(R.dimen.paper_recyclerview_divider_margin),getResources().getDimensionPixelSize(R.dimen.paper_recyclerview_divider_margin))
                        .build());
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this.getApplicationContext());
        examListView.setLayoutManager(layoutManager);
        adapter = new PaperInfoAdapter();
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
            case R.id.add_paper:
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
        private ArrayList<View> selectedViewList = new ArrayList<>();
        private final int GROUPTYPE = 1;
        private final int ITEMTYPE = 2;
        private int viewType = GROUPTYPE;

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            switch (holder.getItemViewType()) {
                case ITEMTYPE:
                    final PaperInfoViewHolder holder1 = (PaperInfoViewHolder) holder;
                    final ExamPaperInfo info = (ExamPaperInfo) paperInfos.get(holder1.getAdapterPosition());
                    //title 以及label的初始显示
                    if (info.getTitle() != null) {
                        holder1.paperName.setText(info.getTitle());
                        holder1.paperLabel.setText(info.getTitle().substring(0, 1));
                        holder1.paperLabel.setBackground(getResources().getDrawable(DrawableUtil.getDrawable()));
                    }
                    //作者信息初始显示
                    if (info.getAuthor() != null) {
                        holder1.paperAuthorAndDate.setText(getAuthorAndData(info));
                    }
                    //是否收藏信息显示
                    if (!info.isStared()) {
//                        holder1.paperStar.setBackgroundColor(getResources().getColor(R.color.colorWhite));
                    } else {
//                        holder1.paperStar.setBackgroundColor(getResources().getColor(R.color.colorBlue));
                    }
                    //设置paperlabel的正面以及反面
                    holder1.paperLabel.setSidesStyle(new RotateTextView.UpAndDownSideStyle() {
                        @Override
                        public void setUpSide() {
                            holder1.paperLabel.setText(info.getTitle().substring(0, 1));
                            holder1.paperLabel.setBackground(getResources().getDrawable(DrawableUtil.getDrawable()));
                            holder1.item.setBackgroundColor(getResources().getColor(R.color.colorWhite));
                            selectedViewList.remove(holder1.paperLabel);
                        }

                        @Override
                        public void setDownSide() {
                            holder1.paperLabel.setText("√");
                            holder1.paperLabel.setBackground(getResources().getDrawable(R.drawable.circle_background_downside));
                            holder1.item.setBackgroundColor(getResources().getColor(R.color.colorRecyclerViewItemSelectedBackGround));
                            selectedViewList.add(holder1.paperLabel);
                        }
                    });
                    //paperlabel的单击事件
                    holder1.paperLabel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (!getActionModeState())
                                startMyActionMode();
                            holder1.paperLabel.changeSide();
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
                            holder1.paperLabel.changeSide();
                            startMyActionMode();
                            return true;
                        }
                    });
                    break;
                case GROUPTYPE:
                    PaperGroupTitleViewHolder groupTitleViewHolder = (PaperGroupTitleViewHolder) holder;
                    String groupTitle  = (String) paperInfos.get(position);
                    groupTitleViewHolder.groupTitle.setText(groupTitle);
                    break;
            }
        }

        @Override
        public int getItemViewType(int position) {
            if (paperInfos.get(position).toString().equals("最近") || paperInfos.get(position).toString().equals("其余")) {
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
            View view = null;
            switch(viewType){
                case ITEMTYPE:
                    view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_main_recyclerview_item, parent, false);
                    return new PaperInfoViewHolder(view);

                case GROUPTYPE:
                    view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_main_recyclerview_group_item, parent, false);
                    return new PaperGroupTitleViewHolder(view);
            }
            return null;
        }

        private void releaseSelectedView() {
            while (selectedViewList.size() > 0) {
                ((RotateTextView) (selectedViewList.remove(selectedViewList.size() - 1))).changeSide();
            }
        }

        private String getAuthorAndData(ExamPaperInfo info){
            String author = info.getAuthor();
            String lastDate = info.getLastStudyDate();
            if (author == null || author.equals("")) author = "      ";
            if (lastDate == null || lastDate.equals("")) {
                lastDate = "未学习";
            }else {
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

            public PaperInfoViewHolder(View itemView) {
                super(itemView);
                item = (RelativeLayout) itemView.findViewById(R.id.list_item);
                paperName = (TextView) itemView.findViewById(R.id.paper_name);
                paperLabel = (RotateTextView) itemView.findViewById(R.id.label);
                paperLabel.setClickable(true);
                paperAuthorAndDate = (TextView) itemView.findViewById(R.id.auther_and_date);
                paperStar = (TextView) itemView.findViewById(R.id.activity_main_paper_star_tv);
            }
        }

        public class PaperGroupTitleViewHolder extends RecyclerView.ViewHolder {
            public TextView groupTitle;

            public PaperGroupTitleViewHolder(View itemView) {
                super(itemView);
                groupTitle = (TextView) itemView.findViewById(R.id.recyclerview_group_title);
            }
        }
    }
}
