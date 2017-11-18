package com.zgq.wokaofree;

import android.app.Activity;
import android.app.Application;
import android.os.Environment;

import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;
import com.zgq.wokaofree.Util.ContextUtil;
import com.zgq.wokaofree.Util.FileUtil;
import com.zgq.wokaofree.Util.FontsUtil;
import com.zgq.wokaofree.Util.StringUtil;
import com.zgq.wokaofree.data.sp.SharedPreferencesHelper;

import java.io.File;
import java.io.IOException;
import java.util.Stack;

import cn.qqtheme.framework.util.StorageUtils;
import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by zgq on 16-6-22.
 */
public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
        // The Realm file will be located in Context.getFilesDir() with name "default.realm"
        RealmConfiguration config = new RealmConfiguration.Builder(this).build();
        Realm.setDefaultConfiguration(config);

        ContextUtil.init(this);
        FontsUtil.init(this);

        Logger.addLogAdapter(new AndroidLogAdapter());

        SharedPreferencesHelper.init(this);
        StringUtil.init(this);
    }

    public static final String TAG = "MyApplication";

    public static MyApplication application;


    private Stack<Activity> activityStack;


    public static MyApplication getInstance() {
        return application;
    }


    /***************************** activity管理 start **********************************/

    /**
     * add Activity 添加Activity到栈
     */
    public synchronized void addActivity(Activity activity) {

        if (activityStack == null) {
            activityStack = new Stack<Activity>();
        }
        activityStack.add(activity);

    }

    /**
     * get current Activity 获取当前Activity（栈中最后一个压入的）
     */
    public synchronized Activity currentActivity() {
        Activity activity = activityStack.lastElement();
        return activity;
    }

    /**
     * 结束当前Activity（栈中最后一个压入的）
     */
    public synchronized void finishActivity() {
        Activity activity = activityStack.lastElement();
        finishActivity(activity);
    }

    /**
     * 结束指定的Activity
     */
    public synchronized void finishActivity(Activity activity) {
        if (activity != null) {
            activityStack.remove(activity);
            activity.finish();
            activity = null;
        }
    }

    public synchronized void removeActivity(Activity activity) {
        if (activity != null) {
            activityStack.remove(activity);
        }
    }

    /**
     * 结束指定类名的Activity
     */
    public synchronized void finishActivity(Class<?> cls) {
        for (Activity activity : activityStack) {
            if (activity.getClass().equals(cls)) {
                finishActivity(activity);
                return;
            }
        }
    }

    /**
     * 结束所有Activity
     */
    public synchronized void finishAllActivity() {
        for (int i = 0, size = activityStack.size(); i < size; i++) {
            if (null != activityStack.get(i)) {
                activityStack.get(i).finish();
            }
        }
        activityStack.clear();

    }

    /**
     * 退出应用程序
     */
    public void AppExit() {
        try {
            finishAllActivity();
        } catch (Exception e) {
        }
    }

    /***************************** activity管理 end **********************************/

}