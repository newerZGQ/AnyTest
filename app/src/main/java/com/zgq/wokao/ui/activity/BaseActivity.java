package com.zgq.wokao.ui.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.AlertDialog;

import com.zgq.wokao.model.paper.QuestionType;

/**
 * Created by zgq on 2017/9/18.
 */

public class BaseActivity extends AbsActivity {
    protected void startStudy(String paperId, QuestionType type, int qstNum, boolean finishThis) {
        Intent intent = new Intent(this, AnswerStudyActivity.class);
        intent.putExtra("paperId", paperId);
        intent.putExtra("questionType", (Parcelable) type);
        intent.putExtra("qstNum", qstNum);
        startActivity(intent);
        if (finishThis) {
            finish();
        }
    }

    protected void showBaseDialog(String title, String message,
                              String positive, String negative, final OnOprateDialogListener listener) {
        new AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(positive, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        listener.onClickPositive();
                    }
                })
                .setNegativeButton(negative, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        listener.onClickNegative();
                    }
                }).setCancelable(false).show();
    }

    public interface OnOprateDialogListener {
        void onClickPositive();
        void onClickNegative();
    }
}
