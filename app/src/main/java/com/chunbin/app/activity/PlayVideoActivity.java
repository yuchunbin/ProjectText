package com.chunbin.app.activity;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.chunbin.app.R;
import com.chunbin.app.adapter.SmartServicePagerAdapter;
import com.chunbin.app.application.MyApplication;
import com.chunbin.app.controller.video.AudioController;
import com.chunbin.app.controller.video.LightnessController;
import com.chunbin.app.utils.FullVideoView;

public class PlayVideoActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener,Handler.Callback, SeekBar.OnSeekBarChangeListener, MediaPlayer.OnPreparedListener, MediaPlayer.OnErrorListener, View.OnTouchListener {
    private FullVideoView video_View;
    private TextView curent_position,duration,tv_title;
    private CheckBox pause_play,full_screen_controller;
    private String url = "";
    private String move_name = "";

    //VideoView的容器
    private FrameLayout video_container;

    private SeekBar progress_ctroller;

    private LinearLayout bottem_controller;
    private int mHeight;

    private SeekBar bright_display;

    //更新进度的标志
    private final static int UPDATE_PROGRESS =1;
    private final static int PAUSE_AUTO_DISAPPEAR = 2;//播放和暂停按钮设置
    private final static int BRIGHTNESS_DISPLAY_DISAPPEAR = 3;

