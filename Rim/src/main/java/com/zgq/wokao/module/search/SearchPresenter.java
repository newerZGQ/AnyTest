package com.zgq.wokao.module.search;

import com.google.common.base.Strings;
import com.zgq.wokao.entity.paper.NormalExamPaper;
import com.zgq.wokao.entity.paper.question.QuestionType;
import com.zgq.wokao.module.base.BasePresenter;
import com.zgq.wokao.module.search.entity.HistorySuggestion;
import com.zgq.wokao.module.search.entity.SearchInfoItem;
import com.zgq.wokao.module.search.entity.SearchQuestionItem;
import com.zgq.wokao.module.search.entity.Searchable;
import com.zgq.wokao.repository.RimRepository;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;
import javax.inject.Inject;

import io.reactivex.Flowable;

public class SearchPresenter extends BasePresenter<SearchContract.SearchView>
        implements SearchContract.SearchPresenter {
    RimRepository repository;

    @Inject
    public SearchPresenter(RimRepository repository){
        this.repository = repository;
    }

    @Override
    public void loadHistory(String query) {
        ArrayList<HistorySuggestion> suggestions = new ArrayList<>();
        if (Strings.isNullOrEmpty(query)){
            repository.getLastestSearchHistory(10)
                    .flatMap(searchHistory -> Flowable.just(new HistorySuggestion(searchHistory.getContent())))
                    .subscribe(suggestion -> suggestions.add(suggestion));
        }else{
            repository.findRelativeSearchHistory(query,10)
                    .flatMap(searchHistory -> Flowable.just(new HistorySuggestion(searchHistory.getContent())))
                    .subscribe(suggestion -> suggestions.add(suggestion));
        }
        view.showHistory(suggestions);
    }

    @Override
    public void search(String query) {
        if (Strings.isNullOrEmpty(query)){
            return;
        }
        ArrayList<Searchable> searchResult = new ArrayList<>();
        repository.getAllExamPaper()
                .flatMap(normalExamPapers -> Flowable.fromIterable(normalExamPapers))
                .flatMap(examPaper -> Flowable.fromIterable(parseSearchable(examPaper,query)))
                .subscribe(searchable -> searchResult.add(searchable));
        view.showSearchResult(searchResult);
    }

    @Nonnull
    private List<Searchable> parseSearchable(NormalExamPaper paper, String query){
        ArrayList<Searchable> result = new ArrayList<>();
        if (paper.getPaperInfo().getTitle().contains(query)){
            result.add(new SearchInfoItem(paper.getPaperInfo()));
        }
        Flowable.fromIterable(paper.getFillInQuestions())
                .filter(fillInQuestion -> fillInQuestion.getBody().getContent().contains(query)
                )
                .flatMap(fillInQuestion -> Flowable.just(
                        new SearchQuestionItem(paper.getPaperInfo(),
                                QuestionType.FILLIN,
                                fillInQuestion.getInfo().getIndex(),
                                fillInQuestion)))
                .subscribe(searchQuestionItem ->  result.add(searchQuestionItem));
        Flowable.fromIterable(paper.getTfQuestions())
                .filter(tfQuestion -> tfQuestion.getBody().getContent().contains(query)
                )
                .flatMap(tfQuestion -> Flowable.just(
                        new SearchQuestionItem(paper.getPaperInfo(),
                                QuestionType.TF,
                                tfQuestion.getInfo().getIndex(),
                                tfQuestion)))
                .subscribe(searchQuestionItem ->  result.add(searchQuestionItem));
        Flowable.fromIterable(paper.getSglChoQuestions())
                .filter(sglChoQuestion -> sglChoQuestion.getBody().getContent().contains(query)
                )
                .flatMap(sglChoQuestion -> Flowable.just(
                        new SearchQuestionItem(paper.getPaperInfo(),
                                QuestionType.SINGLECHOOSE,
                                sglChoQuestion.getInfo().getIndex(),
                                sglChoQuestion)))
                .subscribe(searchQuestionItem ->  result.add(searchQuestionItem));
        Flowable.fromIterable(paper.getMultChoQuestions())
                .filter(multChoQuestion -> multChoQuestion.getBody().getContent().contains(query)
                )
                .flatMap(multChoQuestion -> Flowable.just(
                        new SearchQuestionItem(paper.getPaperInfo(),
                                QuestionType.MUTTICHOOSE,
                                multChoQuestion.getInfo().getIndex(),
                                multChoQuestion)))
                .subscribe(searchQuestionItem ->  result.add(searchQuestionItem));
        Flowable.fromIterable(paper.getDiscussQuestions())
                .filter(discussQuestion -> discussQuestion.getBody().getContent().contains(query)
                )
                .flatMap(discussQuestion -> Flowable.just(
                        new SearchQuestionItem(paper.getPaperInfo(),
                                QuestionType.DISCUSS,
                                discussQuestion.getInfo().getIndex(),
                                discussQuestion)))
                .subscribe(searchQuestionItem ->  result.add(searchQuestionItem));
        return result;
    }
}
