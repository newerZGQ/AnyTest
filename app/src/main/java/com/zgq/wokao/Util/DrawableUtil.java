package com.zgq.wokao.Util;

import android.util.Log;

import com.zgq.wokao.R;

import java.util.ArrayList;

/**
 * Created by zgq on 16-7-10.
 */
public class DrawableUtil {
    private static int colorGreen = R.drawable.circle_background_upside_green;
    private static int colorOrange = R.drawable.circle_background_upside_orange;
    private static int colorYellow = R.drawable.circle_background_upside_yellow;
    private static int colorBlue = R.drawable.circle_background_upside_blue;
    private static int colorPrivious = 0;
    static ArrayList<Integer> colorSet = new ArrayList<>();
    static {
        colorSet.add(colorGreen);
        colorSet.add(colorBlue);
        colorSet.add(colorOrange);
        colorSet.add(colorYellow);
    }

    public static int getDrawable(){
        int position = (int)(Math.random()*4);
        Log.d("getColorPo",""+position);
        int colorCurrent = colorSet.get(position);
        Log.d("getColor",""+colorCurrent);
        if (colorPrivious == colorCurrent){
            return getDrawable();
        }else {
            colorPrivious = colorCurrent;
            return colorCurrent;
        }
    }
}
