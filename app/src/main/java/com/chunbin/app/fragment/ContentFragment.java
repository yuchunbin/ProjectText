package com.chunbin.app.fragment;

import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.RadioGroup;

import com.chunbin.app.MainActivity;
import com.chunbin.app.R;
import com.chunbin.app.adapter.ContentFragmentAdapter;
import com.chunbin.app.base.BaseFragment;
import com.chunbin.app.base.BasePager;
import com.chunbin.app.pager.DoKiPager;
import com.chunbin.app.pager.HomePager;
import com.chunbin.app.pager.HotspotPager;
import com.chunbin.app.pager.SettingPager;
import com.chunbin.app.pager.VIPPager;
import com.chunbin.app.view.NoScrollViewPager;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;

public class ContentFragment extends BaseFragment {

    private static final String TAG = "ContentFragment" ;
    @ViewInject(R.id.viewpager)
    private NoScrollViewPager viewPager;

    @ViewInject(R.id.rg_main)
    private RadioGroup rg_main;

    /*
     *装5个页面的集合
     */
    private ArrayList<BasePager> basePagers;

    @Override
    public View initView() {
        View view = View.inflate(context,R.layout.fragment_content,null);
        x.view().inject(ContentFragment.this,view);
        return view;
    }

    @Override
    public void initData() {
        super.initData();
        Log.e(TAG,"正文Fragment数据被初始化了");
        basePagers = new ArrayList<BasePager>();
        basePagers.add(new HomePager(context));
        basePagers.add(new HotspotPager(context));
        basePagers.add(new VIPPager(context));
        basePagers.add(new DoKiPager(context));
        basePagers.add(new SettingPager(context));


        Log.e(TAG,"viewPager = " + viewPager);
        Log.e(TAG,"basePagers = " + basePagers);
        viewPager.setAdapter(new ContentFragmentAdapter(basePagers));

        rg_main.setOnCheckedChangeListener(new MyOnCheckedChangeListener());

        viewPager.addOnPageChangeListener(new MyOnPageChangeListener());
        rg_main.check(R.id.rb_home);
        basePagers.get(0).initData();
        isEnableSlidingMenu(SlidingMenu.TOUCHMODE_NONE);
    }


    private class MyOnCheckedChangeListener implements RadioGroup.OnCheckedChangeListener {
        @Override
        public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {

            switch (checkedId){
                case R.id.rb_home:
                    viewPager.setCurrentItem(0,false);
                    isEnableSlidingMenu(SlidingMenu.TOUCHMODE_NONE);
                    break;
                case R.id.rb_newscenter:
                    viewPager.setCurrentItem(1,false);
                    isEnableSlidingMenu(SlidingMenu.TOUCHMODE_FULLSCREEN);
                    break;
                case R.id.rb_smartservice:
                    viewPager.setCurrentItem(2,false);
                    isEnableSlidingMenu(SlidingMenu.TOUCHMODE_NONE);
                    break;
                case R.id.rb_govaffair:
                    viewPager.setCurrentItem(3,false);
                    isEnableSlidingMenu(SlidingMenu.TOUCHMODE_NONE);
                    break;
                case R.id.rb_setting:
                    viewPager.setCurrentItem(4,false);
                    isEnableSlidingMenu(SlidingMenu.TOUCHMODE_NONE);
                    break;
            }
        }
    }

    private class MyOnPageChangeListener implements ViewPager.OnPageChangeListener {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        /**
         * 当某个页面被选中的时候回调这个方法
         * @param position 被选中页面的位置
         */
        @Override
        public void onPageSelected(int position) {
//            BasePager basePager = basePagers.get(position);
            //调用被选中的页面的initData方法
            basePagers.get(position).initData();
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }

    /**
     根据传入的参数设置是否让SlidingMenu可以滑动
     */
    private void isEnableSlidingMenu(int touchmodeNone) {
        MainActivity mainActivity = (MainActivity) context;
        mainActivity.getSlidingMenu().setTouchModeAbove(touchmodeNone);
    }
}
