package com.zgq.wokao.parser.context;

import com.zgq.wokao.parser.context.item.ParserItem;

/**
 * Created by zgq on 2017/2/20.
 */

public interface Context <T extends ParserItem>{
    public void init(int length);
    public void inContext(T t);
    public void deContext();
}
