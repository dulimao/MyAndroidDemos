#include <jni.h>
#include <string>

extern "C" JNIEXPORT void JNICALL
Java_com_example_myandroidproject_ndk_patch_update_Main_patch(
        JNIEnv *env,
        jobject /* this */,
        jstring oldfile, jstring newfile, jstring patchfile) {


}
