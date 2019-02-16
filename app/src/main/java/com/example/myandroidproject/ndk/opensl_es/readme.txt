OpenSl_ES:音频播放，可以结合ffmpeg或transcode进行解码
两大核心内容：
1、Object对象：任务的抽象资源集合
2、Interface接口：对象提供的功能的抽象集合


OpenSL ES（Open Sound Library embedded system）

$(call import-module, transcode-1.1.7/avilib) 配置
1.C/C++ build->Environment
添加环境变量：
名称：NDK_MODULE_PATH
路径：E:\dongnao\openclass\ndk-wav\

Object create
Interface getInterface


transcode 解码wav音频文件，解码得到音频数据给OpenSL播放