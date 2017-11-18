package com.zgq.wokaofree.parser.formater;

import java.io.File;

/**
 * Created by zgq on 2017/2/9.
 */

public interface Formater {
    void params(Object... args);

    String getContent();
}
