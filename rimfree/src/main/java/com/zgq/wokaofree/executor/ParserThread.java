package com.zgq.wokaofree.executor;

import android.util.Log;

import com.zgq.wokaofree.exception.ParseException;
import com.zgq.wokaofree.model.paper.NormalExamPaper;
import com.zgq.wokaofree.parser.ParserHelper;

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
                    if (paper != null) {
                        realm.copyToRealm(paper);
                    }
                }
            });

            listener.onCompleted(paper);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public interface OnCompletedListener {
        public void onCompleted(NormalExamPaper paper);
    }
}
