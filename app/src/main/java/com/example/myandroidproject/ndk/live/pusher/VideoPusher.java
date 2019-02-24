package com.example.myandroidproject.ndk.live.pusher;


import android.hardware.Camera;
import android.util.Log;
import android.view.SurfaceHolder;

import com.example.myandroidproject.ndk.live.params.VideoParams;

/**
 * 视频推流器
 */
public class VideoPusher extends Pusher implements SurfaceHolder.Callback, Camera.PreviewCallback {

    private SurfaceHolder mSuraceHolder;
    private Camera mCamera;
    private VideoParams mVideoParams;

    public VideoPusher(SurfaceHolder mSuraceHolder,VideoParams videoParams) {
        this.mSuraceHolder = mSuraceHolder;
        this.mVideoParams = videoParams;
        this.mSuraceHolder.addCallback(this);
    }

    @Override
    public void startPush() {
        startPreview();
    }

    @Override
    public void stopPush() {

    }




    @Override
    public void surfaceCreated(SurfaceHolder holder) {

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }
    byte[] videoBuffer;
    /**
     * 开始相机预览
     * todo 动态申请权限
     */
    private void startPreview() {
        try {
            mCamera = Camera.open(mVideoParams.getCameraID());
            mCamera.setPreviewDisplay(mSuraceHolder);
//            videoBuffer = new byte[mVideoParams.getPreviewWidth() * mVideoParams.getPreviewHeight() * 4];
            videoBuffer = new byte[3110400];
            mCamera.addCallbackBuffer(videoBuffer);
            mCamera.setPreviewCallbackWithBuffer(this);
            mCamera.startPreview();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 停止相机预览
     */
    private void stopPreview() {
        if (mCamera != null) {
            mCamera.stopPreview();
            mCamera.release();
            mCamera = null;
        }
    }

    @Override
    public void onPreviewFrame(byte[] data, Camera camera) {
        if (camera != null) {
            camera.addCallbackBuffer(videoBuffer);
            Log.e("VideoPusher","视频数据" + data.length);
        }
    }
}
