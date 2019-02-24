package com.example.myandroidproject.ndk.live.params;

public class AudioParams {
    //采样率
    private int sampleRateInHz = 44100;
    //声道个数
    private int channel = 1;

    public AudioParams() {
    }

    public AudioParams(int sampleRateInHz, int channel) {
        this.sampleRateInHz = sampleRateInHz;
        this.channel = channel;
    }

    public int getSampleRateInHz() {
        return sampleRateInHz;
    }

    public void setSampleRateInHz(int sampleRateInHz) {
        this.sampleRateInHz = sampleRateInHz;
    }

    public int getChannel() {
        return channel;
    }

    public void setChannel(int channel) {
        this.channel = channel;
    }
}
