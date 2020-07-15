package com.zgq.wokao.module.welcome;

import android.content.Context;
import android.os.Environment;

import com.zgq.wokao.module.home.summary.StudySummary;
import com.zgq.wokao.module.home.summary.TotalDailyCount;
import com.zgq.wokao.module.base.BasePresenter;
import com.zgq.wokao.parser.ParserHelper;
import com.zgq.wokao.repository.RimRepository;
import com.zgq.rim.common.utils.DateUtil;
import com.zgq.wokao.util.FileUtil;

import java.io.File;
import java.util.UUID;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.realm.RealmList;

public class WelcomePresenter extends BasePresenter<WelcomeContract.MainView>
        implements WelcomeContract.MainPresenter {

    private RimRepository repository;

    @Inject
    public WelcomePresenter(RimRepository repository) {
        this.repository = repository;
    }

    @Override
    public void subscribe() {
        super.subscribe();
        checkStudySummary();
    }

    private void checkStudySummary() {
        RealmList<TotalDailyCount> realmList = new RealmList<>();
        String today = DateUtil.getCurrentDate();
        for (int i = 0; i < 7; i++) {
            TotalDailyCount totalDailyCount = TotalDailyCount
                    .builder()
                    .date(DateUtil.getTargetDateApart(today, -i))
                    .id(UUID.randomUUID().toString())
                    .build();
            realmList.add(0, totalDailyCount);
        }
        repository.saveSummary(StudySummary.builder()
                .lastWeekRecords(realmList)
                .id(UUID.randomUUID().toString())
                .build());
    }

    public void checkSample(Context context) {

        String folder = Environment.getExternalStorageDirectory().getPath() + "/wokao";
        if (FileUtil.SdcardMountedRight()) {
            File file = new File(folder);
            if (!file.exists()) {
                file.mkdir();
            }
        }

        String samplePath = folder + "/sample.txt";
        File sample = new File(samplePath);
        if (sample.exists()) {
            view.goHomeActivity();
            return;
        }
        FileUtil.transAssets2SD(context,"sample.txt", samplePath);

        try {
            parseDocument(samplePath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void parseDocument(String filePath) {
        Observable.just(filePath)
                .subscribeOn(Schedulers.io())
                .flatMap(s -> Observable.just(ParserHelper.getInstance().parse(filePath)))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(normalExamPaperOptional -> {
                    if (normalExamPaperOptional.isPresent()){
                        repository.saveExamPaper(normalExamPaperOptional.get());
                        repository.setSked(normalExamPaperOptional.get(), true);
                        view.goHomeActivity();
                    }
                });
    }
}
