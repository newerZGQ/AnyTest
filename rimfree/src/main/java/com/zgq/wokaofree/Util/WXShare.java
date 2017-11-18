package com.zgq.wokaofree.Util;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXTextObject;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.zgq.wokaofree.R;

/**
 * Created by zhangguoqiang on 2017/9/23.
 */

public class WXShare {
    private static IWXAPI api;

    public static void regToWx(Context context){
        String APP_ID = "wxec6f0ca0b7e79c8d";
        api = WXAPIFactory.createWXAPI(context, APP_ID, true);
        api.registerApp(APP_ID);
    }

    public static void shareApp(Context context){
        WXWebpageObject webObject = new WXWebpageObject();
        webObject.webpageUrl = "http://zgq2015.coding.me/PaperFactory_web/";
        WXMediaMessage message = new WXMediaMessage();
        message.mediaObject = webObject;
        message.description = "把文档装进口袋";
        message.title = "把文档装进口袋--试卷工厂";

        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(),R.mipmap.ic_launcher);
        message.setThumbImage(bitmap);
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = "wokaofree_app";
        req.message = message;
        req.scene = SendMessageToWX.Req.WXSceneTimeline;
        api.sendReq(req);
    }

    public static void handleIntent(Intent intent, IWXAPIEventHandler handler){
        api.handleIntent(intent, handler);
    }
}
