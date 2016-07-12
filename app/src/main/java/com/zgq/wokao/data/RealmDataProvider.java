package com.zgq.wokao.data;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import com.zgq.wokao.Util.FileUtil;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import cn.qqtheme.framework.util.StorageUtils;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmList;
import io.realm.RealmResults;

/**
 * Created by zgq on 16-6-20.
 */
public class RealmDataProvider {
    public static boolean saveExampaperToRealm(NormalExamPaper examPaper,Realm realm){
        if (realm == null) return false;
        if (examPaperIsExist(examPaper,realm)) {
            return false;
        }
        realm.beginTransaction();
        realm.copyToRealm(examPaper);
        realm.commitTransaction();
        return true;
    }
    public static RealmResults getAllExamPapers(){
        return null;
    }
    public static ArrayList<NormalExamPaper> getAllExamPaper(Realm realm){
        if (realm == null) return null;
        RealmResults<NormalExamPaper> results = realm.where(NormalExamPaper.class).findAll();
        ArrayList<NormalExamPaper> list = new ArrayList<>();
        for (int i = 0; i < results.size();i++){
            list.add(results.get(i));
        }
        return list;
    }

    public static ArrayList<ExamPaperInfo> getAllExamPaperInfo(Realm realm){
        if (realm == null) return null;
        RealmResults<NormalExamPaper> results = realm.where(NormalExamPaper.class).findAll();
        ArrayList<ExamPaperInfo> list = new ArrayList<>();
        for (int i = 0; i < results.size();i++){
            list.add(results.get(i).getPaperInfo());
        }
        return list;
    }
    public static boolean examPaperIsExist(NormalExamPaper paper,Realm realm){
        if (paper == null) {
            return false;
        }

        ArrayList<ExamPaperInfo> infos = getAllExamPaperInfo(realm);
        String thisInfo = paper.getPaperInfo().getTitle() + paper.getPaperInfo().getAuthor();
        for (int i = 0; i<infos.size();i++){
            if (thisInfo.equals(infos.get(i).getTitle()+infos.get(i).getAuthor())) return true;
        }
        return false;
    }


    private static ExecutorService pool = Executors.newFixedThreadPool(4);
    public static void parseTxt2Realm(File txtFile, File xmlFile, Realm realm, Handler handler){
        if (txtFile == null || xmlFile == null || !FileUtil.isTxtFile(txtFile) || !FileUtil.isXmlFile(xmlFile)){
            return;
        }
        NormalExamPaper paper = null;
        Callable callable = new ParseTxt2Realm(txtFile,xmlFile);
        Future future = pool.submit(callable);
        try {
            paper = (NormalExamPaper) future.get();
        }catch (Exception e){

        }
        if (paper == null || paper.getPaperInfo().getTitle() ==null ||paper.getPaperInfo().getTitle().equals("")) return;
        if (saveExampaperToRealm(paper,realm)){
            Message message = Message.obtain();
            message.what = 0X1111;
            handler.sendMessage(message);
        }
    }


    public static class ParseTxt2Realm implements Callable{
        private File txtFile;
        private File xmlFile;
        public ParseTxt2Realm(File txtFile,File xmlFile){
            this.txtFile = txtFile;
            this.xmlFile = xmlFile;
        }
        @Override
        public NormalExamPaper call() {
            DataTxt2XmlParser dataTxt2XmlParser = DataTxt2XmlParser.getInstance();
            DataXml2ObjParser dataXml2ObjParser = DataXml2ObjParser.getInstance();
            NormalExamPaper normalExamPaper = null;
            try {
                if (xmlFile.exists()){
                    xmlFile.delete();
                }
                if (!xmlFile.exists()){
                    xmlFile.createNewFile();
                }
                dataTxt2XmlParser.parse(txtFile,xmlFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
            try{
                normalExamPaper = dataXml2ObjParser.parse(xmlFile);
            }catch (Exception e){
                Log.d("----normal","exception");
            }
            return normalExamPaper;
        }
    }
}
