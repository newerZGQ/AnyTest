package com.zgq.wokao.common.utils;

import java.util.ArrayList;
import java.util.List;

import io.realm.RealmList;
import io.realm.RealmObject;

public class ListUtil {
    public static <T> List<T> assem(List<T>... args) {
        List<T> results = new ArrayList<>();
        for (List tmp : args) {
            for (int i = 0; i < tmp.size(); i++) {
                results.add((T) tmp.get(i));
            }
        }
        return results;
    }

    public static <T> List<T> array2list(T[] array) {
        ArrayList<T> list = new ArrayList<>();
        for (T t : array) {
            list.add(t);
        }
        return list;
    }

    public static <T extends RealmObject> List<T> changeRealmListToList(RealmList<T> list) {
        List<T> results = new ArrayList<>();
        for (T t : list) {
            results.add(t);
        }
        return results;
    }
}
