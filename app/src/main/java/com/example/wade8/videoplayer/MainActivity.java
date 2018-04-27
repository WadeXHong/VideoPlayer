package com.example.wade8.videoplayer;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.MediaController;
import android.widget.VideoView;

public class MainActivity extends AppCompatActivity {

    private VideoView mVideoView;
    private String mVideoUrl = "http://s3-ap-northeast-1.amazonaws.com/mid-exam/Video/taeyeon.mp4";
    private MediaController mediaController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mVideoView = findViewById(R.id.videoview);

        try{
            mediaController = new MediaController(this);
            mediaController.setAnchorView(mVideoView);
            mediaController.setMediaPlayer(mVideoView);
            mVideoView.setMediaController(mediaController);
//            Uri link = Uri.parse(mVideoUrl.replace(" ","%20"));
            mVideoView.setVideoURI(Uri.parse(mVideoUrl));
            mVideoView.requestFocus();
            mVideoView.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }




}
