//
// Created by Administrator on 2019/2/13.
//JNI中使用多线程
//
#include <jni.h>
#include <pthread.h>
#include <android/log.h>
#include <unistd.h>
#define LOGI(FORMAT,...) __android_log_print(ANDROID_LOG_INFO,"jason",FORMAT,##__VA_ARGS__);
#define LOGE(FORMAT,...) __android_log_print(ANDROID_LOG_ERROR,"jason",FORMAT,##__VA_ARGS__);

JavaVM *javaVM;

//子线程
void* fun(void* arg)
{
    //通过JavaVM关联当前线程，获取当前线程的JNIEnv
    JNIEnv *env = NULL;
    (*javaVM)->AttachCurrentThread(javaVM,&env,NULL);

    //拿到env, dosomething


    char* value = (char*) arg;
    int i = 0;
    for (; i < 10; i++)
    {
        LOGI("value: %s, i: %d",value,i);
    }
    //解除关联
    (*javaVM)->DetachCurrentThread(javaVM);

    pthread_exit(0);
}

//每个线程都有自己独立的JNIEnv，所以子线程不能用主线程的JNIEnv
// 可以通过JavaVM获取到每个线程相关联的JNIEnv
//如何获取？
//1.通过JNI_OnLoad()方法
//2.通过(*env)->GetJavaVM(env,&javaVM);
JNIEXPORT void JNICALL Java_com_example_myandroidproject_ndk_patch_1update_Main_jniPthreadTest(
        JNIEnv *env,jobject jobject1)
{
    //创建多线程
    pthread_t tid;
    pthread_create(&tid,NULL,fun,"demo");
    //(*env)->GetJavaVM(env,&javaVM);

}


//加载动态库的时候会执行
//JNI_VERSION_1_4 兼容Android SDK2.2之后，之前没有这个函数
JNIEXPORT jint JNI_OnLoad(JavaVM* vm, void* reserved)
{
    javaVM = vm;
    return JNI_VERSION_1_4;
}





