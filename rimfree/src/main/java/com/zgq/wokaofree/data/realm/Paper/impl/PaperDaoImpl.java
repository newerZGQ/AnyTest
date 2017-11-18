package com.zgq.wokaofree.data.realm.Paper.impl;

import android.os.Handler;
import android.os.Message;

import com.zgq.wokaofree.Util.DateUtil;
import com.zgq.wokaofree.Util.FileUtil;
import com.zgq.wokaofree.Util.ListUtil;
import com.zgq.wokaofree.Util.UUIDUtil;
import com.zgq.wokaofree.data.realm.BaseRealmProvider;
import com.zgq.wokaofree.data.realm.Paper.IPaperDao;
import com.zgq.wokaofree.model.paper.IExamPaper;
import com.zgq.wokaofree.model.paper.NormalExamPaper;
import com.zgq.wokaofree.model.paper.QuestionType;
import com.zgq.wokaofree.model.paper.info.ExamPaperInfo;
import com.zgq.wokaofree.model.paper.info.IPaperInfo;
import com.zgq.wokaofree.model.paper.question.IQuestion;
import com.zgq.wokaofree.model.schedule.DailyRecord;
import com.zgq.wokaofree.model.schedule.Schedule;
import com.zgq.wokaofree.model.search.SearchInfoItem;
import com.zgq.wokaofree.model.search.SearchQstItem;
import com.zgq.wokaofree.model.search.Searchable;
import com.zgq.wokaofree.parser.DataTxt2XmlParser;
import com.zgq.wokaofree.parser.DataXml2ObjParser;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.RealmQuery;
import io.realm.RealmResults;

/**
 * Created by zgq on 16-6-20.
 */
public class PaperDaoImpl extends BaseRealmProvider<NormalExamPaper> implements IPaperDao {

    Realm realm = getRealm();

    private PaperDaoImpl() {
        setClass(NormalExamPaper.class);
    }

    public static final PaperDaoImpl getInstance() {
        return ProviderHolder.instance;
    }


    @Override
    public synchronized void star(final IExamPaper paper) {
        realm.beginTransaction();
        paper.getPaperInfo().setStared(true);
        realm.commitTransaction();
    }

    @Override
    public synchronized void unStar(final IExamPaper paper) {
        realm.beginTransaction();
        paper.getPaperInfo().setStared(false);
        realm.commitTransaction();
    }

    @Override
    public synchronized void setTitle(IExamPaper paper, String title) {

    }

    @Override
    public synchronized void addToSchedule(final IExamPaper paper) {
        realm.beginTransaction();
        paper.getPaperInfo().setInSchedule(true);
        realm.commitTransaction();
    }

    @Override
    public synchronized void removeFromSchedule(final IExamPaper paper) {
        realm.beginTransaction();
        paper.getPaperInfo().setInSchedule(false);
        realm.commitTransaction();
    }

    @Override
    public void setLastStudyDate(IExamPaper paper) {
        realm.beginTransaction();
        paper.getPaperInfo().setLastStudyDate(DateUtil.getCurrentDate());
        realm.commitTransaction();
    }

    @Override
    public synchronized void openSchedule(final IExamPaper paper) {
        realm.beginTransaction();
        paper.getPaperInfo().getSchedule().open();
        realm.commitTransaction();

        paper.getPaperInfo().getSchedule().addRecord();
    }

    @Override
    public synchronized void closeSchedule(final IExamPaper paper) {
        realm.beginTransaction();
        paper.getPaperInfo().getSchedule().close();
        realm.commitTransaction();
    }

    @Override
    public synchronized void setDailyCount(final IExamPaper paper, final int count) {
        realm.beginTransaction();
        paper.getPaperInfo().getSchedule().setDailyCount(count);
        realm.commitTransaction();
    }

    @Override
    public synchronized void updateDailyRecord(final IExamPaper paper) {
        realm.beginTransaction();
        paper.getPaperInfo().getSchedule().recordPlus1();
        realm.commitTransaction();
    }

    @Override
    public synchronized void addRecord(final Schedule schedule, final DailyRecord dailyRecord) {
        realm.beginTransaction();
        RealmList<DailyRecord> dailyRecords = schedule.getDailyRecords();
        dailyRecords.add(dailyRecord);

        realm.commitTransaction();
    }

