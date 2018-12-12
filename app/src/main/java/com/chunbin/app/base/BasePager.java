package com.chunbin.app.base;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import com.chunbin.app.MainActivity;
import com.chunbin.app.R;

import static android.os.Build.VERSION_CODES.O;

public class BasePager {

    /**
     * 上下文
     */
    public Context context;

    /**
     * 视图，代表各个不同的页面
     */
    public View rootView;

    /**
     * 显示标题
     */
    public TextView tv_title;

    /**
     * 点击侧滑
     */
    public ImageButton ib_menu;

    /**
     * 加载各个子页面
     */
    public FrameLayout fl_content;

    public ImageButton ib_swich_list_grid;

    public Button btn_cart;

    public BasePager(Context context) {
        this.context = context;
        this.rootView = initView();
    }

    private View initView() {
        View view = View.inflate(context,R.layout.base_pager,null);
        tv_title = view.findViewById(R.id.tv_title);
        ib_menu = (ImageButton) view.findViewById(R.id.ib_menu);
        fl_content = (FrameLayout) view.findViewById(R.id.fl_content);
        ib_swich_list_grid = (ImageButton) view.findViewById(R.id.ib_swich_list_grid);
        btn_cart = (Button) view.findViewById(R.id.btn_cart);
        ib_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity mainActivity = (MainActivity) context;
                mainActivity.getSlidingMenu().toggle();
            }
        });
        return view;
    }

    /**
     * 初始化数据;当孩子需要初始化数据;或者绑定数据;联网请求数据并且绑定的时候，重写该方法
     */
    public void initData(){

    }
}
