package com.chunbin.app.pager;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.Gravity;
import android.widget.TextView;

import com.chunbin.app.base.BasePager;

public class HotspotPager extends BasePager {
    private static final String TAG = "HotspotPager" ;
    @Override
    public void initData() {
        super.initData();
        Log.e(TAG,"热点数据被初始化了..");
        //1.设置标题
        tv_title.setText("热点页面");
        //2.联网请求，得到数据，创建视图
        TextView textView = new TextView(context);
        textView.setGravity(Gravity.CENTER);
        textView.setTextColor(Color.RED);
        textView.setTextSize(25);
        //3.把子视图添加到BasePager的FrameLayout中
        fl_content.addView(textView);
        //4.绑定数据
        textView.setText("热点页面内容");
    }

    public HotspotPager(Context context) {
        super(context);
    }
}
