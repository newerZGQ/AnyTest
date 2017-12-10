package com.zgq.wokao.parser;

public interface IParser {
    boolean isFillInTitle(String source);
    boolean isTFTitle(String source);
    boolean isSglChoTitle(String source);
    boolean isMutiChoTitle(String source);
    boolean isDiscusTitle(String source);
    boolean isAuthorTitle(String source);
}