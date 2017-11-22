package com.zgq.wokao.module.base;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.widget.TextView;
import android.widget.Toast;

import com.zgq.wokao.R;
import com.zgq.wokao.RimApplication;
import com.zgq.wokao.injector.components.ApplicationComponent;
import com.zgq.wokao.injector.modules.ActivityModule;
import com.zgq.wokao.module.BasePresenter;
import com.zgq.wokao.module.BaseView;

import javax.inject.Inject;

import butterknife.ButterKnife;

public abstract class BaseActivity<T extends BasePresenter> extends AppCompatActivity implements BaseView<T> {

    private Toast toast;

    protected abstract void daggerInject();

    @LayoutRes
    protected abstract int attachLayoutRes();

    @Inject
    protected T presenter;

    protected abstract void initViews();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        daggerInject();
        setContentView(attachLayoutRes());
        ButterKnife.bind(this);
        initViews();
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.takeView(this);
        presenter.subscribe();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.unsubscribe();
        presenter.dropView();
    }

    protected ApplicationComponent getAppComponent() {
        return RimApplication.getAppComponent();
    }

    protected ActivityModule getActivityModule() {
        return new ActivityModule(this);
    }

    protected void addFragment(int containerViewId, Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(containerViewId, fragment);
        fragmentTransaction.commit();
    }

    protected void addFragment(int containerViewId, Fragment fragment, String tag) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(containerViewId, fragment, tag);
        fragmentTransaction.addToBackStack(tag);
        fragmentTransaction.commit();
    }

    protected void replaceFragment(int containerViewId, Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(containerViewId, fragment);
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    protected void replaceFragment(int containerViewId, Fragment fragment, String tag) {
        if (getSupportFragmentManager().findFragmentByTag(tag) == null) {
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(containerViewId, fragment, tag);
            fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            fragmentTransaction.addToBackStack(tag);
            fragmentTransaction.commit();
        } else {
            getSupportFragmentManager().popBackStack(tag, 0);
        }
    }


    protected void openActivity(Class<?> pClass) {
        openActivity(pClass, null);
    }

    protected void openActivity(Class<?> pClass, Bundle pBundle) {
        openActivity(pClass, pBundle, null);
    }

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

    protected void openActivity(String pAction) {
        openActivity(pAction, null);
    }


    protected void openActivity(String pAction, Bundle pBundle) {
        openActivity(pAction, pBundle, null);
    }

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

    protected void showLongToast(int pResId) {
        showLongToast(getString(pResId));
    }

    protected void showToast(String pMsg) {
        if (toast == null) {
            initToast();
        }
        TextView view = (TextView) toast.getView();
        view.setText(pMsg);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.show();
    }

    protected void showToast(String msg, int duration){
        if (toast == null) {
            initToast();
        }
        TextView view = (TextView) toast.getView();
        view.setText(msg);
        toast.setDuration(duration);
        toast.show();
    }

    protected void showLongToast(String pMsg) {
        if (toast == null) {
            initToast();
        }
        TextView view = (TextView) toast.getView();
        view.setText(pMsg);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.show();
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.hold, R.anim.slide_right_exit);
    }
}
