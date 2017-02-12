package com.zgq.wokao.parser.formater.impl;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.googlecode.tesseract.android.TessBaseAPI;
import com.zgq.wokao.Util.FileUtil;
import com.zgq.wokao.parser.formater.BaseFormater;
import com.zgq.wokao.parser.formater.IImageFormater;

/**
 * Created by zhangguoqiang on 2017/2/12.
 */

public class ImageFormater extends BaseFormater implements IImageFormater {

    private static String TAG = "ImageFormater";
    private String imagePath;
    private TessBaseAPI tessBaseApi;
    private String lang = "eng";
    private static final String DATA_PATH = FileUtil.getOrInitAppStoragePath();
    private ImageFormater(){}
    private static class InstanceHolder{
        private static ImageFormater instance = new ImageFormater();
    }
    public static ImageFormater getInstance(){
        return InstanceHolder.instance;
    }
    @Override
    public void params(Object... args) {
        if (args.length<2){
            return;
        }
        imagePath = args[0].toString();
        lang = args[1].toString();
    }

    @Override
    public String getContent() {
        Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
        extractText(bitmap);
        return null;
    }

    private String extractText(Bitmap bitmap) {
        try {
            tessBaseApi = new TessBaseAPI();
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
            if (tessBaseApi == null) {
                Log.e(TAG, "TessBaseAPI is null. TessFactory not returning tess object.");
            }
        }

        tessBaseApi.init(DATA_PATH, lang);

//       //EXTRA SETTINGS
//        //For example if we only want to detect numbers
//        tessBaseApi.setVariable(TessBaseAPI.VAR_CHAR_WHITELIST, "1234567890");
//
//        //blackList Example
//        tessBaseApi.setVariable(TessBaseAPI.VAR_CHAR_BLACKLIST, "!@#$%^&*()_+=-qwertyuiop[]}{POIU" +
//                "YTRWQasdASDfghFGHjklJKLl;L:'\"\\|~`xcvXCVbnmBNM,./<>?");
        Log.d(TAG, "Training file loaded");
        tessBaseApi.setImage(bitmap);
        String extractedText = "empty result";
        try {
            extractedText = tessBaseApi.getUTF8Text();
            Log.d("TAG",extractedText);
        } catch (Exception e) {
            Log.e(TAG, "Error in recognizing text.");
        }
        tessBaseApi.end();
        return extractedText;
    }
}