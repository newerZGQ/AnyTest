package com.zgq.wokao.Util;

import android.content.Context;

import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXTextObject;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

/**
 * Created by zhangguoqiang on 2017/9/23.
 */

public class WXShare {
    private IWXAPI api;

    public void regToWx(Context context){
        String APP_ID = "wxec6f0ca0b7e79c8d";
        api = WXAPIFactory.createWXAPI(context, APP_ID, true);
        api.registerApp(APP_ID);
    }

    public void shareApp(){
        WXTextObject textObject = new WXTextObject();
        textObject.text = "this is a test text";
        WXMediaMessage message = new WXMediaMessage();
        message.mediaObject = textObject;
        message.description = "this is a test des";
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = "definer";
        req.message = message;
        api.sendReq(req);
    }
}
