package com.zgq.wokao.data.realm.Paper;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.zgq.wokao.Util.FileUtil;
import com.zgq.wokao.Util.ListUtil;
import com.zgq.wokao.data.realm.BaseRealmProvider;
import com.zgq.wokao.model.paper.DiscussQuestion;
import com.zgq.wokao.model.paper.ExamPaper;
import com.zgq.wokao.model.paper.ExamPaperInfo;
import com.zgq.wokao.model.paper.FillInQuestion;
import com.zgq.wokao.model.paper.MultChoQuestion;
import com.zgq.wokao.model.paper.NormalExamPaper;
import com.zgq.wokao.model.paper.Question;
import com.zgq.wokao.model.paper.QuestionType;
import com.zgq.wokao.model.search.SearchQstItem;
import com.zgq.wokao.model.search.Searchable;
import com.zgq.wokao.model.paper.SglChoQuestion;
import com.zgq.wokao.model.paper.TFQuestion;
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
import io.realm.RealmQuery;
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

    /**
     * 保存paper
     * @param entity
     */
    @Override
    public void save(NormalExamPaper entity) {
        if (examPaperIsExist(entity)){
            return;
        }
        super.save(entity);
    }

    /**
     * get所有Paper
     * @return
     */
    @Override
    public List<NormalExamPaper> getAllPaper() {
        RealmResults<NormalExamPaper> results = realm.where(NormalExamPaper.class).findAll();
        ArrayList<NormalExamPaper> list = new ArrayList<>();
        for (int i = 0; i < results.size(); i++) {
            list.add(results.get(i));
        }
        return list;
    }

    /**
     * get所有paperinfo
     * @return
     */
    @Override
    public List<ExamPaperInfo> getAllPaperInfo() {
        RealmResults<NormalExamPaper> results = realm.where(NormalExamPaper.class).findAll();
        ArrayList<ExamPaperInfo> list = new ArrayList<>();
        for (int i = 0; i < results.size(); i++) {
            list.add(results.get(i).getPaperInfo());
        }
        return list;
    }

    /**
     * 搜索符合条件的item
     * @param query
     * @return
     */
    @Override
    public List<Searchable> search(String query) {
        if (query == null || query.equals("")){
            return null;
        }
        List<ExamPaperInfo> infos = searchPaperInfo(query);
        List<FillInQuestion> fillInQst = searchQuestion(query,FillInQuestion.class);
        List<TFQuestion> tfQst = searchQuestion(query,TFQuestion.class);
        List<SglChoQuestion> sglQst = searchQuestion(query,SglChoQuestion.class);
        List<MultChoQuestion> mlcQst = searchQuestion(query,MultChoQuestion.class);
        List<DiscussQuestion> disQst = searchQuestion(query,DiscussQuestion.class);
        return ListUtil.assem(cast2Searchable(infos),
                cast2Searchable(fillInQst),
                cast2Searchable(tfQst),
                cast2Searchable(sglQst),
                cast2Searchable(mlcQst),
                cast2Searchable(disQst));
    }

    /**
     * 搜索满足条件的paperinfo
     * @param query
     * @return
     */
    private List<ExamPaperInfo> searchPaperInfo(String query){
        if (query == null || query.equals("")) return null;
        RealmQuery<ExamPaperInfo> infoQuery = realm.where(ExamPaperInfo.class);
        infoQuery.contains("title",query);
        infoQuery.or().contains("author",query);
        List<ExamPaperInfo> paperInfos = infoQuery.findAll();
        return paperInfos;
    }

    /**
     * 搜索满足条件的Question
     * @param query
     * @param question
     * @param <T>
     * @return
     */
    private <T extends RealmObject> List<T> searchQuestion(String query,Class question){
        if (query == null || query.equals("")) return null;
        RealmQuery<T> queryQuestion = realm.where(question);
        queryQuestion.contains("body",query);
        List<T> results = queryQuestion.findAll();
        return results;
    }

    public List<SearchQstItem> searchQstFromPaper(String query,NormalExamPaper paper){
        List<SearchQstItem> results = new ArrayList<>();
        results = ListUtil.assem(
                searchQstFromList(
                        query,
                        (List) paper.getFillInQuestions(),
                        paper.getPaperInfo(), QuestionType.fillin.getIndex()),
                searchQstFromList(
                        query,
                        (List)paper.getTfQuestions(),
                        paper.getPaperInfo(),
                        QuestionType.tf.getIndex()),
                searchQstFromList(
                        query,
                        (List)paper.getSglChoQuestions(),
                        paper.getPaperInfo(),
                        QuestionType.sglc.getIndex()),
                searchQstFromList(
                        query,
                        (List)paper.getMultChoQuestions(),
                        paper.getPaperInfo(),
                        QuestionType.mtlc.getIndex()),
                searchQstFromList(
                        query,
                        (List)paper.getDiscussQuestions(),
                        paper.getPaperInfo(),
                        QuestionType.disc.getIndex())
                );
        return results;
    }

    private List<SearchQstItem> searchQstFromList(String query, List<Question> list,
                                                  ExamPaperInfo info, int qstType){
        List<SearchQstItem> results = new ArrayList<>();
        for (Question tmp : list){
            if (tmp.getBody().contains(query)){
                SearchQstItem item = new SearchQstItem();
                item.setInfo(info);
                item.setQstType(qstType);
                item.setQst(tmp);
                item.setQstId(tmp.getId());
                results.add(item);
            }
        }
        return results;
    }

    /**
     * T实现了Searchable，该方法把list内所有item转换为Searchable类型
     * @param list
     * @param <T>
     * @return
     */
    private <T extends Searchable> List<Searchable> cast2Searchable(List<T> list){
        List<Searchable> results = new ArrayList<>();
        for (int i = 0; i < list.size(); i++){
            results.add(i, (Searchable)list.get(i));
        }
        return results;
    }


    /**
     * 检查paper是否存在，根据paper 名称和作者名称
     * @param paper
     * @return
     */
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
