package com.example.myandroidproject.ndk.live;

import android.app.Activity;
import android.os.Bundle;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;

import com.example.myandroidproject.R;
import com.example.myandroidproject.ndk.live.pusher.PusherManager;


/**
 * 直播推流（视频，音频）
 * 步骤：
 * 1、音视频采集，参数设置
 * 2、编译x264,faac,rtmpdump源文件库
 * 3、流数据传入Native层
 * 4、视频编码
 * 5、音频编码
 * 6、RTMP推流
 *
 *H264:
 * 逻辑划分：VCL:视频编码层（编码），NAL：网络提起层（打包，传输）
 * NALU：NAL单元
 * I帧（关键帧，完整帧）：帧内压缩，帧内预测
 * P帧，B帧（差别帧） 帧间压缩，P帧前向预测，B帧双向预测
 */
public class LiveActivity extends Activity implements View.OnClickListener {

    private Button mBtnStartPush;
    private SurfaceView mSurfaceView;
    private PusherManager mPusherManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live);
       initView();
       initPuser();
    }


    private void initView(){
        mBtnStartPush = findViewById(R.id.btn_start_push);
        mBtnStartPush.setOnClickListener(this);
        mSurfaceView = findViewById(R.id.surfaceview);
    }


    private void initPuser() {
        mPusherManager = new PusherManager(mSurfaceView.getHolder());
    }

    @Override
    public void onClick(View v) {
        mPusherManager.startPush();
    }
}
