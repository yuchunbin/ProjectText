package com.chunbin.app.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Environment;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class CacheUtils {
    private static String TAG="CacheUtils";

    public static boolean getIsStartMain(Context context, String key){
        SharedPreferences sp = context.getSharedPreferences("chunbin",Context.MODE_PRIVATE);
        return sp.getBoolean(key,false);
    }

    public static void setIsStartMain(Context context , String key,boolean value){
        SharedPreferences sp = context.getSharedPreferences("chunbin",Context.MODE_PRIVATE);
        sp.edit().putBoolean(key,value).commit();
    }

    /**
     * 获取保存的文本
     * @param context 上下文
     * @param key 键值
     * @return
     */
    public static String getString(Context context, String key) {
        String result = "";
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            try {
                String fileName = MD5Encoder.encode(key);
                File file = new File(Environment.getExternalStorageDirectory()+"/chunbin/files",fileName);

                if (!file.exists()) {
                    FileInputStream fis = new FileInputStream(file);
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    byte[] bytes = new byte[1024];
                    int lenth;
                    while ((lenth=fis.read(bytes))!=-1){
                        baos.write(bytes,0,lenth);
                    }
                    fis.close();
                    baos.close();
                    result = baos.toString();
                }
            } catch (Exception e) {
                e.printStackTrace();
                Log.e(TAG,"文本获取失败");
            }
        }else{
            SharedPreferences sp = context.getSharedPreferences("chunbin",Context.MODE_PRIVATE);
            result = sp.getString(key,"");
        }
        return result;
    }

    /**
     * 保存文本文件
     * @param context   上下文
     * @param key       键
     * @param value     值
     */
    public static void putString(Context context, String key, String value) {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            try {
                String fileName = MD5Encoder.encode(key);
                File file = new File(Environment.getExternalStorageDirectory()+"/chunbin/files",fileName);
                File parentFile = file.getParentFile();
                if (!parentFile.exists()) {
                    parentFile.mkdirs();
                }

                if (!file.exists()) {
                    file.createNewFile();
                }

                FileOutputStream fos = new FileOutputStream(file);
                fos.write(value.getBytes());
                fos.close();
            } catch (Exception e) {
                e.printStackTrace();
                Log.e(TAG,"文本数据缓存失败");
            }
        }else{
            SharedPreferences sharedPreferences = context.getSharedPreferences("chunbin",Context.MODE_PRIVATE);
            sharedPreferences.edit().putString(key,value).commit();
        }
    }
}
