package com.zgq.wokao.parser.context;

import com.zgq.wokao.parser.context.item.ParserItem;

/**
 * Created by zgq on 2017/2/20.
 */

public interface Context<T extends ParserItem> {
    void init(int length);

    void inContext(T t);

    void deContext();

    T tail(int distance);
}
