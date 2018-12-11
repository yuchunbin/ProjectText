package com.chunbin.app.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.widget.RelativeLayout;

import com.chunbin.app.MainActivity;
import com.chunbin.app.R;
import com.chunbin.app.utils.CacheUtils;

public class SplashActivity extends Activity {

    public static String START_MAIN = "start_main";
    RelativeLayout rl_splahs_root;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        rl_splahs_root = findViewById(R.id.rl_splahs_root);
        //渐变动画，缩放动画，旋转动画
        AlphaAnimation alphaAnimation = new AlphaAnimation(0,1);
        alphaAnimation.setFillAfter(true);
        ScaleAnimation scaleAnimation = new ScaleAnimation(0,1,0,1,ScaleAnimation.RELATIVE_TO_SELF,0.5f,ScaleAnimation.RELATIVE_TO_SELF,0.5f);
        scaleAnimation.setFillAfter(true);
        RotateAnimation rotateAnimation = new RotateAnimation(0,360,RotateAnimation.RELATIVE_TO_SELF,0.5f,RotateAnimation.RELATIVE_TO_SELF,0.5f);
        rotateAnimation.setFillAfter(true);
        AnimationSet animationSet = new AnimationSet(false);
        animationSet.addAnimation(alphaAnimation);
        animationSet.addAnimation(scaleAnimation);
        animationSet.addAnimation(rotateAnimation);
        animationSet.setDuration(2000);
        rl_splahs_root.startAnimation(animationSet);
        animationSet.setAnimationListener(new AnimationListener());

    }

    private class AnimationListener implements Animation.AnimationListener {
        /**
         * 当动画开始播放的时候回调这个方法
         * @param animation
         */
        @Override
        public void onAnimationStart(Animation animation) {

        }
        /**
         * 当动画播放结束的时候回调这个方法
         * @param animation
         */
        @Override
        public void onAnimationEnd(Animation animation) {
            boolean isStartMain = CacheUtils.getIsStartMain(SplashActivity.this,START_MAIN);
            Intent intent;
            if (isStartMain) {
                intent = new Intent(SplashActivity.this,MainActivity.class);
            }else{
                intent = new Intent(SplashActivity.this,GuideActivity.class);
            }
            startActivity(intent);
            finish();
        }
        /**
         * 当动画重复播放的时候回调这个方法
         * @param animation
         */
        @Override
        public void onAnimationRepeat(Animation animation) {

        }
    }
}
