package com.zgq.wokao.ui.fragment.impl;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.marshalchen.ultimaterecyclerview.UltimateRecyclerView;
import com.zgq.wokao.R;
import com.zgq.wokao.model.paper.info.IPaperInfo;
import com.zgq.wokao.ui.activity.PaperInfoActivity;
import com.zgq.wokao.ui.adapter.HomePaperAdapter;
import com.zgq.wokao.ui.adapter.ultiadapter.ExpCustomAdapter;
import com.zgq.wokao.ui.fragment.BaseFragment;
import com.zgq.wokao.ui.presenter.impl.PapersPresenter;
import com.zgq.wokao.ui.view.IPapersView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.recyclerview.animators.FadeInAnimator;

public class PapersFragment extends BaseFragment implements IPapersView{

    private static final String TAG = "PapersFragment";

    private OnPaperFragmentListener mListener;
    @BindView(R.id.paper_list)
    UltimateRecyclerView paperList;

    private ExpCustomAdapter papersAdapter;
    private LinearLayoutManager linearLayoutManager;

    private PapersPresenter papersPresenter = new PapersPresenter(this);

    private static String[] sampledatagroup1 = {
            "peter", "http://google",
            "billy", "http://google",
            "lisa", "http://google",
            "visa", "http://google"
    };
    private static String[] sampledatagroup2 = {
            "mother", "http://google",
            "father", "http://google",
            "son", "http://google",
            "holy spirit", "http://google",
            "god the son", "http://google"
    };
    private static String[] sampledatagroup3 = {
            "SONY", "http://google",
            "LG", "http://google",
            "SAMSUNG", "http://google",
            "XIAOMI", "http://google",
            "HTC", "http://google"
    };
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
        paperList.setHasFixedSize(false);
        papersAdapter = new ExpCustomAdapter(getContext());
        papersAdapter.addAll(ExpCustomAdapter.getPreCodeMenu(sampledatagroup1, sampledatagroup2, sampledatagroup3), 0);
        linearLayoutManager = new LinearLayoutManager(getContext());
        paperList.setLayoutManager(linearLayoutManager);
        paperList.setAdapter(papersAdapter);
        paperList.setRecylerViewBackgroundColor(Color.parseColor("#FFFFFF"));
        addExpandableFeatures();
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

    private void addExpandableFeatures() {
        paperList.getItemAnimator().setAddDuration(100);
        paperList.getItemAnimator().setRemoveDuration(100);
        paperList.getItemAnimator().setMoveDuration(200);
        paperList.getItemAnimator().setChangeDuration(100);
    }

    @Override
    public void notifyDataChanged() {
        paperList.getAdapter().notifyDataSetChanged();
    }

    public interface OnPaperFragmentListener {

    }
}
