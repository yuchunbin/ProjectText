package com.chunbin.app.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.chunbin.app.MainActivity;
import com.chunbin.app.R;
import com.chunbin.app.utils.CacheUtils;
import com.chunbin.app.utils.DensityUtil;

import java.util.ArrayList;
import java.util.List;

public class GuideActivity extends AppCompatActivity {

    private String TAG = GuideActivity.class.getSimpleName();
    private ViewPager view_pager;
    private Button start_main;
    private LinearLayout ll_point_group;
    private ImageView iv_red_point;

    private ArrayList<ImageView> imageViews;

    //两点间距
    private int leftmax;
    private int widthdpi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);

        initView();
        int[] ids = new int[]{
                R.drawable.guide_1,
                R.drawable.guide_2,
                R.drawable.guide_3
        };
        int widthdpi =DensityUtil.dip2px(this,10);

        imageViews = new ArrayList<ImageView>();
        for (int i = 0; i < ids.length; i++) {
            ImageView imageView = new ImageView(this);
            //设置背景
            imageView.setBackgroundResource(ids[i]);
            //添加集合
            imageViews.add(imageView);
            //创建点
            ImageView point = new ImageView(this);
            point.setBackgroundResource(R.drawable.point_normal);
            /**
             * 单位是像数
             * 把单位当成dp转成对应的像数
             */
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(widthdpi,widthdpi);
            if (i != 0) {
                layoutParams.leftMargin = widthdpi;
            }
            point.setLayoutParams(layoutParams);
            ll_point_group.addView(point);
            //设置ViewPager适配器
            view_pager.setAdapter(new MyPagerAdapter());

            //根据View的生命周期，但视图执行到onLayout或onDraw的时候，视图的宽和高，边距就都有了
            iv_red_point.getViewTreeObserver().addOnGlobalLayoutListener(new MyOnGlobalLayoutListener());

            //得到屏幕滑动的百分比
            view_pager.addOnPageChangeListener(new MyOnPageChangeListener());

            //设置按钮的点击事件
            start_main.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CacheUtils.setIsStartMain(GuideActivity.this,SplashActivity.START_MAIN,true);
                    Intent intent = new Intent(GuideActivity.this,MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            });
        }
    }


    private void initView() {
        view_pager = findViewById(R.id.view_pager);
        start_main = findViewById(R.id.start_main);
        ll_point_group = findViewById(R.id.ll_point_group);
        iv_red_point = findViewById(R.id.iv_red_point);
    }

    private class MyPagerAdapter extends PagerAdapter {
        /**
         * 返回数据的总个数
         * @return
         */
        @Override
        public int getCount() {
            return imageViews.size();
        }

        /**
         * 作用，getView
         * @param container ViewPager
         * @param position 要创业页面的位置
         * @return 返回和创建当前页面右关系的值
         */
        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            ImageView imageView = imageViews.get(position);
            container.addView(imageView);
            return imageView;
        }

        /**
         * 判断
         * @param view 当前创建的视图
         * @param object 上面instantiateItem返回的结果值
         * @return
         */
        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {

            return view == object;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView((View) object);
        }
    }

    private class MyOnGlobalLayoutListener implements ViewTreeObserver.OnGlobalLayoutListener {
        private String TAG=GuideActivity.class.getSimpleName()+"  "+ "  " + MyOnGlobalLayoutListener.class.getSimpleName();
        @Override
        public void onGlobalLayout() {
            //执行不止一次
            iv_red_point.getViewTreeObserver().removeOnGlobalLayoutListener(MyOnGlobalLayoutListener.this);
            //间距  = 第1个点距离左边的距离 - 第0个点距离左边的距离
            leftmax = ll_point_group.getChildAt(1).getLeft() - ll_point_group.getChildAt(0).getLeft();
            Log.d(TAG,"leftmax = " + leftmax);
        }
    }

    private class MyOnPageChangeListener implements ViewPager.OnPageChangeListener {
        /**
         *当页面回调是回调用这个方法
         * @param position              当前滑动页面的位置
         * @param positionOffset        页面滑动的百分比
         * @param positionOffsetPixels  滑动的像素
         */
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            //两点间移动的距离 = 屏幕滑动百分比 * 间距
            //int leftmargin = (int) (positionOffset * leftmax);

            //两点间滑动距离对应的坐标 = 原来的起始位置 +  两点间移动的距离
            int leftmargin = (int) (position * leftmax + positionOffset * leftmax);
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) iv_red_point.getLayoutParams();
            layoutParams.leftMargin = leftmargin;
            iv_red_point.setLayoutParams(layoutParams);
        }

        /**
         * 当页面被选中的时候，会调用这个方法
         * @param position 被选中页面的对应的位置
         */
        @Override
        public void onPageSelected(int position) {
            if (position == imageViews.size()-1) {
                start_main.setVisibility(View.VISIBLE);
            }else{
                start_main.setVisibility(View.GONE);
            }
        }

        /**
         * 当Viewpager页面滑动状态改变的时候
         * @param state
         */
        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }
}
