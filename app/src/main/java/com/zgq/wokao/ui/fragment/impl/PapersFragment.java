package com.zgq.wokao.ui.fragment.impl;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.zgq.wokao.R;
import com.zgq.wokao.model.paper.info.IPaperInfo;
import com.zgq.wokao.ui.activity.HomeActivity;
import com.zgq.wokao.ui.adapter.HomePaperAdapter;
import com.zgq.wokao.ui.fragment.BaseFragment;
import com.zgq.wokao.ui.presenter.impl.PapersPresenter;
import com.zgq.wokao.ui.view.IPapersView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PapersFragment extends BaseFragment implements IPapersView{

    private static final String TAG = "PapersFragment";

    private OnPaperFragmentListener mListener;
    @BindView(R.id.paper_list)
    RecyclerView paperList;
    @BindView(R.id.cover_view)
    View coverView;
    @BindView(R.id.no_paper_stub)
    ViewStub noPaperStub;

    LinearLayout noContentLayout;

    private FrameLayout rootView;

    private PapersPresenter papersPresenter = new PapersPresenter(this);

    public PapersFragment() {}

    public static PapersFragment newInstance() {
        PapersFragment fragment = new PapersFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView =  (FrameLayout) inflater.inflate(R.layout.fragment_papers, container, false);
        ButterKnife.bind(this,rootView);
        papersPresenter.initPapersList();
        papersPresenter.checkPapersSize();
        return rootView;
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {

        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnPaperFragmentListener) {
            mListener = (OnPaperFragmentListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnPaperFragmentListener");
        }
    }

    @Override
    protected void onAttachToContext(Context context) {

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
    public void initPaperList(final ArrayList<IPaperInfo> paperInfos) {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        paperList.setLayoutManager(layoutManager);
        paperList.setAdapter(new HomePaperAdapter(paperInfos, new HomePaperAdapter.PaperAdapterListener() {
            @Override
            public void onItemClick(int position, IPaperInfo info) {

            }

            @Override
            public void onItemLongClick(int position, IPaperInfo info) {

            }

            @Override
            public void onDeleteClick(final int position, final IPaperInfo info) {
                Snackbar.make(rootView,"确定要删除么",Snackbar.LENGTH_LONG).setAction("确定", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        papersPresenter.deletePaper(info.getId());
                        paperList.getAdapter().notifyItemChanged(position);
                    }
                }).setActionTextColor(0xfff44336).show();
            }

            @Override
            public void onExitClick(final int position, final IPaperInfo info) {
                Snackbar.make(rootView,"确定退出学习该试卷么",Snackbar.LENGTH_LONG).setAction("确定", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        papersPresenter.removeFromSchedule(info.getId());
                        paperList.getAdapter().notifyItemChanged(position);
                    }
                }).show();
            }

            @Override
            public void onStartClick(final int position, final IPaperInfo info) {
                Snackbar.make(rootView,"确定开始学习么",Snackbar.LENGTH_LONG).setAction("确定", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        papersPresenter.addToSchedule(info.getId());
                        paperList.getAdapter().notifyItemChanged(position);
                    }
                }).show();
            }
        }));
        paperList.setItemViewCacheSize(1);
    }


    @Override
    public void notifyDataChanged(ArrayList<IPaperInfo> paperInfos) {
        ((HomePaperAdapter)paperList.getAdapter()).setData(paperInfos);
        paperList.getAdapter().notifyDataSetChanged();
        papersPresenter.checkPapersSize();
    }

    public interface OnPaperFragmentListener {

    }

    public HomeActivity getHomeActivity(){
        return (HomeActivity)getActivity();
    }

    @Override
    public void onEmptyPapers() {
        paperList.setVisibility(View.GONE);
        coverView.setVisibility(View.GONE);
        if (noContentLayout == null){
            noPaperStub.inflate();
            noContentLayout = (LinearLayout) rootView.findViewById(R.id.no_paper_layout);
        }else{
            noContentLayout.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onNoneEmptyPapers() {
        paperList.setVisibility(View.VISIBLE);
        coverView.setVisibility(View.VISIBLE);
        if (noContentLayout != null){
            noContentLayout.setVisibility(View.GONE);
        }
    }

    public PapersPresenter getPapersPresenter() {
        return papersPresenter;
    }
}
