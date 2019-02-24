package com.example.myandroidproject.ndk.live.pusher;

import android.hardware.Camera;
import android.view.SurfaceHolder;

import com.example.myandroidproject.ndk.live.params.AudioParams;
import com.example.myandroidproject.ndk.live.params.VideoParams;

public class PusherManager {

    private SurfaceHolder mSurfaceHolder;

    private VideoPusher videoPusher;
    private AudioPusher audioPusher;

    public PusherManager(SurfaceHolder holder){
        this.mSurfaceHolder = holder;
        prepare();
    }

    private void prepare() {
        //初始化视频，音频推流器
        VideoParams videoParams = new VideoParams(480,320, Camera.CameraInfo.CAMERA_FACING_BACK);
        videoPusher = new VideoPusher(mSurfaceHolder,videoParams);

        AudioParams audioParams = new AudioParams();
        audioPusher = new AudioPusher(audioParams);
    }

    public void startPush() {
        videoPusher.startPush();
        audioPusher.startPush();
    }

}
