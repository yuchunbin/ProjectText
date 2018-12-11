package com.chunbin.app;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.DisplayMetrics;

import com.chunbin.app.fragment.ContentFragment;
import com.chunbin.app.fragment.LeftmenuFragment;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;

public class MainActivity extends SlidingFragmentActivity {


    private int screewidth;
    private int screehight;

    public static String MAIN_CONTENT_TAG = "main_content_tag";
    public static String LEFTMENU_TAG = "leftmenu_tag";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initSlidingMenu();
        //初始化fragment
        initFragment();
    }

    private void initSlidingMenu() {
        //设置主界面
        setContentView(R.layout.activity_main);

        //设置左侧菜单
        setBehindContentView(R.layout.activity_leftmenu);

        //设置右侧菜单
        SlidingMenu slidingMenu = getSlidingMenu();
        //slidingMenu.setSecondaryMenu(R.layout.activity_rightmenu);//设置右侧菜单
        //4.设置显示的模式：左侧菜单+主页，左侧菜单+主页面+右侧菜单；主页面+右侧菜单
        slidingMenu.setMode(SlidingMenu.LEFT);

        //5.设置滑动模式：滑动边缘，全屏滑动，不可以滑动
        slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        screehight = displayMetrics.heightPixels;
        screewidth = displayMetrics.widthPixels;
        //6.设置主页占据的宽度
        //slidingMenu.setBehindOffset(DensityUtil.dip2px(MainActivity.this,200));
        slidingMenu.setBehindOffset((int) (screewidth * 0.625));
    }

    private void initFragment() {
        //1.得到fragment
        FragmentManager fragmentManager = getSupportFragmentManager();
        //2.开启事务
        FragmentTransaction ft = fragmentManager.beginTransaction();

        //3.替换
        ft.replace(R.id.fl_main_content, new ContentFragment(), MAIN_CONTENT_TAG);
        ft.replace(R.id.fl_leftmenu, new LeftmenuFragment(), LEFTMENU_TAG);

        ft.commit();
    }

    //获得左侧菜单
    public LeftmenuFragment getLeftmenuFragment() {
        return (LeftmenuFragment) getSupportFragmentManager().findFragmentByTag(LEFTMENU_TAG);
    }

    //获得主页菜单
    public ContentFragment getContentFragment(){
        return (ContentFragment) getSupportFragmentManager().findFragmentByTag(MAIN_CONTENT_TAG);
    }
}
