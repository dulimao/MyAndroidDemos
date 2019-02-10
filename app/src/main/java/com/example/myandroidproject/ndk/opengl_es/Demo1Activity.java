package com.example.myandroidproject.ndk.opengl_es;

import android.app.Activity;
import android.content.Context;
import android.opengl.GLSurfaceView;
import android.os.Bundle;

import com.example.myandroidproject.ndk.opengl_es.render.MyRender;
import com.example.myandroidproject.ndk.opengl_es.render.PointerRender;
import com.example.myandroidproject.ndk.opengl_es.render.TextureRender;

public class Demo1Activity extends Activity {

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        MyGLSurfaceView surfaceView = new MyGLSurfaceView(this);
        surfaceView.setRenderer(new TextureRender(this));
        surfaceView.setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
        setContentView(surfaceView);
    }

    class MyGLSurfaceView extends GLSurfaceView{

        public MyGLSurfaceView(Context context) {
            super(context);
        }
    }



}
