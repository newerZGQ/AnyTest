package com.zgq.wokao.parser.formater;

import java.io.File;

public interface IMSDocFormater {
    boolean fileAvailable(File file);

    String getContent(String filePath);
}
