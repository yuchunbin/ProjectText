package com.chunbin.app.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class CacheUtils {
    public static boolean getIsStartMain(Context context,String key){
        SharedPreferences sp = context.getSharedPreferences("chunbin",Context.MODE_PRIVATE);
        return sp.getBoolean(key,false);
    }

    public static void setIsStartMain(Context context , String key,boolean value){
        SharedPreferences sp = context.getSharedPreferences("chunbin",Context.MODE_PRIVATE);
        sp.edit().putBoolean(key,value).commit();
    }
}
