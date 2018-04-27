package com.example.wade8.videoplayer;

import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.FrameLayout;
import android.widget.MediaController;

import java.util.jar.Attributes;

public class MainActivity extends AppCompatActivity implements VideoControllerView.MediaPlayerControl, SurfaceHolder.Callback, MediaPlayer.OnPreparedListener{

    private SurfaceView mSurfaceView;
    private FrameLayout mFrameLayout;
    private String mVideoUrl = "https://s3-ap-northeast-1.amazonaws.com/mid-exam/Video/taeyeon.mp4";
    private MediaController mediaController;
    private VideoControllerView mController;
    private MediaPlayer mMediaPlayer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mFrameLayout = findViewById(R.id.framelayout);
        mSurfaceView = findViewById(R.id.surfaceview);
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
        mController.show();
        Log.e("onTouchEvent","executed");
        return false;
    }

    @Override
    public void start() {
        mMediaPlayer.start();
    }

    @Override
    public void pause() {

    }

    @Override
    public int getDuration() {
        return 0;
    }

    @Override
    public int getCurrentPosition() {
        return 0;
    }

    @Override
    public void seekTo(int pos) {

    }

    @Override
    public boolean isPlaying() {
        return false;
    }

    @Override
    public int getBufferPercentage() {
        return 0;
    }

    @Override
    public boolean canPause() {
        return false;
    }

    @Override
    public boolean canSeekBackward() {
        return false;
    }

    @Override
    public boolean canSeekForward() {
        return false;
    }

    @Override
    public boolean isFullScreen() {
        return false;
    }

    @Override
    public void toggleFullScreen() {

    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        mController.setMediaPlayer(this);
        mController.setAnchorView(mFrameLayout);
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
