package com.zgq.wokao.parser.formater.impl;

import com.tencent.mm.opensdk.utils.Log;
import com.zgq.wokao.parser.formater.BaseFormater;
import com.zgq.wokao.parser.formater.IMSDocFormater;

import org.textmining.text.extraction.WordExtractor;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * Created by zgq on 2017/2/9.
 */

public class MSDocFormater extends BaseFormater implements IMSDocFormater {

    public static final String TAG = "MSDocFormater";
    private String doc;

    private MSDocFormater() {
    }

    private static class InstanceHolder {
        private static MSDocFormater msDocFormater = new MSDocFormater();
    }

    public static MSDocFormater getInstance() {
        return InstanceHolder.msDocFormater;
    }


    @Override
    public void params(Object... args) {
        if (args[0] instanceof String) {
            doc = args[0].toString();
        }
    }

    @Override
    public String getContent() {
        File docFile = new File(doc);
        if (!fileAvailable(docFile)) {
            return null;
        }
        return getDocContent(docFile);
    }

    @Override
    public boolean fileAvailable(File file) {
        if (file.getName().toLowerCase().endsWith(".doc")) {
            return true;
        }
        return false;
    }

    @Override
    public String getContent(String filePath) {
        doc = filePath;
        return getContent();
    }

    private String getDocContent(File file) {
        // 创建输入流读取doc文件
        FileInputStream in = null;
        try {
            in = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        WordExtractor extractor = null;
        String text = null;
        // 创建WordExtractor
        extractor = new WordExtractor();
        // 对doc文件进行提取
        try {
            text = extractor.extractText(in);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return text;
    }
}
