package com.zgq.wokao.module.parser;

import com.zgq.wokao.module.base.BasePresenter;
import com.zgq.wokao.parser.ParserHelper;
import com.zgq.wokao.repository.RimRepository;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ParserPresenter extends BasePresenter<ParserContract.View>
        implements ParserContract.Presenter {

    @Inject
    RimRepository repository;

    @Inject
    public ParserPresenter(){}

    @Override
    public void parseDocument(final String filePath) {
        view.showLoading();
        Observable.just(filePath)
                .subscribeOn(Schedulers.io())
                .flatMap(s -> Observable.just(ParserHelper.getInstance().parse(filePath)))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(normalExamPaperOptional -> {
                    if (normalExamPaperOptional.isPresent()){
                        view.showSuccessToast();
                        repository.saveExamPaper(normalExamPaperOptional.get());
                        repository.setSked(normalExamPaperOptional.get(), true);
                    }else{
                        view.showFailedToast();
                    }
                    view.destoryView();
                });
    }

    @Override
    public void subscribe() {
        super.subscribe();
    }
}
