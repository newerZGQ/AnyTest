package com.zgq.wokao.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;
import com.zgq.wokao.R;
import com.zgq.wokao.data.NormalExamPaper;
import com.zgq.wokao.view.RotateTextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;;
import io.realm.RealmResults;

public class QuestionsListActivity extends AppCompatActivity {

    private static final int FILLINQUESTIONLABEL  = 1;
    private static final int TFQUESTIONLABEL      = 2;
    private static final int SGLCHOQUESTIONLABEL  = 3;
    private static final int MULTCHOQUESTIONLABEL = 4;
    private static final int DISCUSSQUESTIONLABEL = 5;

    private RecyclerView examListView;
    private QuestionTypeAdapter adapter;

    @BindView(R.id.test1)
    RotateTextView textView;

    private ArrayList<String> typeNames = new ArrayList<>();
    private ArrayList<Integer> typeImages = new ArrayList<>();
    private ArrayList<Integer> questionCount = new ArrayList<>();

    private NormalExamPaper normalExamPaper;

    private Realm realm = Realm.getDefaultInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
        setContentView(R.layout.activity_questions_list);
        ButterKnife.bind(this);
        setTitle(normalExamPaper.getPaperInfo().getTitle());
        examListView = (RecyclerView) findViewById(R.id.question_list);
        examListView.addItemDecoration(
                new HorizontalDividerItemDecoration.Builder(this)
                        .color(getResources().getColor(R.color.colorRecyclerViewDivider))
                        .size(getResources().getDimensionPixelSize(R.dimen.paper_recyclerview_divider_height))
                        .build());
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this.getApplicationContext());
        examListView.setLayoutManager(layoutManager);
        adapter = new QuestionTypeAdapter();
        examListView.setAdapter(adapter);
        textView.setClickable(true);
        textView.setSidesStyle(new RotateTextView.UpAndDownSideStyle() {
            @Override
            public void setUpSide() {
                textView.setText("U");
                textView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            }
            @Override
            public void setDownSide() {
                textView.setText("D");
                textView.setBackgroundColor(getResources().getColor(R.color.colorAccent));
            }
        });
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textView.changeSide();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.close();
    }

    private void initData() {
        String title = getIntent().getStringExtra("paperTitle");
        String author = getIntent().getStringExtra("paperAuthorAndDate");
        RealmResults<NormalExamPaper> papers = realm.where(NormalExamPaper.class).findAll();
        for (NormalExamPaper paper : papers) {
            if (title.equals(paper.getPaperInfo().getTitle()) || author.equals(paper.getPaperInfo().getAuthor())) {
                normalExamPaper = paper;
            }
        }
        questionCount.add(normalExamPaper.getFillInQuestions().size());
        questionCount.add(normalExamPaper.getTfQuestions().size());
        questionCount.add(normalExamPaper.getSglChoQuestions().size());
        questionCount.add(normalExamPaper.getMultChoQuestions().size());
        questionCount.add(normalExamPaper.getDiscussQuestions().size());
        questionCount.add(normalExamPaper.getQuestionsCount());

        typeNames.add("填空题");
        typeNames.add("判断题");
        typeNames.add("单选题");
        typeNames.add("多选题");
        typeNames.add("简答题");
        typeNames.add("顺序学习");

        typeImages.add(R.drawable.circle_background_downside);
        typeImages.add(R.drawable.circle_background_downside);
        typeImages.add(R.drawable.circle_background_downside);
        typeImages.add(R.drawable.circle_background_downside);
        typeImages.add(R.drawable.circle_background_downside);
        typeImages.add(R.drawable.circle_background_downside);
    }

    private boolean isEmptyQuestionList(int position){
        if (normalExamPaper == null) return true;
        switch (position){
            case FILLINQUESTIONLABEL:
                if (normalExamPaper.getFillInQuestions().size() == 0 ) return true;
                break;
            case TFQUESTIONLABEL:
                if (normalExamPaper.getTfQuestions().size() == 0) return true;
                break;
            case SGLCHOQUESTIONLABEL:
                if (normalExamPaper.getSglChoQuestions().size() == 0) return true;
                break;
            case MULTCHOQUESTIONLABEL:
                if (normalExamPaper.getMultChoQuestions().size() == 0) return true;
                break;
            case DISCUSSQUESTIONLABEL:
                if (normalExamPaper.getDiscussQuestions().size() == 0) return true;
                break;
        }
        return false;
    }

    public class QuestionTypeAdapter extends RecyclerView.Adapter {

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
            QuestionViewHolder holder1 = (QuestionViewHolder) holder;
            holder1.typeName.setText(typeNames.get(holder1.getAdapterPosition()));
            holder1.typeImage.setImageResource(typeImages.get(holder1.getAdapterPosition()));
            holder1.questionCount.setText("共" + questionCount.get(holder1.getAdapterPosition()) + "题");
            holder1.item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (isEmptyQuestionList(position+1)){
                        //tanchutishi
                        Log.d("------------->","in");
                        return;
                    }
                    Intent intent = new Intent(QuestionsListActivity.this,AnswerStudyActivity.class);
                    intent.putExtra("QuestionLabel",position+1);
                    startActivity(intent);
                }
            });
        }

        @Override
        public int getItemCount() {
            return typeNames.size();
        }

        @Override
        public QuestionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_question_list_recyclerview_item, parent, false);
            return new QuestionViewHolder(view);
        }

        public class QuestionViewHolder extends RecyclerView.ViewHolder {
            public TextView typeName;
            public ImageView typeImage;
            public TextView questionCount;
            public LinearLayout item;

            public QuestionViewHolder(View itemView) {
                super(itemView);
                item = (LinearLayout) itemView.findViewById(R.id.question_type_item);
                typeImage = (ImageView) itemView.findViewById(R.id.type_image);
                typeName = (TextView) itemView.findViewById(R.id.type_name);
                questionCount = (TextView) itemView.findViewById(R.id.question_count);
            }
        }
    }
}
