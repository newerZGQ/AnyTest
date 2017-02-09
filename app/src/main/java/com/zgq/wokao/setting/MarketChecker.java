package com.zgq.wokao.setting;

import android.content.Context;
import android.util.Log;

import com.zgq.wokao.data.sp.SPConstant;
import com.zgq.wokao.data.sp.SharedPreferencesHelper;

/**
 * Created by zgq on 2017/2/9.
 */

public class MarketChecker extends BaseSetting {

    private static final int BOUNDRARY = 2;

    /**
     * 检查是否需要打开应用商店打分
     * @param context
     * @return
     */
    public static boolean checkMarket(Context context){
        checkKey(context);
        boolean gradeState = (Boolean) SharedPreferencesHelper.get(SPConstant.GRADE_STATE,false);
        if (gradeState == true) return false;
        int workingCount = WoringCounter.getCount(context);
        if (workingCount >= BOUNDRARY){
            return true;
        }
        return false;
    }

    /**
     * 设置是否已经打过分的状态
     * @param context
     * @param state
     */
    public static void setState(Context context, boolean state){
        checkKey(context);
        SharedPreferencesHelper.put(SPConstant.GRADE_STATE,state);
    }

    /**
     * 获得是否打分的状态
     * @param context
     * @return
     */
    private static boolean getState(Context context){
        checkKey(context);
        Object object = SharedPreferencesHelper.get(SPConstant.GRADE_STATE,false);
        boolean state = false;
        if (object instanceof Boolean){
            state = (Boolean)object;
        }
        return state;
    }

    /**
     * 检查key是否存在
     * @param context
     */
    private static void checkKey(Context context){
        if (!SharedPreferencesHelper.contains(SPConstant.GRADE_STATE)){
            SharedPreferencesHelper.put(SPConstant.GRADE_STATE,false);
        }
    }
    public static class WoringCounter {
        /**
         * 打开app次数加一
         * @param context
         */
        public static void increase(Context context){
            checkKey(context);
            Object object = SharedPreferencesHelper.get(SPConstant.WORKING_COUNT,1);
            if (object instanceof Integer){
                int count = (Integer)object;
                count++;
                SharedPreferencesHelper.put(SPConstant.WORKING_COUNT,count);
            }
        }

        /**
         * 获得打开app的次数
         * @param context
         * @return
         */
        static int getCount(Context context){
            checkKey(context);
            Object object = SharedPreferencesHelper.get(SPConstant.WORKING_COUNT,1);
            int count = 0;
            if (object instanceof Integer){
                count = (Integer)object;
            }
            return count;
        }

        /**
         * 检查key是否存在
         * @param context
         */
        private static void checkKey(Context context){
            if (!SharedPreferencesHelper.contains(SPConstant.WORKING_COUNT)){
                SharedPreferencesHelper.put(SPConstant.WORKING_COUNT,1);
            }
        }
    }
}