    @Override
    public void setLastStudyInfo(String paperId, QuestionType questionType, int num) {
        realm.beginTransaction();
        IExamPaper paper = query(paperId);
        paper.getPaperInfo().getSchedule().setLastStudyNum(num);
        paper.getPaperInfo().getSchedule().setLastStudyType(questionType);
        realm.commitTransaction();
    }

    @Override
    public QuestionType getLastStudyType(String paperId) {
        IExamPaper paper = query(paperId);
        return QuestionType.valueOf(paper.getPaperInfo().getSchedule().getLastStudyType());
    }

    @Override
    public int getLastStudyNum(String paperId) {
        IExamPaper paper = query(paperId);
        return paper.getPaperInfo().getSchedule().getLastStudyNum();
    }


    private static class ProviderHolder {
        private static PaperDaoImpl instance = new PaperDaoImpl();
    }

    /**
     * 保存paper
     *
     * @param entity
     */
    @Override
    public void save(NormalExamPaper entity) {
        if (examPaperIsExist(entity)) {
            return;
        }
        super.save(entity);
    }

    /**
     * get所有Paper
     *
     * @return
     */
    @Override
    public List<NormalExamPaper> getAllPaper() {
        RealmResults<NormalExamPaper> results = realm.where(NormalExamPaper.class).findAll();
        return changeRealmListToList(results);
    }

    @Override
    public List<NormalExamPaper> getAllPaperInSchdl() {
        RealmResults<NormalExamPaper> results = realm.where(NormalExamPaper.class)
                .equalTo("paperInfo.isInSchedule", true)
                .findAll();
        return changeRealmListToList(results);
    }

    /**
     * get所有paperinfo
     *
     * @return
     */
    @Override
    public List<IPaperInfo> getAllPaperInfo() {
        RealmResults<ExamPaperInfo> results = realm.where(ExamPaperInfo.class).findAll();
        List<ExamPaperInfo> list = changeRealmListToList(results);
        return examPaperInfoToIPaperInfo(list);
    }

    @Override
    public List<IPaperInfo> getPaperInfosInSchdl() {
        RealmResults<ExamPaperInfo> results = realm.where(ExamPaperInfo.class).equalTo("isInSchedule", true).findAll();
        List<ExamPaperInfo> list = changeRealmListToList(results);
        return examPaperInfoToIPaperInfo(list);
    }

    private List<IPaperInfo> examPaperInfoToIPaperInfo(List<ExamPaperInfo> list) {
        ArrayList<IPaperInfo> results = new ArrayList<>();
        for (ExamPaperInfo info : list) {
            results.add((IPaperInfo) info);
        }
        return results;
    }

    /**
     * 搜索符合条件的item
     *
     * @param query
     * @return
     */
    @Override
    public List<Searchable> search(String query) {
        if (query == null || query.equals("")) {
            return null;
        }
        List<SearchInfoItem> infos = searchInfoItem(query);
        List<SearchQstItem> qstItems = searchQstItemList(query);
        List<Searchable> results = ListUtil.assem(cast2Searchable(infos), cast2Searchable(qstItems));
        return results;
    }

    @Override
    public List<ExamPaperInfo> getSchedulePapers() {
        RealmResults<ExamPaperInfo> results = realm
                .where(ExamPaperInfo.class)
                .equalTo("isInSchedule", true)
                .findAll();
        return changeRealmListToList(results);
    }

    @Override
    public void deleteById(String id) {
        realm.beginTransaction();
        RealmResults<NormalExamPaper> results = realm
                .where(NormalExamPaper.class)
                .equalTo("paperInfo.id", id)
                .findAll();
        for (int i = 0; i < results.size(); i++) {
            results.get(i).cascadeDelete();
        }
        realm.commitTransaction();
    }

    @Override
    public void updateStudyInfo(IExamPaper paper, boolean isCorrect) {
        realm.beginTransaction();
        paper.getPaperInfo().getSchedule().updateStudyInfo(isCorrect);
        realm.commitTransaction();
    }

