package com.zgq.wokaofree.parser;

import com.orhanobut.logger.Logger;

/**
 * Created by zgq on 2017/2/9.
 */

public abstract class BaseParser implements IParser {
    @Override
    public boolean isFillInTitle(String source) {
        if (source.contains("填空")){
            Logger.i("this line is fillin topic:" + source);
            return true;
        }

        if ((source.toLowerCase().contains("spot dictation") ||
                source.toLowerCase().contains("fill the blanks") ||
                source.toLowerCase().contains("fill in the blank"))){
            Logger.i("this line is fillin topic:" + source);
            return true;
        }
        return false;
    }

    @Override
    public boolean isTFTitle(String source) {
        if (source.contains("判断")){
            Logger.i("this line is tf topic:" + source);
            return true;
        }

        if ((source.toLowerCase().contains("true/false") ||
                source.toLowerCase().contains("true-false"))){
            Logger.i("this line is tf topic:" + source);
            return true;
        }
        return false;
    }

    @Override
    public boolean isSglChoTitle(String source) {
        if (source.contains("单") && source.contains("选")){
            Logger.i("this line is sglcho topic:" + source);
            return true;
        }

        if (source.toLowerCase().contains("multiple choice(single answer)")){
            Logger.i("this line is sglcho topic:" + source);
            return true;
        }
        return false;
    }

    @Override
    public boolean isMutiChoTitle(String source) {
        if (source.contains("多") && source.contains("选")){
            Logger.i("this line is mutilcho topic:" + source);
            return true;
        }

        if (source.toLowerCase().contains("multiple choice(mutiple answer)")){
            Logger.i("this line is mutilcho topic:" + source);
            return true;
        }
        return false;
    }

    @Override
    public boolean isDiscusTitle(String source) {
        if (source.contains("简答") || source.contains("问答")){
            Logger.i("this line is short answer topic:" + source);
            return true;
        }

        if (source.toLowerCase().contains("short answer")){
            Logger.i("this line is short answer topic:" + source);
            return true;
        }
        return false;
    }

    @Override
    public boolean isAuthorTitle(String source) {
        if (source.contains("作者")){
            Logger.i("this line is author topic:" + source);
            return true;
        }

        if (source.toLowerCase().contains("author")){
            Logger.i("this line is author topic:" + source);
            return true;
        }
        return false;
    }
}
