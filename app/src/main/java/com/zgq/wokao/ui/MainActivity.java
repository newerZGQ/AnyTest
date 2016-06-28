package com.zgq.wokao.ui;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.support.v7.widget.RecyclerView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;
import com.zgq.wokao.R;
import com.zgq.wokao.data.ExamPaperInfo;
import com.zgq.wokao.data.NormalExamPaper;
import com.zgq.wokao.data.RealmDataProvider;

import java.io.File;
import java.lang.ref.WeakReference;

import cn.qqtheme.framework.picker.FilePicker;
import cn.qqtheme.framework.util.StorageUtils;
import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity {
    private RecyclerView examListView;

    private Realm realm = Realm.getDefaultInstance();

    private RealmList<ExamPaperInfo> paperInfos = new RealmList<>();
    private PaperAdapter adapter;

    private MyHandler myHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
        setContentView(R.layout.activity_main);
        initView();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.close();
    }

    public void initData() {
        paperInfos = RealmDataProvider.getAllExamPaperInfo(realm);
        myHandler = new MyHandler(this);
    }
    public void initView(){
        examListView = (RecyclerView) findViewById(R.id.recy_exam_paper);
        examListView.addItemDecoration(
                new HorizontalDividerItemDecoration.Builder(this)
                        .color(getResources().getColor(R.color.colorRecyclerViewDivider))
                        .size(getResources().getDimensionPixelSize(R.dimen.paper_recyclerview_divider))
                        .build());
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this.getApplicationContext());
        examListView.setLayoutManager(layoutManager);
        adapter = new PaperAdapter();
        examListView.setAdapter(adapter);
    }
    public void updatePaperInfos(){
        paperInfos = RealmDataProvider.getAllExamPaperInfo(realm);
        adapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_activity_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.add_paper:
                FilePicker picker = new FilePicker(MainActivity.this, FilePicker.FILE);
                picker.setShowHideDir(false);
                picker.setRootPath(StorageUtils.getRootPath(MainActivity.this) + "Download/");
                picker.setOnFilePickListener(new FilePicker.OnFilePickListener() {
                    @Override
                    public void onFilePicked(String currentPath) {
                        RealmDataProvider.parseTxt2Realm(new File(currentPath), new File(StorageUtils.getRootPath(MainActivity.this) + "wokao/tmp.xml"), realm,myHandler);
                    }
                });
                picker.show();
                break;
        }
        return true;
    }

    static class MyHandler extends Handler{
        WeakReference<MainActivity> mWeakActivity;
        public MyHandler(MainActivity activity){
            mWeakActivity = new WeakReference<MainActivity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 0X1111:
                    mWeakActivity.get().updatePaperInfos();
            }
            super.handleMessage(msg);
        }
    }

    public class PaperAdapter extends RecyclerView.Adapter {

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            final PaperViewHolder holder1 = (PaperViewHolder) holder;
            final ExamPaperInfo info = paperInfos.get(holder1.getAdapterPosition());
            if (info.getTitle() != null) {
                holder1.paperName.setText(info.getTitle());
            }
            if (info.getAuthor() != null) {
                holder1.paperAuthor.setText(info.getAuthor());
            }
            if (info.getTitle() != null && info.getTitle().length()>=1) {
                holder1.paperLabel.setText(info.getTitle().substring(0, 1));
                if (!info.isStared()){
                    holder1.paperLabel.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                    holder1.paperLabel.setBackground(getResources().getDrawable(R.drawable.circle_background));
                }else{
                    holder1.paperLabel.setTextColor(getResources().getColor(R.color.colorWhite));
                    holder1.paperLabel.setBackground(getResources().getDrawable(R.drawable.circle_background_selected));
                }
            }
            holder1.paperLabel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (info.isStared()){
                        realm.executeTransaction(new Realm.Transaction() {
                            @Override
                            public void execute(Realm realm) {
                                info.setStared(false);
                            }
                        });
                        holder1.paperLabel.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                        holder1.paperLabel.setBackground(getResources().getDrawable(R.drawable.circle_background));
                    }else{
                        realm.executeTransaction(new Realm.Transaction() {
                            @Override
                            public void execute(Realm realm) {
                                info.setStared(true);
                            }
                        });
                        holder1.paperLabel.setTextColor(getResources().getColor(R.color.colorWhite));
                        holder1.paperLabel.setBackground(getResources().getDrawable(R.drawable.circle_background_selected));
                    }

                }
            });
            holder1.item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.setClass(MainActivity.this,QuestionsListActivity.class);
                    intent.putExtra("paperTitle",info.getTitle());
                    intent.putExtra("paperAuthor",info.getAuthor());
                    startActivity(intent);
                }
            });
            holder1.item.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    Log.d("----------->>","long");
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setMessage("确定删除么？");
                    builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            final RealmResults<NormalExamPaper> results = realm.where(NormalExamPaper.class).equalTo("paperInfo.title",info.getTitle())
                                    .equalTo("paperInfo.author",info.getAuthor()).findAll();
                            realm.executeTransaction(new Realm.Transaction() {
                                @Override
                                public void execute(Realm realm) {
                                    results.deleteAllFromRealm();
                                    updatePaperInfos();
                                }
                            });
                        }
                    }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    }).show();
                    return true;
                }
            });
        }

        @Override
        public int getItemCount() {
            return paperInfos.size();
        }

        @Override
        public PaperViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_main_recyclerview_item, parent, false);
            return new PaperViewHolder(view);
        }

        public void updatePaperLabel(ExamPaperInfo info){
            if (info.isStared()){

            }
        }

        public class PaperViewHolder extends RecyclerView.ViewHolder {
            public TextView paperName;
            public TextView paperLabel;
            public TextView paperAuthor;
            public TextView paperDate;
            public RelativeLayout item;
            public PaperViewHolder(View itemView) {
                super(itemView);
                item = (RelativeLayout) itemView.findViewById(R.id.list_item);
                paperName = (TextView) itemView.findViewById(R.id.paper_name);
                paperLabel = (TextView) itemView.findViewById(R.id.label);
                paperAuthor = (TextView) itemView.findViewById(R.id.auther);
                paperDate = (TextView) itemView.findViewById(R.id.last_time);
            }
        }
    }
}
