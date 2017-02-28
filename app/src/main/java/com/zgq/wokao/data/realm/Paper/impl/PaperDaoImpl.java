package com.zgq.wokao.data.realm.Paper.impl;

import android.os.Handler;
import android.os.Message;

import com.zgq.wokao.Util.FileUtil;
import com.zgq.wokao.Util.ListUtil;
import com.zgq.wokao.Util.UUIDUtil;
import com.zgq.wokao.data.realm.BaseRealmProvider;
import com.zgq.wokao.data.realm.Paper.IPaperDao;
import com.zgq.wokao.data.realm.Paper.IQuestionDao;
import com.zgq.wokao.model.paper.IExamPaper;
import com.zgq.wokao.model.paper.NormalIExamPaper;
import com.zgq.wokao.model.paper.info.ExamIPaperInfo;
import com.zgq.wokao.model.paper.question.IQuestion;
import com.zgq.wokao.model.paper.QuestionType;
import com.zgq.wokao.model.search.SearchInfoItem;
import com.zgq.wokao.model.search.SearchQstItem;
import com.zgq.wokao.model.search.Searchable;
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
public class PaperDaoImpl extends BaseRealmProvider<NormalIExamPaper> implements IPaperDao, IQuestionDao {

    private PaperDaoImpl() {
        setClass(NormalIExamPaper.class);
    }

    public static final PaperDaoImpl getInstance() {
        return ProviderHolder.instance;
    }

