package com.zgq.wokao.ui.fragment.impl;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.zgq.wokao.R;
import com.zgq.wokao.model.paper.QuestionType;
import com.zgq.wokao.model.viewdate.QstData;
import com.zgq.wokao.ui.adapter.QuestionsInfoAdapter;
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

    private static final String TAG = QuestionsFragment.class.getSimpleName();

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String paperId;

    private QuestionsFragmentListener mListener;

    private RecyclerView qstPager;

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
        View view = inflater.inflate(R.layout.fragment_questions_layout, container, false);
        initQstList(view);
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
        void startFromQuestionFrag(String paperId, QuestionType type);
    }

    private void initQstList(View parent) {
        qstPager = (RecyclerView) parent.findViewById(R.id.questions_list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(),
                OrientationHelper.VERTICAL, false);
        qstPager.setLayoutManager(layoutManager);
        LinearLayout.LayoutParams lp = new LinearLayout.
                LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        qstPager.setLayoutParams(lp);
        qstPager.setAdapter(new QuestionsInfoAdapter(getActivity().getApplication(),
                presenter.getQstLists(), presenter.getPaperInfo(),new QuestionsInfoAdapter.ItemClickListener() {
            @Override
            public void onItemClicked(int position) {
                QstData qstData = presenter.getQstLists().get(position);
                Log.d(TAG,position + ": " +qstData.getType());
                mListener.startFromQuestionFrag(qstData.getPaperId(),qstData.getType());
            }
        }));
    }
}
