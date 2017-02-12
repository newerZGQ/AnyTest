package com.zgq.wokao.parser.formater.impl;

import com.zgq.wokao.parser.formater.BaseFormater;
import com.zgq.wokao.parser.formater.IPDFFormater;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Created by zgq on 2017/2/11.
 */

public class PDFFormater extends BaseFormater implements IPDFFormater{

    public static final String TAG = "PDFFormater";
    private String pdf;

    private PDFFormater(){}

    private static class InstanceHolder{
        private static PDFFormater formater = new PDFFormater();
    }

    public static PDFFormater getInstance(){
        return InstanceHolder.formater;
    }

    @Override
    public void params(Object... args) {
        if (args[0] instanceof String){
            pdf = args[0].toString();
        }
    }

    @Override
    public String getContent() {
        File pdfFile = new File(pdf);
        if (!fileAvailable(pdfFile)){
            return null;
        }
        return getPDFContent(pdfFile);
    }

    @Override
    public boolean fileAvailable(File file) {
        if (file.getName().endsWith(".pdf") || file.getName().endsWith(".PDF")){
            return true;
        }
        return false;
    }

    @Override
    public String getContent(String filePath) {
        pdf = filePath;
        return getContent();
    }

    private String getPDFContent(File file){
        PDDocument document =  null;
        String content = null;

        try {
            document = PDDocument.load(file);
            // 获取页码
            int pages = document.getNumberOfPages();
            // 读文本内容
            PDFTextStripper stripper=new PDFTextStripper();
            // 设置按顺序输出
            stripper.setSortByPosition(true);
            stripper.setStartPage(1);
            stripper.setEndPage(pages);
            content = stripper.getText(document);
            System.out.println(content);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return content;
    }
}
