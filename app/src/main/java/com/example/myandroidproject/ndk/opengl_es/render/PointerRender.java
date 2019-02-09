package com.example.myandroidproject.ndk.opengl_es.render;

import android.opengl.GLSurfaceView;
import android.opengl.GLU;
import android.util.Log;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.List;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;


/**
*@author 杜立茂
*@date 2019/2/8 16:46
*@description 螺旋线渲染器
*/
public class PointerRender implements GLSurfaceView.Renderer {


    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        gl.glClearColor(0f,0f,0f,1f);
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);

    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {

        gl.glViewport(0,0,width,height);
        float ratio = (float) height / (float) width;
        gl.glMatrixMode(GL10.GL_PROJECTION);
        gl.glLoadIdentity();
        gl.glFrustumf(-1f,1f,-ratio,ratio,3f,7f);

    }

    @Override
    public void onDrawFrame(GL10 gl) {
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
        gl.glMatrixMode(GL10.GL_MODELVIEW);
        gl.glLoadIdentity();
        GLU.gluLookAt(gl,0f,0f,5f,0f,0f,0f,0f,1f,0f);

        gl.glColor4f(1f,0f,0f,1f);
        gl.glPointSize(10f);

        //坐标系按照x轴往里旋转90度
        gl.glRotatef(-40,1,0,0);

        List<Float> list = new ArrayList<>();

        //画三圈，总角度是6*PI
        double total = 6 * Math.PI;
        float r = 0.5f;
        float x = 0f;
        float y = 0f;
        float z = 1f;//坐标原点在平截头体中间，所以从外往里画
        float zStep = 0.01f;
        for (double a = 0; a < total; a = a + Math.PI / 32){
            x = (float) (r * Math.cos(a));
            y = (float) (r * Math.sin(a));
            z = z - zStep;
            list.add(x);
            list.add(y);
            list.add(z);
        }
        Log.e("PointerRender","list.size(): " + list.size());

        ByteBuffer ibb = ByteBuffer.allocateDirect(list.size() * 4);
        ibb.order(ByteOrder.nativeOrder());
        FloatBuffer fbb = ibb.asFloatBuffer();

        for (int i = 0; i < list.size(); i++){
            fbb.put(list.get(i));
        }
        fbb.position(0);
        gl.glVertexPointer(3, GL10.GL_FLOAT,0,fbb);

        gl.glDrawArrays(GL10.GL_POINTS,0,list.size() / 3);



    }
}
