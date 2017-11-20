package com.zgq.wokao.parser;

/**
 * Created by zgq on 2017/2/9.
 */

public interface IParser {
    boolean isFillInTitle(String source);
    boolean isTFTitle(String source);
    boolean isSglChoTitle(String source);
    boolean isMutiChoTitle(String source);
    boolean isDiscusTitle(String source);
    boolean isAuthorTitle(String source);
}