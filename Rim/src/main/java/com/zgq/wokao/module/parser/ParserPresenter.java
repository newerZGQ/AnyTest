package com.zgq.wokao.module.parser;

import com.google.common.base.Optional;
import com.orhanobut.logger.Logger;
import com.zgq.wokao.entity.paper.NormalExamPaper;
import com.zgq.wokao.module.base.BasePresenter;
import com.zgq.wokao.parser.ParserHelper;
import com.zgq.wokao.repository.RimRepository;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
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
                .flatMap(new Function<String, ObservableSource<Optional<NormalExamPaper>>>() {
                    @Override
                    public ObservableSource<Optional<NormalExamPaper>> apply(String s) throws Exception {
                        return Observable.just(ParserHelper.getInstance().parse(filePath));
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Optional<NormalExamPaper>>() {
                    @Override
                    public void accept(Optional<NormalExamPaper> normalExamPaperOptional) throws Exception {
                        if (normalExamPaperOptional.isPresent()){
                            view.showSuccessToast();
                            repository.saveExamPaper(normalExamPaperOptional.get());
                            repository.setSked(normalExamPaperOptional.get(), true);
                        }else{
                            view.showFailedToast();
                        }
                        view.destoryView();
                    }
                });
    }

    @Override
    public void subscribe() {
        super.subscribe();
    }
}
