package com.example.wade8.videoplayer;

import android.content.pm.ActivityInfo;
import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.FrameLayout;
import android.widget.MediaController;

public class MainActivity extends AppCompatActivity implements VideoControllerView.MediaPlayerControl, SurfaceHolder.Callback, MediaPlayer.OnPreparedListener{

    private SurfaceView mSurfaceView;
    private FrameLayout mFrameLayout;
    private ConstraintLayout mConstraintLayout;
    private FrameLayout mOuterFramLayout;
    private String mVideoUrl = "https://s3-ap-northeast-1.amazonaws.com/mid-exam/Video/taeyeon.mp4";
    private MediaController mediaController;
    private VideoControllerView mController;
    private MediaPlayer mMediaPlayer;
    private int mHeight, mWidth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mFrameLayout = findViewById(R.id.framelayout);
        mConstraintLayout = findViewById(R.id.constraintlayout);
        mSurfaceView = findViewById(R.id.surfaceview);
        mOuterFramLayout = findViewById(R.id.outerframlayout);
        SurfaceHolder videoHolder = mSurfaceView.getHolder();
        videoHolder.addCallback(this);
        mController = new VideoControllerView(this);
        mMediaPlayer = new MediaPlayer();



        try{
            mMediaPlayer.setAudioAttributes(new AudioAttributes.Builder().setContentType(AudioAttributes.CONTENT_TYPE_MOVIE).build());
            mMediaPlayer.setDataSource(mVideoUrl);
            //Sets the audio stream type for this MediaPlayer，设置流的类型，此为音乐流
//            mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mMediaPlayer.setOnPreparedListener(this);

            //Sets the SurfaceHolder to use for displaying the video portion of the media，设置播放的容器
//            mMediaPlayer.setDisplay(mSurfaceView.getHolder());
//            mMediaPlayer.prepare();
            //Interface definition for a callback to be invoked when the media source is ready for playback



//            mController = new VideoControllerView(this);
//            mController.setAnchorView(mFrameLayout);
//            mController.setMediaPlayer(this);
//            mSurfaceView.setMediaController(mController);
////            Uri link = Uri.parse(mVideoUrl.replace(" ","%20"));
//            mSurfaceView.setVideoURI(Uri.parse(mVideoUrl));
//            mSurfaceView.requestFocus();
//            mSurfaceView.start();
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
        mHeight = mMediaPlayer.getVideoHeight();
        mWidth = mMediaPlayer.getVideoWidth();
        Log.e("Height",mHeight+"");
        Log.e("Width",mWidth+"");
        if (getResources().getConfiguration().orientation == 1) {
            mController.showPermanent();
        }else {
            mController.show();
        }
        mMediaPlayer.start();
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
}
