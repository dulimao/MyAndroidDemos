package com.example.myandroidproject.ndk.opengl_es.render;

import android.opengl.GLSurfaceView;
import android.opengl.GLU;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class MyRender implements GLSurfaceView.Renderer {


    //表层创建时
    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        //设置清屏色(背景色)
        gl.glClearColor(0f,0f,0f,1f);
        //启用顶点缓冲区
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);

    }

    //表层size时
    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        //设置视口
        gl.glViewport(0,0,width,height);

        //宽高比
        float ratio = (float)width / (float)height;

        //矩阵模式，投影矩阵，基于状态机
        gl.glMatrixMode(GL10.GL_PROJECTION);

        //加载单位矩阵
        gl.glLoadIdentity();

        //设置视景体 zNear:离眼睛最近的距离，zFar:离眼睛最远的距离
        gl.glFrustumf(-1f,1f,-ratio,ratio,3,7);
    }

    //绘制
    @Override
    public void onDrawFrame(GL10 gl) {

        gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
        //模型视图矩阵
        gl.glMatrixMode(GL10.GL_MODELVIEW);
        gl.glLoadIdentity();
        //eyeX,Y,Z:眼睛或相机坐标
        //centerX,Y,Z,朝向原点的就是观察的方向
        GLU.gluLookAt(gl,0,0,5,0,0,0,0,1,0);

        //画三角形
        //坐标
        float [] coodrs = {
                0f,0.5f,0f,
                -0.5f,0f,0f,
                0.5f,0f,0f,
        };
        //放入字节缓冲区，存放顶点坐标数据
        ByteBuffer ibb = ByteBuffer.allocateDirect(coodrs.length * 4);
        ibb.order(ByteOrder.nativeOrder());

        FloatBuffer fbb = ibb.asFloatBuffer();
        fbb.put(coodrs);
        //将指针恢复到初始位置，从该位置开始读取顶点数据
        fbb.position(0);

        gl.glColor4f(1f,0f,0f,1f);
        //size:使用3维点表示一个点
        //stride:跨度
        gl.glVertexPointer(3,GL10.GL_FLOAT,0,fbb);
        //绘制数组
        //count:顶点个数
        gl.glDrawArrays(GL10.GL_TRIANGLES,0,3);

    }
}
