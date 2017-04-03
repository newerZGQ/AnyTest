package com.zgq.wokao.ui.fragment.impl;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zgq.wokao.R;
import com.zgq.wokao.model.paper.info.IPaperInfo;
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
        View rootView =  inflater.inflate(R.layout.fragment_papers, container, false);
        ButterKnife.bind(this,rootView);
        papersPresenter.initPapersList();
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
    public void initPaperList(ArrayList<IPaperInfo> paperInfos) {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        paperList.setLayoutManager(layoutManager);
//        paperList.setItemAnimator(new FadeInAnimator());
        paperList.setAdapter(new HomePaperAdapter(paperInfos, new HomePaperAdapter.PaperAdapterListener() {
            @Override
            public void onItemClick(int position, IPaperInfo info) {

            }

            @Override
            public void onItemLongClick(int position, IPaperInfo info) {

            }
        }));
        paperList.setItemViewCacheSize(1);
    }

    @Override
    public void setPaperList(ArrayList<IPaperInfo> paperInfos) {
//        Log.d("----->>",TAG+" setPaperlist");
//        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
//        paperList.setLayoutManager(layoutManager);
//        paperList.setItemAnimator(new FadeInAnimator());
//        paperList.setAdapter(new HomePaperAdapter(paperInfos, new HomePaperAdapter.PaperAdapterListener() {
//            @Override
//            public void onItemClick(int position, IPaperInfo info) {
//                Intent intent = new Intent(getActivity(), PaperInfoActivity.class);
//                intent.putExtra("paperId",info.getId());
//                startActivity(intent);
//            }
//
//            @Override
//            public void onItemLongClick(int position, IPaperInfo info) {
//
//            }
//        }));
    }

    @Override
    public void notifyDataChanged() {
        paperList.getAdapter().notifyDataSetChanged();
    }

    public interface OnPaperFragmentListener {

    }
}
