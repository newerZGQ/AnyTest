package com.zgq.wokao.parser.formater.impl;

import com.zgq.wokao.parser.formater.BaseFormater;
import com.zgq.wokao.parser.formater.IMSDocFormater;

import org.textmining.text.extraction.WordExtractor;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * Created by zgq on 2017/2/9.
 */

public class MSDocFormater extends BaseFormater implements IMSDocFormater{

    private String doc;


    @Override
    public void params(Object... args) {
        if (args[0] instanceof String){
            doc = args[0].toString();
            System.out.println("----->>"+doc);
        }
    }

    @Override
    public String getContent() {
        File docFile = new File(doc);
        if (!fileAvailable(docFile)){
            return null;
        }
        return getDocContent(docFile);
    }

    @Override
    public boolean fileAvailable(File file) {
        if (file.getName().endsWith(".doc") || file.getName().endsWith(".docx")){
            return true;
        }
        return false;
    }

    @Override
    public String getContent(String filePath) {
        doc = filePath;
        return getContent();
    }

    private String getDocContent(File file){
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
