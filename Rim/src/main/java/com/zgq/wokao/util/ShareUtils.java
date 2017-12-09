package com.zgq.wokao.util;

import android.app.Activity;
import android.net.Uri;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.share.Sharer;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;

public class ShareUtils {
    private static String TAG = ShareUtils.class.getSimpleName();

    public static void FacebookShare(Activity activity) {
        //抽取成员变量
        CallbackManager callbackManager = CallbackManager.Factory.create();
        ShareDialog shareDialog = new ShareDialog(activity);
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
}
