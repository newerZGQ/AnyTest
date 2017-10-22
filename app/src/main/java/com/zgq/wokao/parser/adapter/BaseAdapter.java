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
            if (tmp >= '0' && tmp <= '9') {
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
        if (chars[0] >= '0' && chars[0] <= '9') {
            int index = 0;
            for (char tmp : chars) {
                index++;
                if ((int) tmp >= '0' && (int) tmp <= '9') {
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
                if ((int) tmp >= '0' && (int) tmp <= '9') {
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
            if ((tmp >= 'A' && tmp <= 'Z') || (tmp >= 'a' && tmp <= 'z')) {
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

    protected boolean checkIsAnswerTitle(String source){
        if (source.startsWith("答案")){
            return true;
        }

        if (source.matches("[a-zA-z]+") && source.length() >= 6 &&
                source.substring(0,6).toLowerCase().startsWith("answer")){
            return true;
        }

        return false;
    }
}
