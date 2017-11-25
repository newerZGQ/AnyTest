package com.zgq.wokao.module.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zgq.wokao.R;
import com.zgq.wokao.adapter.HomePaperAdapter;
import com.zgq.wokao.entity.paper.NormalExamPaper;
import com.zgq.wokao.injector.components.DaggerHomeComponent;
import com.zgq.wokao.injector.modules.HomeModule;
import com.zgq.wokao.module.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by zhangguoqiang on 2017/11/21.
 */

public class PaperFragment extends BaseFragment<HomeContract.PaperPresenter>
        implements HomeContract.PaperView {

    @BindView(R.id.paper_list)
    RecyclerView paperList;

    private View rootView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = super.onCreateView(inflater, container, savedInstanceState);
        return rootView;
    }

    @Override
    protected void daggerInject() {
        DaggerHomeComponent.builder()
                .applicationComponent(getAppComponent())
                .homeModule(new HomeModule())
                .build()
                .inject(this);
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.fragment_paper;
    }

    @Override
    protected void initViews() {
        initPaperList();
    }

    @Override
    public void setPaperListData(List<NormalExamPaper> examPapers) {
        homePaperAdapter.replaceData(examPapers);
    }

    @Override
    public void notifyDataChanged() {
        homePaperAdapter.notifyDataSetChanged();
    }

    private void initPaperList() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        paperList.setLayoutManager(layoutManager);
        paperList.setAdapter(homePaperAdapter);
        paperList.setItemViewCacheSize(1);
    }

    private HomePaperAdapter homePaperAdapter = new HomePaperAdapter(new ArrayList<>(0),
            new HomePaperAdapter.PaperAdapterListener() {
                @Override
                public void onItemClick(int position, NormalExamPaper paper) {

                }

                @Override
                public void onItemLongClick(int position, NormalExamPaper paper) {

                }

                @Override
                public void onDeleteClick(int position, NormalExamPaper paper) {
                    Snackbar.make(rootView, getResources().getString(R.string.delete_speer),
                            Snackbar.LENGTH_LONG).setAction(getResources().getString(R.string.delete),
                            view -> presenter.deletePaper(paper)).setActionTextColor(0xfff44336).show();
                }

                @Override
                public void onExitClick(int position, NormalExamPaper paper) {
                    Snackbar.make(rootView, getResources().getString(R.string.exit_speer),
                            Snackbar.LENGTH_LONG).setAction(getResources().getString(R.string.confirm),
                            view -> presenter.removeFromSchedule(paper)).show();
                }

                @Override
                public void onStartClick(int position, NormalExamPaper paper) {
                    Snackbar.make(rootView, getResources().getString(R.string.start_speer),
                            Snackbar.LENGTH_LONG).setAction(getResources().getString(R.string.confirm),
                            view -> presenter.addToSchedule(paper)).show();
                }
            });
}
