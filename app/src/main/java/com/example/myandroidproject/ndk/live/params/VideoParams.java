package com.example.myandroidproject.ndk.live.params;

public class VideoParams {
    private int previewWidth;
    private int previewHeight;
    private int cameraID;

    private int bitRate = 480000;//码率 480kbps
    private int fps = 25;//帧率 25/s

    public VideoParams(int previewWidth, int previewHeight, int cameraID) {
        this.previewWidth = previewWidth;
        this.previewHeight = previewHeight;
        this.cameraID = cameraID;
    }

    public int getPreviewWidth() {
        return previewWidth;
    }

    public void setPreviewWidth(int previewWidth) {
        this.previewWidth = previewWidth;
    }

    public int getPreviewHeight() {
        return previewHeight;
    }

    public void setPreviewHeight(int previewHeight) {
        this.previewHeight = previewHeight;
    }

    public int getCameraID() {
        return cameraID;
    }

    public void setCameraID(int cameraID) {
        this.cameraID = cameraID;
    }

    public int getBitRate() {
        return bitRate;
    }

    public void setBitRate(int bitRate) {
        this.bitRate = bitRate;
    }

    public int getFps() {
        return fps;
    }

    public void setFps(int fps) {
        this.fps = fps;
    }
}
