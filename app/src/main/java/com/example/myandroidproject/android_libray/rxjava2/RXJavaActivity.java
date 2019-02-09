package com.example.myandroidproject.android_libray.rxjava2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.myandroidproject.R;
import com.example.myandroidproject.android_libray.rxjava2.imageloader.ImageLoader;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class RXJavaActivity extends AppCompatActivity {
    private static final String TAG = "RXJavaActivity";

    private Button mButton;
    private ImageView mImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rxjava);
        mButton = this.findViewById(R.id.button);
        mImageView = this.findViewById(R.id.imageview);
       mButton.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               ImageLoader.width(RXJavaActivity.this)
                       .load("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1547784930973&di=cb1d80306b2b007bb40b8869a8e0a375&imgtype=0&src=http%3A%2F%2Fdingyue.nosdn.127.net%2FBHtymRNYXdO1BD1Y9qzW44pUNSPBcdpEW0hmmdGyq%3D2Ro1503710905103.jpg")
                       .into(mImageView);
           }
       });
    }
}
