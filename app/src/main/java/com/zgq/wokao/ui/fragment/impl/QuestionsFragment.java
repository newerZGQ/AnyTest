package com.zgq.wokao.ui.fragment.impl;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zgq.wokao.R;
import com.zgq.wokao.ui.adapter.QuestionsPagerAdapter;
import com.zgq.wokao.ui.fragment.BaseFragment;
import com.zgq.wokao.ui.presenter.impl.QuestionsPresenterImpl;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link QuestionsFragment.QuestionsFragmentListener} interface
 * to handle interaction events.
 * Use the {@link QuestionsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class QuestionsFragment extends BaseFragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String paperId;

    private QuestionsFragmentListener mListener;

    private ViewPager qstPager;

    private QuestionsPresenterImpl presenter;

    public QuestionsFragment() {

    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param paperId Parameter 1.
     * @return A new instance of fragment QuestionsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static QuestionsFragment newInstance(String paperId) {
        QuestionsFragment fragment = new QuestionsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, paperId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            paperId = getArguments().getString(ARG_PARAM1);
            presenter = new QuestionsPresenterImpl(paperId);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_questions_fragment, container, false);
        initQstPager(view);
        return view;
    }


    @Override
    protected void onAttachToContext(Context context) {
        if (context instanceof QuestionsFragmentListener) {
            mListener = (QuestionsFragmentListener) context;
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
    public interface QuestionsFragmentListener {

    }

    private void initQstPager(View parent){
        qstPager = (ViewPager) parent.findViewById(R.id.qst_pager);
        qstPager.setAdapter(new QuestionsPagerAdapter(getContext(), presenter.getQstLists(),
                new QuestionsPagerAdapter.OnViewClickListener() {
            @Override
            public void onClickTopLayout(int position) {

            }
        }));
    }

}
