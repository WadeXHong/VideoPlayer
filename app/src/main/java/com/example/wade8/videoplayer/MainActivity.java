package com.example.wade8.videoplayer;

import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Color;
import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.FrameLayout;
import android.widget.MediaController;
import android.widget.RelativeLayout;

public class MainActivity extends AppCompatActivity implements VideoControllerView.MediaPlayerControl, SurfaceHolder.Callback, MediaPlayer.OnPreparedListener{

    private SurfaceView mSurfaceView;
    private RelativeLayout mInnerRelativeLayout;
    private RelativeLayout mRelativeLayout;
    private FrameLayout mOuterFramLayout;
    private String mVideoUrl = "https://s3-ap-northeast-1.amazonaws.com/mid-exam/Video/protraitVideo.mp4";
    //https://s3-ap-northeast-1.amazonaws.com/mid-exam/Video/protraitVideo.mp4
    //https://s3-ap-northeast-1.amazonaws.com/mid-exam/Video/taeyeon.mp4
    private MediaController mediaController;
    private VideoControllerView mController;
    private MediaPlayer mMediaPlayer;
    private int mVideoHeight, mVideoWidth, mScreenHeight, mScreenWidth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        mScreenHeight = metrics.heightPixels;
        mScreenWidth = metrics.widthPixels;

        mInnerRelativeLayout = findViewById(R.id.innerrelativelayout);
        mInnerRelativeLayout.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, mScreenWidth));
        mRelativeLayout = findViewById(R.id.relativelayout);
        mSurfaceView = findViewById(R.id.surfaceview);
        mOuterFramLayout = findViewById(R.id.outerframlayout);
        SurfaceHolder videoHolder = mSurfaceView.getHolder();
        videoHolder.addCallback(this);
        mController = new VideoControllerView(this);
        mMediaPlayer = new MediaPlayer();



        try{
            mMediaPlayer.setAudioAttributes(new AudioAttributes.Builder().setContentType(AudioAttributes.CONTENT_TYPE_MOVIE).build());
            mMediaPlayer.setDataSource(mVideoUrl);
            mMediaPlayer.setOnPreparedListener(this);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (getResources().getConfiguration().orientation == 1) {
            mController.showPermanent();
            Log.e("onTouchEvent", "executed");
        }else {
            mController.show();
        }
        return false;
    }

    @Override
    public void start() {
        mMediaPlayer.start();
    }

    @Override
    public void pause() {
        mMediaPlayer.pause();
    }

    @Override
    public int getDuration() {
        return 0;
    }

    @Override
    public int getCurrentPosition() {
        return mMediaPlayer.getCurrentPosition();
    }

    @Override
    public void seekTo(int pos) {
        mMediaPlayer.seekTo(pos);
    }

    @Override
    public boolean isPlaying() {
        return mMediaPlayer.isPlaying();
    }

    @Override
    public int getBufferPercentage() {
        return 0;
    }

    @Override
    public boolean canPause() {
        return true;
    }

    @Override
    public boolean canSeekBackward() {
        return true;
    }

    @Override
    public boolean canSeekForward() {
        return true;
    }

    @Override
    public boolean isFullScreen() {
        return false;
    }

    @Override
    public void toggleFullScreen() {
        //變成全螢幕
        if (getResources().getConfiguration().orientation == 1){
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);
        }else {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);
        }
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        mController.setMediaPlayer(this);
        mController.setAnchorView(mOuterFramLayout);
        mVideoHeight = mMediaPlayer.getVideoHeight();
        mVideoWidth = mMediaPlayer.getVideoWidth();
        Log.e("Height", mVideoHeight +"");
        Log.e("Width", mVideoWidth +"");
        if (getResources().getConfiguration().orientation == 1) {
            portraitLayoutSet();
            mController.showPermanent();
        }else {
            mController.show();
        }
        mMediaPlayer.start();
    }

    private void portraitLayoutSet() {
        if (mVideoHeight>mVideoWidth){
            mInnerRelativeLayout.setBackgroundColor(Color.BLACK);

            mSurfaceView.setLayoutParams(new RelativeLayout.LayoutParams(mScreenWidth *mVideoWidth/mVideoHeight, mScreenWidth));

        }else {
            mInnerRelativeLayout.setBackgroundColor(Color.TRANSPARENT);
            mSurfaceView.setLayoutParams(new RelativeLayout.LayoutParams(mScreenWidth, mScreenWidth*mVideoHeight/mVideoWidth));
        }
    }

    private void landscapeLayoutSet() {
        if (mVideoHeight>mVideoWidth){
            mInnerRelativeLayout.setBackgroundColor(Color.BLACK);

            mSurfaceView.setLayoutParams(new RelativeLayout.LayoutParams(mScreenWidth*mVideoWidth/mVideoHeight, mScreenWidth));

        }else {
            mInnerRelativeLayout.setBackgroundColor(Color.TRANSPARENT);
            mSurfaceView.setLayoutParams(new RelativeLayout.LayoutParams(mScreenHeight, mScreenHeight*mVideoHeight/mVideoWidth));
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        mMediaPlayer.setDisplay(holder);
        mMediaPlayer.prepareAsync();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }



    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){
            portraitLayoutSet();
        }else if(newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE){
            landscapeLayoutSet();
        }
    }
}
