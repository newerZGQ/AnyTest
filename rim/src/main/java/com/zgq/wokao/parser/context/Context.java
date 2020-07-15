package com.zgq.wokao.parser.context;

import com.zgq.wokao.parser.context.item.ParserItem;

public interface Context<T extends ParserItem> {
    void init(int length);

    void inContext(T t);

    void deContext();

    T tail(int distance);
}
