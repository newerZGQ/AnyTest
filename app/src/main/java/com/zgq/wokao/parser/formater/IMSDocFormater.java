package com.zgq.wokao.parser.formater;

import java.io.File;

/**
 * Created by zgq on 2017/2/9.
 */

public interface IMSDocFormater {
    boolean fileAvailable(File file);

    String getContent(String filePath);
}
