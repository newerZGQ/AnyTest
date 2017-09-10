package com.zgq.wokao.parser.formater;

import java.io.File;

/**
 * Created by zgq on 2017/2/9.
 */

public interface Formater {
    public void params(Object... args);

    public String getContent();
}
