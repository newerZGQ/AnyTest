package com.zgq.wokao.action.login;

/**
 * Created by zgq on 2017/3/1.
 */

public interface ILoginAction {
    boolean isFirstTimeLogin();

    void setFirstTimeLoginFalse();
}
