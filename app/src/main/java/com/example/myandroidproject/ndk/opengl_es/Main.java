package com.example.myandroidproject.ndk.opengl_es;
/**
 *
 *@author 杜立茂
 * 2019/2/7 15:51
 * OpenGL：开放图形库，使用C语言开发的函数库,动画的本质就是坐标变换
 *               OpenGLES:是OpenGL的一个子集，用于嵌入式系统
 *
 *
 *              笛卡尔坐标系
 *              状态机
 *              近平面和原平面：frustum,平截头体，视景体
 *              ViewPort:视口，画面输出的区域,坐标原点在左下角
 *              Matrix:
 *              投影：透视投影，越远越小
 *                    正投影：大小一样，没有深度的概念，平行投影
 *              纹理：
 *              渲染模式：
 *                  持续渲染
 *                  脏渲染(命令渲染)
 *              图元：
 *                  点：
 *                  线：
 *                      线集
 *                      线带
 *                      线环
 *                  三角形：
 *                      集
 *                      带(用的最多)
 *                      扇面
 *                  顶点着色模式：
 *                      1.smooth平滑模式
 *                      2.flat单调模式
 *                  深度测试：Z轴
 *                  深度缓冲区：深度值
 *                  深度测试：启用Z轴被遮挡的物体看不见
 *                  剔除：如果看不见的地方，告诉OopenGl不要渲染
 *                  剪裁：不在整个ViewPort内渲染，指定一个剪裁区，在区域内渲染
 *
 *                  环绕：顶点的指定次序以及方向的组合
 *
 *
 *                  OpenGL_ES教程：https://learnopengl-cn.github.io/#
 *                  C++教程：https://www.learncpp.com/
 *                  矩阵变换：https://mp.weixin.qq.com/s/con9UDxZ2tFuAw02uz3BsA
 *                  OpenGL全流程详解：https://mp.weixin.qq.com/s/B4GxcNz9bybC6aUcnclVLw
 *
 *
 */
public class Main {
}
