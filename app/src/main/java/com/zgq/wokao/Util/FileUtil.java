package com.zgq.wokao.Util;

import android.os.Environment;

import java.io.File;

/**
 * Created by zgq on 16-6-18.
 */
public class FileUtil {
    public static boolean isTxtFile(File file){
        if (!isLegal(file)) return false;
        String name = file.getName();
        if (name.contains(".txt") || name.contains(".TXT")) return true;
        return false;
    }
    public static boolean isXmlFile(File file){
        if (!isLegal(file)) return false;
        String name = file.getName();
        if (name.contains(".xml") || name.contains(".XML")) return true;
        return false;
    }
    private static boolean isLegal(File file){
        return (file == null)?false:true;
    }

    public static boolean SdcardMountedRight(){
        String status = Environment.getExternalStorageState();
        if (status.equals(Environment.MEDIA_MOUNTED)) {
            return true;
        } else {
            return false;
        }
    }
}
