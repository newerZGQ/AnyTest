package com.zgq.wokao;

import android.content.Context;
import android.support.v4.content.ContextCompat;

import javax.inject.Singleton;

@Singleton
public class RimContextCompat {

    private Context context;

    public RimContextCompat(){}

    public void config(Context context){
        this.context = context;
    }

    public int getColor(int colorId){
        return ContextCompat.getColor(context, colorId);
    }

    public String[] getStringArray(int arrayId){
        return context.getResources().getStringArray(arrayId);
    }
}
