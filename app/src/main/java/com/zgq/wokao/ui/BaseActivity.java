package com.zgq.wokao.ui;

/**
 * Created by zgq on 2017/2/11.
 *  * @类名称： BaseActivity.java
 * @类描述： BaseActivity ,便于管理activity，toast工具类，intent跳转类
 */

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.zgq.wokao.MyApplication;

public abstract class BaseActivity extends Activity {

    private Toast toast = null;

    /**
     * 当前Activity创建时来调用(第一次启动,本Activity被销毁后再次启动,未对android:
     * configChanges进行设置且配置发生改变时)
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        PushAgent mPushAgent = PushAgent.getInstance(this);
//        mPushAgent.enable();
//        PushAgent.getInstance(this).onAppStart();
        // 添加Activity到堆栈
        MyApplication.getInstance().addActivity(this);

        loadingDialog(this, "努力加载中...");

    }
    /** 自定义dialog **/
    protected Dialog dialog;

    /**
     * 自定义dialog
     *
     * @param context
     * @param dialogString
     */
    protected void loadingDialog(Context context, String dialogString) {
//        dialog = new Dialog(context, R.style.new_circle_progress);
//        dialog.setContentView(R.layout.layout_progressbar);
//        ((TextView) dialog.findViewById(R.id.emptyView)).setText(dialogString);// dialog显示时的字样
    }


    /**
     * 简化findViewById()
     *
     * @param id
     * @return
     */
    @SuppressWarnings("unchecked")
    protected <T extends View> T mFindViewById(int id) {
        // return 返回view时，加上泛型T
        return (T) findViewById(id);

    }

    /** * 当前Activity已onStop后(未销毁),重新再次进入后来调用 */
    @Override
    protected void onRestart() {
        super.onRestart();
    }

    /** * 在onCreate()和onRestart()之后回调 */
    @Override
    protected void onStart() {
        super.onStart();
    }

    /** * 在onStart()之后来调用 (获取到焦点,进入用户可操作界面) */
    @Override
    protected void onResume() {
        super.onResume();
    }

    /** * 当前Activity失去焦点后来调用 */
    @Override
    protected void onPause() {
        super.onPause();
    }

    /** * 当前Activity不再可见后将来调用 (在onPausse之后) */
    @Override
    protected void onStop() {
        super.onStop();
    }

    /** * 当前Activity被销毁来调用 ( android.app.Activity.finish() ) */
    @Override
    protected void onDestroy() {
        super.onDestroy();

        MyApplication.getInstance().removeActivity(this);

    }

    /**
     * 某个activity变得“容易”被系统销毁时，该activity的onSaveInstanceState就会被执行，
     * 除非该activity是被用户主动销毁的。
     * (按下HOME键？长按HOME键，选择运行其他的程序?按下电源按键?activity切换？屏幕方向切换？)
     * 此方法常常用来做一些应用中非持久性的存储
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    /**
     * 该activity被恢复时执行(前提是该activity的确已被销毁,即此方法与onSaveInstanceState()方法不一定对等成对调用)
     * ,且savedInstanceState参数还会传递到onCreate()内。 此方法常常用来做一些应用中非持久性的存储的恢复。
     */
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    /**
     * 此方法与清单中android:configChanges对等调用(不指定,配置改变时将重调用onCreate方法,指定后,
     * 指定情况下将不调用onCreate方法而来调用此方法,故你懂的啦)
     */
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @SuppressWarnings("deprecation")
    @Override
    public Object onRetainNonConfigurationInstance() {
        return super.onRetainNonConfigurationInstance();
    }

    /**
     * toast 短 int
     *
     * @param pResId
     */
    protected void showToast(int pResId) {
        showToast(getString(pResId));
    }

    /**
     * toast 长 int
     *
     * @param pResId
     */
    protected void showLongToast(int pResId) {
        showLongToast(getString(pResId));
    }

    /**
     * toast 短 String
     *
     * @param pMsg
     */
    protected void showToast(String pMsg) {
        if (toast == null) {
            initToast();
        }
        TextView view = (TextView) toast.getView();
        view.setText(pMsg);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.show();
    }

    /**
     * toast 长 string
     *
     * @param pMsg
     */
    protected void showLongToast(String pMsg) {
        if (toast == null) {
            initToast();
        }
        TextView view = (TextView) toast.getView();
        view.setText(pMsg);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.show();
    }

    @SuppressLint("ShowToast")
    private void initToast() {
        toast = Toast.makeText(getApplicationContext(), "", Toast.LENGTH_SHORT);
        TextView textView = new TextView(getApplicationContext());
        //自定义toast背景色
//        textView.setBackgroundResource(R.drawable.pop);
        textView.setPadding(15, 10, 15, 10);
        textView.setGravity(Gravity.CENTER);
        textView.setTextColor(0xffffffff);
        toast.setView(textView);
        toast.setGravity(Gravity.BOTTOM, 0, 50);

    }

    /**
     * Activity跳转
     *
     * @param pClass
     */
    protected void openActivity(Class<?> pClass) {
        openActivity(pClass, null);
    }

    /**
     * Activity跳转
     *
     * @param pClass
     * @param pBundle
     */
    protected void openActivity(Class<?> pClass, Bundle pBundle) {
        openActivity(pClass, pBundle, null);
    }

    /**
     * Activity跳转
     *
     * @param pClass
     * @param pBundle
     * @param uri
     */
    protected void openActivity(Class<?> pClass, Bundle pBundle, Uri uri) {
        Intent intent = new Intent(this, pClass);
        if (pBundle != null) {
            intent.putExtras(pBundle);
        }
        if (uri != null) {
            intent.setData(uri);
        }
        startActivity(intent);
    }

    /**
     * Activity跳转
     *
     * @param pAction
     */
    protected void openActivity(String pAction) {
        openActivity(pAction, null);
    }

    /**
     * Activity跳转
     *
     * @param pAction
     * @param pBundle
     */
    protected void openActivity(String pAction, Bundle pBundle) {
        openActivity(pAction, pBundle, null);
    }

    /**
     * Activity跳转
     *
     * @param pAction
     * @param pBundle
     * @param uri
     */
    protected void openActivity(String pAction, Bundle pBundle, Uri uri) {
        Intent intent = new Intent(pAction);
        if (pBundle != null) {
            intent.putExtras(pBundle);
        }
        if (uri != null) {
            intent.setData(uri);
        }
        startActivity(intent);
    }

    /**
     * intent
     *
     * @return
     */
    protected Intent getPrevIntent() {
        return getIntent();
    }

    /**
     * bundle
     *
     * @return
     */
    protected Bundle getPrevExtras() {
        return getPrevIntent().getExtras();
    }

    /** 关闭应用 */
    public void finishDefault() {
        finish();
    }


    /**
     * 检查当前网络是否可用
     * @param activity
     * @return
     */

    protected boolean isNetworkAvailable(Activity activity) {
        Context context = activity.getApplicationContext();
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (connectivityManager == null) {
            return false;
        } else {
            NetworkInfo[] networkInfo = connectivityManager.getAllNetworkInfo();

            if (networkInfo != null && networkInfo.length > 0) {
                for (int i = 0; i < networkInfo.length; i++) {
                    if (networkInfo[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        }
        return false;
    }


}
