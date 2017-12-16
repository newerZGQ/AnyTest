package com.zgq.wokao.module.study;

import android.support.v4.view.ViewPager;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zgq.wokao.R;
import com.zgq.wokao.entity.paper.question.IQuestion;
import com.zgq.wokao.injector.components.DaggerStudyComponent;
import com.zgq.wokao.injector.modules.StudyModule;
import com.zgq.wokao.module.base.BaseFragment;
import com.zgq.wokao.module.study.entity.StudyParams;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

public class LearningFragment extends BaseFragment<StudyContract.LearningPresenter>
        implements StudyContract.LearningView{

    private static final String TAG = LearningFragment.class.getSimpleName();

    //返回
    @BindView(R.id.toolbar_back)
    TextView toolbarBack;

    //切换模式
    @BindView(R.id.all_question)
    TextView studyMode;

    @BindView(R.id.stared_question)
    TextView remebMode;

    //ViewPager
    @BindView(R.id.answer_study_pager)
    ViewPager viewPager;

    //显示答案按钮
    @BindView(R.id.show_answer)
    LinearLayout showAnswerButton;

    @BindView(R.id.answer_label)
    TextView answerLabel;

    //收藏按钮
    @BindView(R.id.set_star)
    LinearLayout setStared;
    @BindView(R.id.star_label)
    TextView starLabel;

    //列表按钮
    @BindView(R.id.question_list)
    LinearLayout questionListTv;

    //题号列表
    @BindView(R.id.question_num_list)
    GridView questionListGv;

    //学习记录
    @BindView(R.id.study_info)
    TextView studyInfo;

    //底部menu
    @BindView(R.id.activity_study_bottom_menu)
    RelativeLayout bottomMenu;

    //弹出底部menu时用于遮挡ViewPager
    @BindView(R.id.background)
    LinearLayout background;

    @Inject
    StudyParams studyParams;

    @Override
    protected void daggerInject() {
        DaggerStudyComponent.builder()
                .applicationComponent(getAppComponent())
                .studyModule(new StudyModule())
                .build()
                .inject(this);
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.fragment_learning;
    }

    @Override
    protected void initViews() {

    }

    @Override
    public void showQuestions(List<IQuestion> questions) {

    }
}
