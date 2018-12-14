package com.chunbin.app.controller.video;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.view.View;
import android.view.WindowManager;
import android.widget.SeekBar;

import com.chunbin.app.activity.PlayVideoActivity;

public class LightnessController {
    public static void turnDown(Context context, float yDilta, int heightPixels) {
        //获取系统当前的亮度
        try {
            int current_brightness = Settings.System.getInt(context.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS);

            //计算亮度的改变值
            int brightnessDelta = (int) (yDilta / heightPixels * 255);
            //亮度最下,不能小于25
            int changedBrightness = Math.max(25, current_brightness - brightnessDelta);
            //修改屏幕的亮度
            WindowManager.LayoutParams attributes = ((Activity) context).getWindow().getAttributes();//窗体的属性集合

            attributes.screenBrightness = changedBrightness*1.0f/255;
            ((Activity) context).getWindow().setAttributes(attributes);
            //当前的亮度设置给系统
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (!Settings.System.canWrite(context)) {
                    Intent intent = new Intent(android.provider.Settings.ACTION_MANAGE_WRITE_SETTINGS);
                    intent.setData(Uri.parse("package:" + context.getPackageName()));
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                } else {
                    //有了权限，具体的动作
                    Settings.System.putInt(context.getContentResolver(),
                            Settings.System.SCREEN_BRIGHTNESS, changedBrightness);
                }
            }
            //获取SeekBar
            SeekBar bright_display = ((PlayVideoActivity) context).getBright_display();
            if (bright_display.getMax()!=255){
                bright_display.setMax(255);
            }
            bright_display.setVisibility(View.VISIBLE);
            bright_display.setProgress(changedBrightness);
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
        }

    }

    public static void turnUp(Context context, float yDilta, int heightPixels) {
        //获取系统当前的亮度
        try {
            int current_brightness = Settings.System.getInt(context.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS);

            //计算亮度的改变值
            int brightnessDelta = (int) (yDilta / heightPixels * 255);
            //亮度最下,不能小于25
            int changedBrightness = Math.min(255, current_brightness - brightnessDelta);
            //修改屏幕的亮度
            WindowManager.LayoutParams attributes = ((Activity) context).getWindow().getAttributes();//窗体的属性集合

            attributes.screenBrightness = changedBrightness*1.0f/255;
            ((Activity) context).getWindow().setAttributes(attributes);
            //当前的亮度设置给系统
            Settings.System.putInt(context.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS,changedBrightness);

            //获取SeekBar
            SeekBar bright_display = ((PlayVideoActivity) context).getBright_display();
            if (bright_display.getMax()!=255){
                bright_display.setMax(255);
            }
            bright_display.setVisibility(View.VISIBLE);
            bright_display.setProgress(changedBrightness);




        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
        }
    }
}
