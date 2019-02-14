package com.example.myandroidproject.ndk.jni_test;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

public class JNITestActivity extends AppCompatActivity {

    static {
        System.loadLibrary("pthread_demo");
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LinearLayout linearLayout = new LinearLayout(this);
        Button button = new Button(this);
        button.setText("JNITest");
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JNITest();
            }
        });
        linearLayout.addView(button);
        setContentView(linearLayout);
    }

    public native static void JNITest();
}
