package com.zgq.wokao.Util;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.view.View;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.share.Sharer;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;
import com.tencent.mm.opensdk.utils.Log;

/**
 * Created by zhangguoqiang on 2017/11/4.
 */

public class FBShare {
    private static String TAG = FBShare.class.getSimpleName();
    private static CallbackManager callbackManager;
    private static ShareDialog shareDialog;
    public static void FacebookShare(Activity activity) {
        //抽取成员变量
        callbackManager = CallbackManager.Factory.create();
        shareDialog = new ShareDialog(activity);
        // this part is optional
        shareDialog.registerCallback(callbackManager, new FacebookCallback<Sharer.Result>() {

            @Override
            public void onSuccess(Sharer.Result result) {
            }

            @Override
            public void onCancel() {
            }

            @Override
            public void onError(FacebookException error) {
            }
        });
        if (ShareDialog.canShow(ShareLinkContent.class)) {
            ShareLinkContent linkContent = new ShareLinkContent.Builder()
                    .setContentUrl(Uri.parse("http://zgq2015.coding.me/PaperFactory_web/"))
                    .build();
            shareDialog.show(linkContent);
        }
    }

    public void shareToFacebook() {

    }
}
