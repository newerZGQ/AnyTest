package com.zgq.wokao.parser;

import com.google.common.base.Optional;
import com.zgq.wokao.entity.paper.NormalExamPaper;
import com.zgq.wokao.exception.ParseException;
import com.zgq.wokao.parser.formater.impl.MSDocFormater;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class ParserHelper {
    private static String TAG = ParserHelper.class.getSimpleName();

    private ParserHelper() {

    }

    public static ParserHelper getInstance() {
        return InstanceHolder.helper;
    }

    private static class InstanceHolder {
        private static ParserHelper helper = new ParserHelper();
    }

    private enum FileFormat {
        TXT, WORD, ERRORFORMAT;
    }

    private FileFormat checkFile(String filePath) throws FileNotFoundException, com.zgq.wokao.exception.ParseException {
        if (filePath == null || filePath.equals("")) {
            throw new com.zgq.wokao.exception.ParseException("路径不存在");
        }
        String lowerCase = filePath.toLowerCase();
        if (lowerCase.endsWith(".txt")) {
            return FileFormat.TXT;
        } else if (lowerCase.endsWith(".doc") || lowerCase.endsWith(".docx")) {
            return FileFormat.WORD;
        }

        if (!lowerCase.endsWith(".txt") && !lowerCase.endsWith(".doc") && !lowerCase.endsWith(".docx")) {
            throw new com.zgq.wokao.exception.ParseException("文件格式错误");
        }
        File file = new File(filePath);
        if (!file.exists()) {
            throw new com.zgq.wokao.exception.ParseException("文件不存在");
        }

        return FileFormat.ERRORFORMAT;
    }

    public Optional<NormalExamPaper> parse(String fileStr) throws FileNotFoundException,
            com.zgq.wokao.exception.ParseException {
        NormalExamPaper paper = null;
        switch (checkFile(fileStr)) {
            case TXT:
                File txtFile = new File(fileStr);
                paper =  parse(new FileInputStream(txtFile));
                break;
            case WORD:
                String wordStr = MSDocFormater.getInstance().getContent(fileStr);
                paper =  parse(new ByteArrayInputStream(wordStr.getBytes()));
                break;
            default:
                break;
        }
        return Optional.fromNullable(paper);
    }

    private NormalExamPaper parse(InputStream inputStream) throws ParseException {
        if (inputStream == null) {
            throw new com.zgq.wokao.exception.ParseException("请检查是否为空文件");
        }
        PaperParser paperParser = new PaperParser();
        return paperParser.parse(inputStream);
    }
}
