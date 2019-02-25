LOCAL_PATH := $(call my-dir)

#x264 静态库
include $(CLEAR_VARS)
LOCAL_MODULE := x264
#指定头文件
LOCAL_EXPORT_C_INCLUDES := $(LOCAL_PATH)/x264/include
LOCAL_SRC_FILES := x264/libx264.a
include $(PREBUILT_STATIC_LIBRARY)
#预编译


#faac
include $(CLEAR_VARS)
LOCAL_MODULE := faac
LOCAL_EXPORT_C_INCLUDES := $(LOCAL_PATH)/faac/include
LOCAL_SRC_FILES := faac/libfaac.a
#把编译的源文件打印处理
$(info $(LOCAL_SRC_FILES))
include $(PREBUILT_STATIC_LIBRARY)

#rtmpdump
include $(CLEAR_VARS)
LOCAL_MODULE    := rtmpdump
LOCAL_EXPORT_C_INCLUDES := $(LOCAL_PATH)/rtmpdump/include
LOCAL_SRC_FILES := rtmpdump/librtmp.a
$(info $(LOCAL_SRC_FILES))
include $(PREBUILT_STATIC_LIBRARY)

include $(CLEAR_VARS)
LOCAL_MODULE := live
LOCAL_SRC_FILES := live.c
LOCAL_STATIC_LIBRARIES := x264 faac rtmpdump
LOCAL_LDLIBS := -llog
include $(BUILD_SHARED_LIBRARY)
