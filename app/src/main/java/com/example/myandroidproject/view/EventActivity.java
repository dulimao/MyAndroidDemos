package com.example.myandroidproject.view;

import android.annotation.SuppressLint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.myandroidproject.R;



/**
*@author 杜立茂
*@date 2018/12/27 11:27
*@description 事件传递顺序：ViewGroup -> View
 *              View事件传递入口方法：dispatchTouchEvent() -> onTouch() -> onTouchEvent() -> onClick()
 *              ViewGroup事件传递入口：dispatchTouchEvent() -> onIntercepteTouchEvent() -> child.dispatchTouchEvent() -> ...
 *              问题：子View不消费事件，如何将此事件传递给父View的？
*/
public class EventActivity extends AppCompatActivity {

    private final String TAG = "EventActivity";

    private LinearLayout linearLayout;
    private Button button;
    private Button button1;


    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);
        linearLayout = findViewById(R.id.linearlayout);
        button = findViewById(R.id.button);
        button1 = findViewById(R.id.button1);

        button.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        if (true){
                            Log.d(TAG,"onTouch() Action Down");
                            return true;
                        }

                        break;
                    case MotionEvent.ACTION_UP:
                        Log.d(TAG,"onTouch() Action Up");
                        break;
                }
                Log.d(TAG,"onTouch() execute " + event.getAction());
                return true;
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG,"onClick() execute ");
            }
        });



        linearLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Log.d(TAG,"you touched linearlayout");
                return false;
            }
        });

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG,"you clicked button1");
            }
        });
    }
}
