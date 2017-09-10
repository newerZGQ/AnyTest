package com.zgq.wokao.action.login;

import com.zgq.wokao.action.BaseAction;
import com.zgq.wokao.data.sp.SPConstant;
import com.zgq.wokao.data.sp.SharedPreferencesHelper;

/**
 * Created by zgq on 2017/3/1.
 */

public class LoginAction extends BaseAction implements ILoginAction {
    private LoginAction() {
    }

    public static class InstanceHolder {
        public static LoginAction instance = new LoginAction();
    }

    public static LoginAction getInstance() {
        return InstanceHolder.instance;
    }

    @Override
    public boolean isFirstTimeLogin() {
        if (!SharedPreferencesHelper.contains(SPConstant.FIRST_TIME_LOGIN)) {
            SharedPreferencesHelper.put(SPConstant.FIRST_TIME_LOGIN, true);
        }
        return (boolean) SharedPreferencesHelper.get(SPConstant.FIRST_TIME_LOGIN, true);
    }

    @Override
    public void setFirstTimeLoginFalse() {
        SharedPreferencesHelper.put(SPConstant.FIRST_TIME_LOGIN, false);
    }
}
