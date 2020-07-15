package com.zgq.wokao.util;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.Environment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class FileUtil {
    public static boolean isTxtFile(File file) {
        if (!isLegal(file)) return false;
        String name = file.getName();
        if (name.contains(".txt") || name.contains(".TXT")) return true;
        return false;
    }

    public static boolean isXmlFile(File file) {
        if (!isLegal(file)) return false;
        String name = file.getName();
        if (name.contains(".xml") || name.contains(".XML")) return true;
        return false;
    }

    private static boolean isLegal(File file) {
        return (file == null) ? false : true;
    }

    public static boolean SdcardMountedRight() {
        String status = Environment.getExternalStorageState();
        if (status.equals(Environment.MEDIA_MOUNTED)) {
            return true;
        } else {
            return false;
        }
    }

    private static String getSdRootPath() {
        String root = Environment.getExternalStorageDirectory().toString();
        return root;
    }

    public static String getOrInitAppStoragePath() {
        if (!SdcardMountedRight()) {
            return null;
        }
        String root = getSdRootPath();
        String appPath = root + "/wokao";
        File file = new File(appPath);
        if (!file.exists()) {
            file.mkdir();
        }
        return appPath;
    }

    public static void transAssets2SD(Context context,String assetsFilePath, String sdFilePath) {
        AssetManager am = context.getAssets();
        try {
            InputStream inputStream = am.open(assetsFilePath);
            createFileFromInputStream(sdFilePath, inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static File createFileFromInputStream(String filePath, InputStream inputStream) {

        try {
            File f = new File(filePath);
            OutputStream outputStream = new FileOutputStream(f);
            byte buffer[] = new byte[1024];
            int length = 0;

            while ((length = inputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, length);
            }
            outputStream.close();
            inputStream.close();
            return f;
        } catch (IOException e) {
            //Logging exception
        }
        return null;
    }

}
