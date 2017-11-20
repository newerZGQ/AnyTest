package com.zgq.wokao.parser.formater;

import java.io.File;

/**
 * Created by zgq on 2017/2/11.
 */

public interface IPDFFormater {
    boolean fileAvailable(File file);

    String getContent(String filePath);
}