    private Handler mHandler = new Handler(this);
    private String TAG = "PlayVideoActivity";
    private RelativeLayout title_background_relative_layout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_video);

        Intent intent  = getIntent();
        Bundle bundle = intent.getBundleExtra(SmartServicePagerAdapter.VIDEO_ACTIVITY_KEY);
        url = bundle.getString(SmartServicePagerAdapter.VIDEO_ACTIVITY_URL);
        move_name = bundle.getString(SmartServicePagerAdapter.VIDEO_ACTIVITY_NAME);
        //MediaController videoController = new MediaController(this);
        video_View = findViewById(R.id.video_view);
        video_View.setVideoURI(Uri.parse(url));
        initTitleBar();
        initView();

    }

    private void initTitleBar() {
        tv_title = findViewById(R.id.tv_title);
        tv_title.setText("正在播放："+move_name);
        title_background_relative_layout = findViewById(R.id.title_background_relative_layout);

    }

    public SeekBar getBright_display() {
        bright_display.setVisibility(View.VISIBLE);
        return bright_display;
    }

    @SuppressLint("WrongViewCast")
    private void initView() {
        //设置亮度的SeekBar
        bright_display = findViewById(R.id.brightness_display);

        //时间控件
        curent_position = findViewById(R.id.current_position);
        duration = findViewById(R.id.duration);

        //设置暂停
        pause_play = findViewById(R.id.pause_play);
        pause_play.setOnCheckedChangeListener(this);

        //设置全屏
        full_screen_controller = findViewById(R.id.full_screen_controller);
        full_screen_controller.setOnCheckedChangeListener(this);

        //设置SeekBar并设置点击事件
        progress_ctroller = findViewById(R.id.progress_controller);
        progress_ctroller.setOnSeekBarChangeListener(this);

        //设置VideoView的点击事件，值预加载和加载错误
        video_View.setOnPreparedListener(this);
        //video_View.setOnErrorListener(this);

        bottem_controller = findViewById(R.id.bottem_controller);

        //设置最外层FrameLayout的点击事件
        video_container = findViewById(R.id.video_container);
        //因为设置点击事件,他的事件分发就会被消费掉,使得下边的ontouth没用  因为他没有返回值 默认是消费掉

        video_container.setOnTouchListener(this);

        //handler发送消息  3秒之后播放和暂停的按钮消失
        mHandler.sendEmptyMessageDelayed(PAUSE_AUTO_DISAPPEAR,3000);
    }


    @Override
    public boolean handleMessage(Message message) {
        switch (message.what){
            case UPDATE_PROGRESS:
                //设置总时间 设置总时间
                int duration = video_View.getDuration();
                if (progress_ctroller.getMax() == 100) {
                    progress_ctroller.setMax(duration);
                }
                //Log.e(TAG,"duration = " + duration);
                CharSequence format = DateFormat.format("mm:ss",duration);
                //Log.e(TAG,"format = " + format.toString());
                this.duration.setText(format);

                //设置当前播放的进度 并将当前的播放进度设置文本中
                int currentPosition = video_View.getCurrentPosition();
                CharSequence current_Position = DateFormat.format("mm:ss",currentPosition);
//                Log.e(TAG,"currentPosition = " + currentPosition);
//                Log.e(TAG,"current_Position = " + current_Position.toString());
                this.curent_position.setText(current_Position);

                //设置seekbar的当前进度
                progress_ctroller.setProgress(currentPosition);
                mHandler.sendEmptyMessageDelayed(UPDATE_PROGRESS,1000);
                break;
            case PAUSE_AUTO_DISAPPEAR:
                bottem_controller.setVisibility(View.GONE);
                pause_play.setVisibility(View.GONE);

                break;
            case BRIGHTNESS_DISPLAY_DISAPPEAR:
                bright_display.setVisibility(View.GONE);
                title_background_relative_layout.setVisibility(View.GONE);
                break;
        }
        return false;
    }


    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean user) {
        Log.e(TAG,"user = " + user + "   compoundButton.getId() = " + compoundButton.getId());
        Log.e(TAG,"R.id.pause_play = " + R.id.pause_play + "   R.id.full_screen_controller = " + R.id.full_screen_controller);
        switch (compoundButton.getId()){
            case R.id.pause_play:
                if (user) {//user为true，用户选中，视频播放，显示成暂停图标
                    video_View.requestFocus();
                    video_View.start();//视频播放
                    //修改播放事件，handler没隔1秒更新播放进度
                    mHandler.sendEmptyMessageDelayed(UPDATE_PROGRESS,1000);
                }else{
                    video_View.pause();
                    //移除，不在发送handler
                    mHandler.removeMessages(UPDATE_PROGRESS);
                }
                break;
            case R.id.full_screen_controller:
                if (user) {//用户点击全屏显示
                    //设置全屏
                    //设置全屏
                    getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
                    //设置手机屏幕横向
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

                    //需要修改VieoView 的高度  如果不修改 当它横屏时不会去充满整个屏幕
                    //首先获得原来的高度
                    mHeight = video_container.getHeight();
                    //重新制定高度
                    ViewGroup.LayoutParams params = video_container.getLayoutParams();
                    params.height = ViewGroup.LayoutParams.MATCH_PARENT;

                    video_container.setLayoutParams(params);

                }else {//从全屏状态回去
                    getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                    //还原之前的高度
                    ViewGroup.LayoutParams portrait_params = video_container.getLayoutParams();
                    portrait_params.height = mHeight;
                    video_container.setLayoutParams(portrait_params);
                }
                break;
        }

    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
        if (b){
            video_View.seekTo(i);
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        Log.e(TAG,"onStartTrackingTouch() --> seekBar = " + seekBar);
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        Log.e(TAG,"onStopTrackingTouch() --> seekBar = " + seekBar);
    }

    @Override
    public void onPrepared(MediaPlayer mediaPlayer) {
        pause_play.setChecked(true);//让视屏进行播放
//        video_View.requestFocus();
//        video_View.start();
    }

    @Override
    public boolean onError(MediaPlayer mediaPlayer, int i, int i1) {
        //当视屏播放错误时,弹出一个对话框
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setIcon(R.mipmap.ic_launcher);
        builder.setMessage("无法播放");
        builder.setTitle("问题");
        builder.setNegativeButton("取消",null);
        builder.create().show();

        return true;
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        switch (motionEvent.getAction()){
            case MotionEvent.ACTION_DOWN:
                title_background_relative_layout.setVisibility(View.VISIBLE);
                pause_play.setVisibility(View.VISIBLE);
                bottem_controller.setVisibility(View.VISIBLE);
                mHandler.sendEmptyMessageDelayed(PAUSE_AUTO_DISAPPEAR,3000);
                break;
        }
        return false;
    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        switch (newConfig.orientation){
            case Configuration.ORIENTATION_LANDSCAPE://横屏
                title_background_relative_layout.getBackground().mutate().setAlpha(0);
                MyApplication.isLandscape = true;
                break;
            case Configuration.ORIENTATION_PORTRAIT://竖屏
                title_background_relative_layout.getBackground().mutate().setAlpha(255);
                MyApplication.isLandscape = false;
                break;
    }
        super.onConfigurationChanged(newConfig);
    }

    private long last_time = 0;

    //点击回退键  如果是全屏,点击时首先回到竖屏
    @Override
    public void onBackPressed() {

        //判断是否是横屏
        if (MyApplication.isLandscape){
            //设置屏幕为竖屏
            full_screen_controller.setChecked(false);
        }else {
            //超过两秒
            if (false){
                //System.currentTimeMillis()-last_time>2000
                Toast.makeText(this,"再次点击退出程序",Toast.LENGTH_LONG).show();
                last_time = System.currentTimeMillis();
            }else {
                super.onBackPressed();
            }

        }

    }

    private float laxt_x,laxt_y;
    //手指在屏幕上滑动时 快进 快退 声音 屏幕亮度
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.e(TAG,"isLandscape = " + MyApplication.isLandscape);
        if (MyApplication.isLandscape){//手机横屏
            Log.e(TAG,"event.getAction() = " + event.getAction());
            Log.e(TAG,"MotionEvent.ACTION_DOWN = " + MotionEvent.ACTION_DOWN);
            Log.e(TAG,"MotionEvent.ACTION_MOVE = " + MotionEvent.ACTION_MOVE);
            Log.e(TAG,"MotionEvent.ACTION_UP = " + MotionEvent.ACTION_UP);
            switch (event.getAction()){
                case MotionEvent.ACTION_DOWN://手指按下
                    //手指按下 记录
                    laxt_x = event.getX();
                    laxt_y = event.getY();
                    break;
                case MotionEvent.ACTION_MOVE://手指在屏幕上移动
                    //如果在x轴方向移动的变化大于y轴  快进 快退
                    float x = event.getX();
                    float y = event.getY();
                    if (Math.abs(x-laxt_x)>Math.abs(y-laxt_y)){//快进,退
                        if (Math.abs(x-laxt_x)>10){
                            int widthPixels = this.getResources().getDisplayMetrics().widthPixels;
                            float xDelta = x - laxt_x;
                            if (x-laxt_x>10){//进
                                forwa(xDelta,widthPixels);
                            }else if (x-laxt_x<-10){//退

                                backward(xDelta,widthPixels);// 该值是负数
                            }
                        }
                    }else {//视屏亮度
                        //手机屏幕左半部分 修改亮度
                        if (Math.abs(y-laxt_y)>10){//用户有意识进行滑动
                            float yDilta = y - laxt_y;
                            //y轴方向的总高度
                            int heightPixels = this.getResources().getDisplayMetrics().heightPixels;

                            if (x<this.getResources().getDisplayMetrics().widthPixels/2){
                                //
                                if (y-laxt_y>10){
                                    LightnessController.turnDown(this,yDilta,heightPixels);
                                }else if (y-laxt_y<-10){
                                    LightnessController.turnUp(this,yDilta,heightPixels);
                                }
                            }else {
                                //手机屏幕右半部分 修改声音
                                if (y-laxt_y>10){//向下移动 声音降低
                                    AudioController.turnDown(this,yDilta,heightPixels);
                                }else if (y-laxt_y<-10){//向上移动,声音反放打
                                    AudioController.turnup(this,yDilta,heightPixels);
                                }
                            }
                        }

                    }

                    break;
                case MotionEvent.ACTION_UP:
                    //修改手指滑动的位置
                    laxt_x = event.getX();
                    laxt_y = event.getY();
                    mHandler.sendEmptyMessageDelayed(BRIGHTNESS_DISPLAY_DISAPPEAR,1000);
                    break;
            }
        }else {//竖屏展示

        }
        return super.onTouchEvent(event);
    }

    private void backward(float xDelta, int widthPixels) {
        int duration = video_View.getDuration();
        int currentPosition = video_View.getCurrentPosition();
        float durationDelta = xDelta / widthPixels * duration;//负数

        int video_move_position = (int) Math.max(0, currentPosition + durationDelta);
        video_View.seekTo(video_move_position);

        progress_ctroller.setProgress(video_move_position);
        //当前播放的时间
        curent_position.setText(DateFormat.format("mm:ss",video_move_position));
    }

    //视屏快进
    private void forwa(float xDelta, int widthPixels) {
        int duration = video_View.getDuration();
        int currentPosition = video_View.getCurrentPosition();
        float durationDelta = xDelta / widthPixels * duration;
        //参数二是相加得到的  有可能大于视屏的总长度
        int video_move_position = (int) Math.min(duration, currentPosition + durationDelta);
        video_View.seekTo(video_move_position);
        //移动seekbar
        progress_ctroller.setProgress(video_move_position);
        //当前播放的时间
        curent_position.setText(DateFormat.format("mm:ss",video_move_position));


    }

    @Override
    protected void onResume() {
        Log.e(TAG,"onResume()");
        super.onResume();
    }

    @Override
    protected void onPause() {
        Log.e(TAG,"onPause()");
        super.onPause();
    }

    @Override
    protected void onStart() {
        Log.e(TAG,"onStart()");
        pause_play.setChecked(true);

        super.onStart();
    }

    private static int mCurrentPosition=0;
    @Override
    protected void onStop() {
        Log.e(TAG,"onStop()");
        mCurrentPosition = video_View.getCurrentPosition();
        Log.e(TAG,"mCurrentPosition = " + mCurrentPosition);
        progress_ctroller.setProgress(mCurrentPosition);
        mHandler.sendEmptyMessageDelayed(UPDATE_PROGRESS,1000);
        super.onStop();
    }

    @Override
    protected void onRestart() {
        Log.e(TAG,"onRestart()");
        super.onRestart();
    }

    @Override
    protected void onDestroy() {
        Log.e(TAG,"onDestroy()");
        video_View.clearFocus();
        super.onDestroy();

    }
}
