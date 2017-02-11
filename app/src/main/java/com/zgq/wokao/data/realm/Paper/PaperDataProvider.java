package com.zgq.wokao.data.realm.Paper;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.zgq.wokao.Util.FileUtil;
import com.zgq.wokao.data.realm.BaseRealmProvider;
import com.zgq.wokao.model.ExamPaper;
import com.zgq.wokao.model.ExamPaperInfo;
import com.zgq.wokao.model.NormalExamPaper;
import com.zgq.wokao.model.Searchable;
import com.zgq.wokao.parser.DataTxt2XmlParser;
import com.zgq.wokao.parser.DataXml2ObjParser;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmResults;

/**
 * Created by zgq on 16-6-20.
 */
public class PaperDataProvider extends BaseRealmProvider<NormalExamPaper> implements IPaperDataProvider {

    private Realm realm = Realm.getDefaultInstance();

    private PaperDataProvider() {
        setClass(NormalExamPaper.class);
    }

    public static final PaperDataProvider getInstance() {
        return ProviderHolder.instance;
    }


    private static class ProviderHolder {
        public static PaperDataProvider instance = new PaperDataProvider();
    }

    @Override
    public void save(NormalExamPaper entity) {
        if (examPaperIsExist(entity)){
            return;
        }
        super.save(entity);
    }

    @Override
    public List<NormalExamPaper> getAllPaper() {
        RealmResults<NormalExamPaper> results = realm.where(NormalExamPaper.class).findAll();
        ArrayList<NormalExamPaper> list = new ArrayList<>();
        for (int i = 0; i < results.size(); i++) {
            list.add(results.get(i));
        }
        return list;
    }

    @Override
    public List<ExamPaperInfo> getAllPaperInfo() {
        RealmResults<NormalExamPaper> results = realm.where(NormalExamPaper.class).findAll();
        ArrayList<ExamPaperInfo> list = new ArrayList<>();
        for (int i = 0; i < results.size(); i++) {
            list.add(results.get(i).getPaperInfo());
        }
        return list;
    }

    public boolean examPaperIsExist(NormalExamPaper paper) {
        if (paper == null) {
            return false;
        }
        ArrayList<ExamPaperInfo> infos = (ArrayList<ExamPaperInfo>) getAllPaperInfo();
        String thisInfo = paper.getPaperInfo().getTitle() + paper.getPaperInfo().getAuthor();
        for (int i = 0; i < infos.size(); i++) {
            if (thisInfo.equals(infos.get(i).getTitle() + infos.get(i).getAuthor())) return true;
        }
        return false;
    }

    public List<Searchable> getSearchResultsList(String query) {
        return null;
    }


    private static ExecutorService pool = Executors.newFixedThreadPool(4);

    public void parseTxt2Realm(File txtFile, File xmlFile, Realm realm, Handler handler) {
        if (txtFile == null || xmlFile == null || !FileUtil.isTxtFile(txtFile) || !FileUtil.isXmlFile(xmlFile)) {
            return;
        }
        NormalExamPaper paper = null;
        Callable callable = new ParseTxt2Realm(txtFile, xmlFile);
        Future future = pool.submit(callable);
        try {
            paper = (NormalExamPaper) future.get();
        } catch (Exception e) {
            Message message = Message.obtain();
            message.what = 0X1113;
            handler.sendMessage(message);
        }
        if (paper == null || paper.getPaperInfo().getTitle() == null || paper.getPaperInfo().getTitle().equals("")) {
            Message message = Message.obtain();
            message.what = 0X1112;
            handler.sendMessage(message);
            return;
        }
        save(paper);
        Message message = Message.obtain();
        message.what = 0X1111;
        handler.sendMessage(message);

    }


    public static class ParseTxt2Realm implements Callable {
        private File txtFile;
        private File xmlFile;

        public ParseTxt2Realm(File txtFile, File xmlFile) {
            this.txtFile = txtFile;
            this.xmlFile = xmlFile;
        }

        @Override
        public NormalExamPaper call() {
            DataTxt2XmlParser dataTxt2XmlParser = DataTxt2XmlParser.getInstance();
            DataXml2ObjParser dataXml2ObjParser = DataXml2ObjParser.getInstance();
            NormalExamPaper normalExamPaper = null;
            try {
                if (xmlFile.exists()) {
                    xmlFile.delete();
                }
                if (!xmlFile.exists()) {
                    xmlFile.createNewFile();
                }
                dataTxt2XmlParser.parse(txtFile, xmlFile);
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                normalExamPaper = dataXml2ObjParser.parse(xmlFile);
            } catch (Exception e) {
                Log.d("----normal", "exception");
            }
            return normalExamPaper;
        }
    }
}
