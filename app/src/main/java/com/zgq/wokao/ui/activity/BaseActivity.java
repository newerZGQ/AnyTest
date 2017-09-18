package com.zgq.wokao.ui.activity;

import android.content.Intent;
import android.os.Parcelable;

import com.zgq.wokao.model.paper.QuestionType;

/**
 * Created by zgq on 2017/9/18.
 */

public class BaseActivity extends AbsActivity {
    protected void startStudy(String paperId, QuestionType type, int qstNum, boolean finishThis) {
        Intent intent = new Intent(this, AnswerStudyActivity.class);
        intent.putExtra("paperId", paperId);
        intent.putExtra("qstType", (Parcelable) type);
        intent.putExtra("qstNum", qstNum);
        startActivity(intent);
        if (finishThis) {
            finish();
        }
    }
}
