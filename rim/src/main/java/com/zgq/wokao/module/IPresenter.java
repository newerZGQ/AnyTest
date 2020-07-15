package com.zgq.wokao.module;

public interface IPresenter<T extends IView> {

    void takeView(T view);

    void dropView();

    void subscribe();

    void unsubscribe();

}
