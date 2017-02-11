package com.zgq.wokao;

import android.app.Application;

import com.zgq.wokao.Util.FileUtil;
import com.zgq.wokao.Util.StringUtil;
import com.zgq.wokao.data.sp.SharedPreferencesHelper;

import java.io.File;

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
        // The Realm file will be located in Context.getFilesDir() with name "default.realm"
        RealmConfiguration config = new RealmConfiguration.Builder(this).build();
        Realm.setDefaultConfiguration(config);

        if (FileUtil.SdcardMountedRight()){
            File file = new File(StorageUtils.getRootPath(this)+"/wokao");
            if (!file.exists()){
                file.mkdir();
            }
        }

        SharedPreferencesHelper.init(this);
        StringUtil.init(this);
    }
}
