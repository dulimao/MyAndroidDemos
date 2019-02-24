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
