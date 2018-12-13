package com.chunbin.app.pager;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.chunbin.app.R;
import com.chunbin.app.adapter.SmartServicePagerAdapter;
import com.chunbin.app.base.BasePager;
import com.chunbin.app.entity.FilmEntity;
import com.chunbin.app.entity.Trailers;
import com.chunbin.app.utils.CacheUtils;
import com.chunbin.app.utils.GsonUtil;
import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;
import com.google.gson.Gson;

import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class HomePager extends BasePager {
    private static final String TAG = "HomePager" ;

    private MaterialRefreshLayout refresh_layout;
    private RecyclerView recycler_view;
    private ProgressBar pd_loading;
    private SmartServicePagerAdapter adapter;
    private final static String url = "http://api.m.mtime.cn/PageSubArea/TrailerList.api";

    public static final int JSON_AJAX_NUMBER = 0x001;

    /**
     * 默认状态
     */
    private static final int STATE_NORMAL = 1;

    /**
     * 下拉加载状态
     */
    private static final int STATE_REFRES = 2;

    /**
     * 上啦刷新
     */
    private static final int STATE_LOADMORE = 3;

    /**
     * 默认正常状态
     */
    private int state = STATE_NORMAL;

    /**
     * 每页显示数据个数
     */
    private int pageSize =10;

    /**
     * 当前页
     */
    private int curPage =1;
    /**
     * 总页数
     */
    private int totalPage;

    /**
     * 数据列表
     */
    private ArrayList<Trailers> datas = new ArrayList<Trailers>(); ;

    public HomePager(Context context) {
        super(context);
    }

    @Override
    public void initData() {

        super.initData();
        View view = View.inflate(context, R.layout.home_pager,null);

        Log.e(TAG,"主页面数据被初始化了..");
        //1.设置标题
        tv_title.setText("主页面");
        refresh_layout = view.findViewById(R.id.refresh_layout);
        recycler_view = view.findViewById(R.id.recycler_view);
        pd_loading = view.findViewById(R.id.pd_loading);
        recycler_view.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false));
        adapter = new SmartServicePagerAdapter(context,datas);
        recycler_view.setAdapter(adapter);
        if (fl_content != null) {
            fl_content.removeAllViews();
        }
        fl_content.addView(view);

        initRefresh();
        /*//2.联网请求，得到数据，创建视图
        TextView textView = new TextView(context);
        textView.setGravity(Gravity.CENTER);
        textView.setTextColor(Color.RED);
        textView.setTextSize(25);
        //3.把子视图添加到BasePager的FrameLayout中
        fl_content.addView(textView);
        //4.绑定数据
        textView.setText("主页面内容");*/
        getDataFromNew();
    }

    private void initRefresh() {
        refresh_layout.setMaterialRefreshListener(new MaterialRefreshListener() {
            @Override
            public void onRefresh(MaterialRefreshLayout materialRefreshLayout) {
                state = STATE_REFRES;
                curPage = 1;
                getDataFromNew();
            }

            @Override
            public void onRefreshLoadMore(MaterialRefreshLayout materialRefreshLayout) {
                super.onRefreshLoadMore(materialRefreshLayout);
                if (curPage<(datas.size()/pageSize)){
                    state = STATE_LOADMORE;
                    curPage +=1;
                    getDataFromNew();
                }else {
                    Toast.makeText(context, "已经到底了", Toast.LENGTH_SHORT).show();
                    refresh_layout.finishRefreshLoadMore();
                }
            }
        });
    }

    public Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == JSON_AJAX_NUMBER) {
                Log.d(TAG,"msg.what = " + msg.what);
                processData(msg.obj.toString());
                Log.e(TAG,"msg.obj.toString() = " + msg.obj.toString());
            }
        }
    };

    public void getDataFromNew() {

        /*String json = CacheUtils.getString(context,url);
        if (!TextUtils.isEmpty(json)) {
            processData(json);
        }*/
        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
                        .get()
                        .url(url)
                        .build();
                    Call call= client.newCall(request);
                    call.enqueue(new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            Log.e(TAG,"e = "+e);
                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            if (response.isSuccessful()) {
                                if (response.code() == HttpURLConnection.HTTP_OK) {
                                    String s = response.body().string();
                                    //CacheUtils.putString(context,url,s);
                                    Log.e(TAG,"s = "+s);
                                    Message message = new Message();
                                    message.obj = s;
                                    message.what = JSON_AJAX_NUMBER;
                                    mHandler.sendMessage(message);
                                }
                            }
                        }
                    });

            }
        }).start();

    }

    private void processData(String json) {
        Map<String, Object> map = GsonUtil.toMap(json);
        List<Map<String, Object>> listMap = (List<Map<String, Object>>) map.get("trailers");
        for (Map<String, Object> map1 : listMap) {

            Trailers trailers = new Trailers();
            trailers.setId(Long.parseLong(map1.get("id").toString()));
            trailers.setMovieName(map1.get("movieName").toString());
            trailers.setCoverImg(map1.get("coverImg").toString());
            trailers.setMovieId(Long.parseLong(map1.get("movieId").toString()));
            trailers.setUrl(map1.get("url").toString());
            trailers.setHightUrl(map1.get("hightUrl").toString());
            trailers.setVideoTitle(map1.get("videoTitle").toString());
            trailers.setRating(map1.get("rating").toString());
            trailers.setSummary(map1.get("summary").toString());
            trailers.setVideoLength(Integer.parseInt(map1.get("videoLength").toString()));
            String str = map1.get("type").toString().substring(1,map1.get("type").toString().length()-1);
            //System.out.println(str);
            String[] stra = str.split(",");
            trailers.setType(stra);
            datas.add(trailers);
        }
        showData();
    }

    private void showData() {
        switch (state){
            case STATE_NORMAL:
                Log.e(TAG,"datas.size() = "+datas.size());


                break;
            case STATE_REFRES:
                adapter.clearDatas();
                adapter.addDatas(0,datas);
                refresh_layout.finishRefresh();
                break;
            case STATE_LOADMORE:
                adapter.addDatas(adapter.getDataCount(),datas);
                refresh_layout.finishRefreshLoadMore();
                break;
        }
        pd_loading.setVisibility(View.GONE);
    }



}
