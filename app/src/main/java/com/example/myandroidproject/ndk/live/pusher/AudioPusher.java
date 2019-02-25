package com.example.myandroidproject.ndk.live.pusher;

import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.util.Log;

import com.example.myandroidproject.ndk.live.params.AudioParams;

/**
 * 音频推流器
 * todo 手动申请权限
 */
public class AudioPusher extends Pusher {

    private AudioParams mAudioParams;
    private AudioRecord mAudioRecord;
    private int miniBufferSize;
    private boolean isPushing;
    private PushRunnable mAudioRunnable;

    public AudioPusher(AudioParams mAudioParams) {
        this.mAudioParams = mAudioParams;
        int channelConfig = AudioFormat.CHANNEL_IN_STEREO;
        int pcmFormat = AudioFormat.ENCODING_PCM_16BIT;
        miniBufferSize = AudioRecord.getMinBufferSize(mAudioParams.getSampleRateInHz(),channelConfig,pcmFormat);
        mAudioRecord = new AudioRecord(MediaRecorder.AudioSource.MIC,mAudioParams.getSampleRateInHz(),
                channelConfig,pcmFormat,miniBufferSize);
        mAudioRunnable = new PushRunnable();

    }

    @Override
    public void startPush() {
        isPushing = true;
        new Thread(mAudioRunnable).start();
    }

    @Override
    public void stopPush() {
        isPushing = false;
        mAudioRecord.release();
        mAudioRecord.stop();
    }


    class PushRunnable implements Runnable {

        @Override
        public void run() {
            mAudioRecord.startRecording();
            while (isPushing) {
                //不停的读取音频数据
                byte[] buffer = new byte[miniBufferSize];
                int len = mAudioRecord.read(buffer,0,buffer.length);
                if (len > 0) {
                    //传递给Native层进行编码
                    PushNative.fireAudio(buffer,len);
                    Log.e("AudioPusher","音频数据 : " + len);
                }
            }
        }
    }
}
