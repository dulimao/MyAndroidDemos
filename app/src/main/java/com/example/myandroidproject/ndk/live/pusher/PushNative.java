package com.example.myandroidproject.ndk.live.pusher;

public class PushNative {

    static {
        System.loadLibrary("live");
    }

    public static native void startPush(String url);

    public static native void stopPush();

    public static native void release();

    //设置视频参数
    public static native void setVideoOptions(int width,int height,int bitrate,int fps);

    //设置音频参数
    public static native void setAudioOptions(int sampleRateInHz,int channel);

    //发送视频数据
    public static native void fireVideo(byte[] data);

    //发送音频数据
    public static native void fireAudio(byte[] data,int len);
}
