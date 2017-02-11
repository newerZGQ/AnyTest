package com.zgq.wokao.Util;

import android.content.Context;

/**
 * Created by zgq on 2017/2/10.
 */

public class StringUtil {
    private static Context context;
    public static void init(Context _context){
        context = _context;
    }
    public static String getResString(int id){
        if (!checkParams()) return null;
        return context.getResources().getString(id);
    }
    public static boolean checkParams(){
        if (context == null){
            return false;
        }
        return true;
    }
}
