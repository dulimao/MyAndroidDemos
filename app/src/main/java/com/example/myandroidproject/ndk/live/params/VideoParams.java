package com.example.myandroidproject.ndk.live.params;

public class VideoParams {
    private int previewWidth;
    private int previewHeight;
    private int cameraID;

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
}
