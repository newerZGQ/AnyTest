package com.zgq.wokao.parser;

/**
 * Created by zgq on 2017/2/9.
 */

public abstract class BaseParser implements IParser {
    @Override
    public boolean isFillInTitle(String source) {
        if (source.contains("填空")){
            return true;
        }

        if (source.matches("[a-zA-z]+]") &&
                source.toLowerCase().contains("spot dictation") ||
                source.toLowerCase().contains("fill the blanks") ||
                source.toLowerCase().contains("fill in the blank")){
            return true;
        }

        return false;
    }

    @Override
    public boolean isTFTitle(String source) {
        if (source.contains("判断")){
            return true;
        }

        if (source.matches("[a-zA-z]+]") &&
                source.toLowerCase().contains("true/false") ||
                source.toLowerCase().contains("true-false")){
            return true;
        }

        return false;
    }

    @Override
    public boolean isSglChoTitle(String source) {
        if (source.contains("单") && source.contains("选")){
            return true;
        }

        if (source.matches("[a-zA-z]+]") &&
                source.contains("multiple choice(single answer)")){
            return true;
        }

        return false;
    }

    @Override
    public boolean isMutiChoTitle(String source) {
        if (source.contains("多") && source.contains("选")){
            return true;
        }

        if (source.matches("[a-zA-z]+]") &&
                source.contains("multiple choice(mutiple answer)")){
            return true;
        }

        return false;
    }

    @Override
    public boolean isDiscusTitle(String source) {
        if (source.contains("简答") || source.contains("问答")){
            return true;
        }

        if (source.matches("[a-zA-z]+]") &&
                source.contains("short answer")){
            return true;
        }

        return false;
    }

    @Override
    public boolean isAuthorTitle(String source) {
        if (source.contains("作者")){
            return true;
        }

        if (source.matches("[a-zA-Z]+") && source.toLowerCase().contains("author")){
            return true;
        }

        return false;
    }
}
