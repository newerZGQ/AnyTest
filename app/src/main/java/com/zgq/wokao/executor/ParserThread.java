package com.zgq.wokao.executor;

import android.util.Log;

import com.zgq.wokao.exception.ParseException;
import com.zgq.wokao.model.paper.NormalExamPaper;
import com.zgq.wokao.parser.ParserHelper;

import java.io.FileNotFoundException;

import io.realm.Realm;

/**
 * Created by zgq on 2017/2/20.
 */

public class ParserThread extends Thread {
    private String filePath;
    private OnCompletedListener listener;

    public ParserThread(String filePath) {
        this.filePath = filePath;
    }

    public void setListener(OnCompletedListener listener) {
        this.listener = listener;
    }

    @Override
    public void run() {
        super.run();
        try {
            Realm realm = Realm.getDefaultInstance();
            final NormalExamPaper paper = ParserHelper.getInstance().parse(filePath);
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    realm.copyToRealm(paper);
                }
            });
            Log.d("---->>", paper.getPaperInfo().getTitle());
            Log.d("---->>", paper.getDiscussQuestions().get(0).getBody().getContent());

            listener.onCompleted(paper);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public interface OnCompletedListener {
        public void onCompleted(NormalExamPaper paper);
    }
}
