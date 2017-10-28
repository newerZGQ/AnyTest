package com.zgq.wokao.ui.fragment.impl;

import android.content.Context;
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
import com.zgq.wokao.ui.adapter.HomePaperAdapter;
import com.zgq.wokao.ui.fragment.BaseFragment;
import com.zgq.wokao.ui.presenter.impl.PapersPresenter;
import com.zgq.wokao.ui.view.IPapersView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PapersFragment extends BaseFragment implements IPapersView {

    private static final String TAG = "PapersFragment";

    private PaperFragmentListener mListener;
    @BindView(R.id.paper_list)
    RecyclerView paperList;
//    @BindView(R.id.cover_view)
//    View coverView;
    @BindView(R.id.no_paper_stub)
    ViewStub noPaperStub;

    LinearLayout noContentLayout;

    private FrameLayout rootView;

    private PapersPresenter presenter = new PapersPresenter(this);

    public PapersFragment() {
    }

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
        rootView = (FrameLayout) inflater.inflate(R.layout.fragment_papers, container, false);
        ButterKnife.bind(this, rootView);
        initPaperList(presenter.getPaperInfos());
        checkPaperCount();
        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof PaperFragmentListener) {
            mListener = (PaperFragmentListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement PaperFragmentListener");
        }
    }

    @Override
    protected void onAttachToContext(Context context) {

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public boolean onActivityBackPress() {
        return false;
    }

    private void initPaperList(final ArrayList<IPaperInfo> paperInfos) {
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
                Snackbar.make(rootView, getResources().getString(R.string.delete_speer),
                        Snackbar.LENGTH_LONG).setAction(getResources().getString(R.string.delete),
                        new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mListener.onPaperDeleted(info.getId());
                        presenter.deletePaper(info.getId());
                        updateList();
                    }
                }).setActionTextColor(0xfff44336).show();
            }

            @Override
            public void onExitClick(final int position, final IPaperInfo info) {
                Snackbar.make(rootView, getResources().getString(R.string.exit_speer),
                        Snackbar.LENGTH_LONG).setAction(getResources().getString(R.string.confirm),
                        new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        presenter.removeFromSchedule(info.getId());
                        mListener.onPaperExitSchedule(info.getId());
                        updateList();
                    }
                }).show();
            }

            @Override
            public void onStartClick(final int position, final IPaperInfo info) {
                Snackbar.make(rootView, getResources().getString(R.string.start_speer),
                        Snackbar.LENGTH_LONG).setAction(getResources().getString(R.string.confirm),
                        new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        presenter.addToSchedule(info.getId());
                        mListener.onPaperInSchedule(info.getId());
                        updateList();
                    }
                }).show();
            }
        }));
        paperList.setItemViewCacheSize(1);
    }

    private void checkPaperCount() {
        if (presenter.getPaperCount() == 0) {
            onEmptyPapers();
        } else {
            onNoneEmptyPapers();
        }
    }

    private void onEmptyPapers() {
        paperList.setVisibility(View.GONE);
//        coverView.setVisibility(View.GONE);
        if (noContentLayout == null) {
            noPaperStub.inflate();
            noContentLayout = (LinearLayout) rootView.findViewById(R.id.no_paper_layout);
        } else {
            noContentLayout.setVisibility(View.VISIBLE);
        }
    }

    private void onNoneEmptyPapers() {
        paperList.setVisibility(View.VISIBLE);
//        coverView.setVisibility(View.VISIBLE);
        if (noContentLayout != null) {
            noContentLayout.setVisibility(View.GONE);
        }
    }

    private void updateList() {
        ((HomePaperAdapter) paperList.getAdapter()).setData(presenter.getPaperInfos());
        paperList.getAdapter().notifyDataSetChanged();
        checkPaperCount();
    }

    public interface PaperFragmentListener {
        void onPaperDeleted(String paperId);

        void onPaperExitSchedule(String paperId);

        void onPaperInSchedule(String paperId);
    }

}