    @Override
    public void star(final IQuestion question) {
        getRealm().executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                question.getInfo().setStared(true);
            }
        });

    }

    @Override
    public void unStar(final IQuestion question) {
        getRealm().executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                question.getInfo().setStared(false);
            }
        });
    }

    @Override
    public void star(final IExamPaper paper) {
        getRealm().executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                paper.getPaperInfo().setStared(true);
            }
        });
    }

    @Override
    public void unStar(final IExamPaper paper) {
        getRealm().executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                paper.getPaperInfo().setStared(false);
            }
        });
    }

    @Override
    public void setTitle(IExamPaper paper, String title) {

    }

    @Override
    public void addToSchedule(final IExamPaper paper) {
        getRealm().executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                paper.getPaperInfo().setInSchedule(true);
            }
        });
    }

    @Override
    public void removeFromSchedule(final IExamPaper paper) {
        getRealm().executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                paper.getPaperInfo().setInSchedule(false);
            }
        });
    }

    @Override
    public void openSchedule(final IExamPaper paper) {
        getRealm().executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                paper.getPaperInfo().getSchedule().open();
            }
        });
    }

    @Override
    public void closeSchedule(final IExamPaper paper) {
        getRealm().executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                paper.getPaperInfo().getSchedule().close();
            }
        });
    }

    @Override
    public void setDailyCount(final IExamPaper paper, final int count) {
        getRealm().executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                paper.getPaperInfo().getSchedule().setDailyCount(count);
            }
        });
    }

    @Override
    public void updateDailyRecord(final IExamPaper paper) {
        getRealm().executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                paper.getPaperInfo().getSchedule().recordPlus1();
            }
        });
    }

    @Override
    public void updateQuestionRecord(final IQuestion question, final boolean isCorrect) {
        getRealm().executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                question.getRecord().updateRecord(isCorrect);
            }
        });
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
    public void save(NormalIExamPaper entity) {
        if (examPaperIsExist(entity)) {
            return;
        }
        super.save(entity);
    }

    @Override
    public void addExamPaper() {

    }

    @Override
    public void deleteExamPaper() {

    }

    /**
     * get所有Paper
     *
     * @return
     */
    @Override
    public List<NormalIExamPaper> getAllPaper() {
        RealmResults<NormalIExamPaper> results = getRealm().where(NormalIExamPaper.class).findAll();
        return changeRealmListToList(results);
    }

    /**
     * get所有paperinfo
     *
     * @return
     */
    @Override
    public List<ExamIPaperInfo> getAllPaperInfo() {
        RealmResults<ExamIPaperInfo> results = getRealm().where(ExamIPaperInfo.class).findAll();
        return changeRealmListToList(results);
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
    public List<ExamIPaperInfo> getSchedulePapers() {
        RealmResults<ExamIPaperInfo> results = getRealm()
                .where(ExamIPaperInfo.class)
                .equalTo("isInSchedule", true)
                .findAll();
        return changeRealmListToList(results);
    }

    /**
     * 搜索满足条件的SearchInfoItem,仅匹配试卷标题
     *
     * @param query
     * @return
     */
    public List<SearchInfoItem> searchInfoItem(String query) {
        if (query == null || query.equals("")) return null;
        RealmQuery<ExamIPaperInfo> infoQuery = getRealm().where(ExamIPaperInfo.class);
        infoQuery.contains("title", query);
//        infoQuery.or().contains("author",query);
        List<ExamIPaperInfo> paperInfos = infoQuery.findAll();
        List<SearchInfoItem> results = new ArrayList<>();
        for (ExamIPaperInfo info : paperInfos) {
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
        List<NormalIExamPaper> papers = getAllPaper();
        for (NormalIExamPaper tmp : papers) {
            results = ListUtil.assem(results, searchQstFromPaper(query, tmp));
        }
//        Log.d("---->>searchQstItemList",""+results.size());
        return results;
    }


    private List<SearchQstItem> searchQstFromPaper(String query, NormalIExamPaper paper) {
        List<SearchQstItem> results = new ArrayList<>();
        results = ListUtil.assem(
                searchQstFromList(
                        query,
                        (List) paper.getFillInQuestions(),
                        paper.getPaperInfo(), QuestionType.fillin.getIndex()),
                searchQstFromList(
                        query,
                        (List) paper.getTfQuestions(),
                        paper.getPaperInfo(),
                        QuestionType.tf.getIndex()),
                searchQstFromList(
                        query,
                        (List) paper.getSglChoQuestions(),
                        paper.getPaperInfo(),
                        QuestionType.sglc.getIndex()),
                searchQstFromList(
                        query,
                        (List) paper.getMultChoQuestions(),
                        paper.getPaperInfo(),
                        QuestionType.mtlc.getIndex()),
                searchQstFromList(
                        query,
                        (List) paper.getDiscussQuestions(),
                        paper.getPaperInfo(),
                        QuestionType.disc.getIndex())
        );
//        Log.d("---->>searchQstFromPape",""+results.size());
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
                                                  ExamIPaperInfo info, int qstType) {
        List<SearchQstItem> results = new ArrayList<>();
        for (IQuestion tmp : list) {
            if (tmp.getBody().getContent().contains(query)) {
//                Log.d("---->>searchQstFromList",tmp.getBody());
                SearchQstItem item = new SearchQstItem();
                item.setInfo(info);
                item.setQstType(qstType);
                item.setQst(tmp);
                item.setQstId(tmp.getInfo().getId());
                results.add(item);
            }
        }
//        Log.d("---->>searchQstFromList",""+results.size());
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
    public boolean examPaperIsExist(NormalIExamPaper paper) {
        if (paper == null) {
            return false;
        }
        ArrayList<ExamIPaperInfo> infos = (ArrayList<ExamIPaperInfo>) getAllPaperInfo();
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
        NormalIExamPaper paper = null;
        Callable callable = new ParseTxt2Realm(txtFile, xmlFile);
        Future future = pool.submit(callable);
        try {
            paper = (NormalIExamPaper) future.get();
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
        public NormalIExamPaper call() {
            DataTxt2XmlParser dataTxt2XmlParser = DataTxt2XmlParser.getInstance();
            DataXml2ObjParser dataXml2ObjParser = DataXml2ObjParser.getInstance();
            NormalIExamPaper normalExamPaper = null;
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
//                Log.d("----normal", "exception");
            }
            return normalExamPaper;
        }
    }
}
