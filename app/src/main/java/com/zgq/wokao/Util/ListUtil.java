package com.zgq.wokao.Util;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zgq on 2017/2/11.
 */

public class ListUtil {
    public static <T> List<T> assem(List<T>... args){
        List<T> results = new ArrayList<>();
        for (List tmp : args){
            for (int i = 0; i<tmp.size(); i++){
                results.add((T) tmp.get(i));
            }
        }
        return results;
    }

    public static <T> List<T> array2list(T[] array){
        ArrayList<T> list = new ArrayList<>();
        for (T t: array){
            list.add(t);
        }
        return list;
    }
}
