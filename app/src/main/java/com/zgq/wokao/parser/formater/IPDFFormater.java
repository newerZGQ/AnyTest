package com.zgq.wokao.parser.formater;

import java.io.File;

/**
 * Created by zgq on 2017/2/11.
 */

public interface IPDFFormater {
    public boolean fileAvailable(File file);
    public String getContent(String filePath);
}