    @Override
    public NormalExamPaper query(String id) {
        RealmResults<NormalExamPaper> results = realm
                .where(NormalExamPaper.class)
                .equalTo("paperInfo.id", id)
                .findAll();
        if (results == null || results.size() == 0) {
            return null;
        }
        return changeRealmListToList(results).get(0);
    }

    /**
     * 搜索满足条件的SearchInfoItem,仅匹配试卷标题
     *
     * @param query
     * @return
     */
    public List<SearchInfoItem> searchInfoItem(String query) {
        if (query == null || query.equals("")) return null;
        RealmQuery<ExamPaperInfo> infoQuery = realm.where(ExamPaperInfo.class);
        infoQuery.contains("title", query);
        List<ExamPaperInfo> paperInfos = infoQuery.findAll();
        List<SearchInfoItem> results = new ArrayList<>();
        for (ExamPaperInfo info : paperInfos) {
            SearchInfoItem item = new SearchInfoItem();
            item.setInfo(info);
            results.add(item);
        }
        return results;
    }


    /**
     * 从所有题目中匹配，仅匹配题干
     *
     * @param query
     * @return
     */
    public List<SearchQstItem> searchQstItemList(String query) {
        List<SearchQstItem> results = new ArrayList<>();
        List<NormalExamPaper> papers = getAllPaper();
        for (NormalExamPaper tmp : papers) {
            results = ListUtil.assem(results, searchQstFromPaper(query, tmp));
        }
        return results;
    }


    private List<SearchQstItem> searchQstFromPaper(String query, NormalExamPaper paper) {
        List<SearchQstItem> results = new ArrayList<>();
        results = ListUtil.assem(
                searchQstFromList(
                        query,
                        (List) paper.getFillInQuestions(),
                        paper.getPaperInfo(), QuestionType.FILLIN),
                searchQstFromList(
                        query,
                        (List) paper.getTfQuestions(),
                        paper.getPaperInfo(),
                        QuestionType.TF),
                searchQstFromList(
                        query,
                        (List) paper.getSglChoQuestions(),
                        paper.getPaperInfo(),
                        QuestionType.SINGLECHOOSE),
                searchQstFromList(
                        query,
                        (List) paper.getMultChoQuestions(),
                        paper.getPaperInfo(),
                        QuestionType.MUTTICHOOSE),
                searchQstFromList(
                        query,
                        (List) paper.getDiscussQuestions(),
                        paper.getPaperInfo(),
                        QuestionType.DISCUSS)
        );

        return results;
    }

    private <T extends RealmObject> List<T> changeRealmListToList(RealmResults<T> list) {
        List<T> results = new ArrayList<>();
        for (T t : list) {
            results.add(t);
        }
        return results;
    }

    private List<SearchQstItem> searchQstFromList(String query, List<IQuestion> list,
                                                  ExamPaperInfo info, QuestionType qstType) {
        List<SearchQstItem> results = new ArrayList<>();
        for (IQuestion tmp : list) {
            if (tmp.getBody().getContent().contains(query)) {
                SearchQstItem item = new SearchQstItem();
                item.setInfo(info);
                item.setQstType(qstType);
                item.setQst(tmp);
                item.setQstId(tmp.getInfo().getQstId());
                results.add(item);
            }
        }
        return results;
    }

    /**
     * T实现了Searchable，该方法把list内所有item转换为Searchable类型
     *
     * @param list
     * @param <T>
     * @return
     */
    private <T extends Searchable> List<Searchable> cast2Searchable(List<T> list) {
        List<Searchable> results = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            results.add(i, (Searchable) list.get(i));
        }
        return results;
    }


    /**
     * 检查paper是否存在，根据paper 名称和作者名称
     *
     * @param paper
     * @return
     */
    public boolean examPaperIsExist(NormalExamPaper paper) {
        if (paper == null) {
            return false;
        }

        if (realm.where(NormalExamPaper.class)
                .equalTo("paperInfo.id", paper.getPaperInfo().getId())
                .findAll()
                .size() == 0) {
            return false;
        }
        return true;
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
                normalExamPaper.getPaperInfo().setId(UUIDUtil.getID());
            } catch (Exception e) {

            }
            return normalExamPaper;
        }
    }
}
