package com.example.myandroidproject.ndk.opengl_es.render;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.GLU;
import android.opengl.GLUtils;
import android.util.Log;

import com.example.myandroidproject.R;

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
*@description 纹理贴图
*/
public class TextureRender implements GLSurfaceView.Renderer {

    private Context mContext;

    public TextureRender(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        gl.glClearColor(0f,0f,0f,1f);
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
        gl.glEnableClientState(GL10.GL_COLOR_ARRAY);
        gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);

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
        //1、生成纹理
        gl.glEnable(GL10.GL_TEXTURE_2D);
        int[] texID = new int[1];
        //生成纹理ID
         gl.glGenTextures(1,texID,0);
        //绑定纹理
         gl.glBindTexture(GL10.GL_TEXTURE_2D,texID[0]);

        //2、加载纹理
        Bitmap bitmap = BitmapFactory.decodeResource(mContext.getResources(), R.mipmap.ic_launcher);
        GLUtils.texImage2D(GL10.GL_TEXTURE_2D,0,bitmap,0);
        bitmap.recycle();

        //3、设置纹理过滤参数
        gl.glTexParameterx(GL10.GL_TEXTURE_2D,GL10.GL_TEXTURE_MAG_FILTER,GL10.GL_LINEAR);
        gl.glTexParameterx(GL10.GL_TEXTURE_2D,GL10.GL_TEXTURE_MIN_FILTER,GL10.GL_LINEAR);
        gl.glTexParameterx(GL10.GL_TEXTURE_2D,GL10.GL_TEXTURE_WRAP_S,GL10.GL_REPEAT);
        gl.glTexParameterx(GL10.GL_TEXTURE_2D,GL10.GL_TEXTURE_WRAP_T,GL10.GL_REPEAT);



        //纹理坐标
        float textureCoodrs[] = {
                0,0,
                1,0,
                0,1,
                1,1
        };

        ByteBuffer ibb = ByteBuffer.allocateDirect(textureCoodrs.length * 4);
        ibb.order(ByteOrder.nativeOrder());
        FloatBuffer fbb = ibb.asFloatBuffer();
        fbb.put(textureCoodrs);
        fbb.position(0);

        //指定纹理坐标
        gl.glTexCoordPointer(2,GL10.GL_FLOAT,0,fbb);

        float rectCoords[] = {
                0f,0.5f,0f,
                -0.5f,0f,0f,
                0.5f,0f,0f,
        };

        ByteBuffer ibbs = ByteBuffer.allocateDirect(rectCoords.length * 4);
        ibbs.order(ByteOrder.nativeOrder());
        FloatBuffer fbbs = ibbs.asFloatBuffer();
        fbbs.put(rectCoords);
        fbbs.position(0);
        gl.glVertexPointer(3,GL10.GL_FLOAT,0,fbbs);

        gl.glDrawArrays(GL10.GL_TRIANGLES,0,rectCoords.length / 3);




    }
}
