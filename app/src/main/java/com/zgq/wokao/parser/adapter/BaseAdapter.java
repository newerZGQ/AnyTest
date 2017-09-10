package com.zgq.wokao.parser.adapter;

/**
 * Created by zgq on 2017/2/19.
 */

public abstract class BaseAdapter implements IAdapter {

    public boolean isStartWithNumber(String s) {
        char[] chars = s.trim().toCharArray();
        int numberCount = 0;
        int index = 0;
        for (char tmp : chars) {
            if (tmp >= 48 && tmp <= 57) {
                numberCount++;
                index++;
            } else {
                break;
            }
        }
        if (numberCount == 0) {
            return false;
        }
        return true;
    }

    protected String trimNum(String s) {
        s = s.trim();
        char[] chars = s.toCharArray();
        if (chars.length == 0) {
            return s;
        }
        if (chars[0] >= 48 && chars[0] <= 57) {
            int index = 0;
            for (char tmp : chars) {
                index++;
                if ((int) tmp >= 48 && (int) tmp <= 57) {
                    continue;
                } else {
                    break;
                }
            }
            s = s.substring(index - 1).trim();
            if (s.startsWith(".")) {
                s = s.substring(1);
            }
            return s.trim();
        }

        if (s.startsWith("(") || s.startsWith("（")) {
            String newS = s.substring(1);
            char[] chars2 = newS.toCharArray();
            int numberCount = 0;
            int index = 0;
            for (char tmp : chars2) {
                index++;
                if ((int) tmp >= 48 && (int) tmp <= 57) {
                    numberCount++;
                    continue;
                } else {
                    break;
                }
            }
            if (numberCount == 0) {
                return s.trim();
            }
            String newS2 = newS.substring(index);
            if ((newS2.startsWith(")") || newS2.startsWith("）")) && numberCount > 0) {
                return newS2.substring(1).trim();
            } else if (numberCount > 0) {
                return newS2.trim();
            }
            return s.trim();
        }
        return s;
    }

    protected boolean isQstNumber(String s) {
        if (isStartWithNumber(s)) {
            return true;
        }
        if (s.startsWith("(") ||
                s.startsWith("（")) {
            s = s.substring(1);
            if (isStartWithNumber(s)) {
                return true;
            }
        }
        return false;
    }

    protected boolean startWithWord(String s) {
        char[] chars = s.trim().toCharArray();
        int letterCount = 0;
        for (char tmp : chars) {
            if ((tmp >= 65 && tmp <= 90) || (tmp >= 97 && tmp <= 122)) {
                letterCount++;
            } else {
                break;
            }
        }
        if (letterCount > 1) {
            return true;
        }
        return false;
    }
}
