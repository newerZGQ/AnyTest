package com.zgq.wokao.executor;

import android.util.Log;

import com.zgq.wokao.Util.FileUtil;
import com.zgq.wokao.exception.ParseException;
import com.zgq.wokao.model.paper.NormalIExamPaper;
import com.zgq.wokao.parser.ParserHelper;

import java.io.FileNotFoundException;

import io.realm.Realm;

/**
 * Created by zgq on 2017/2/20.
 */

public class ParserThread extends Thread {
    private String filePath;
    private OnCompletedListener listener;
    public ParserThread(String filePath){
        this.filePath = filePath;
    }

    public void setListener(OnCompletedListener listener){
        this.listener = listener;
    }
    @Override
    public void run() {
        super.run();
        try {
            Realm realm  = Realm.getDefaultInstance();
            final NormalIExamPaper paper = ParserHelper.getInstance().parse(FileUtil.getOrInitAppStoragePath()+"/default_1.txt");
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    realm.copyToRealm(paper);
                }
            });
            Log.d("---->>",paper.getPaperInfo().getTitle());
            Log.d("---->>",paper.getDiscussQuestions().get(0).getBody().getContent());

            listener.onCompleted(paper);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public interface OnCompletedListener{
        public void onCompleted(NormalIExamPaper paper);
    }
}
