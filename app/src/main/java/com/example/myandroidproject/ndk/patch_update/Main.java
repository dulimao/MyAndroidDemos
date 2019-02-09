package com.example.myandroidproject.ndk.patch_update;


import android.content.Context;

/**
*@author 杜立茂
*@date 2019/2/2 15:03
*@description 增量更新：
 *                  1、差分（跨版本差分）
 *                      旧版本，新版本
 *                  2、合并
 *                      从服务器下载差分包，和当前版本合并，得到新包后安装
*/
public class Main {

    //合并
    public native static void patch(String oldfile,String newfile,String patchfile);

    public static void merge(Context context){

        //获取当前应用的路径 data/app/xxx.apk
        String oldfile = ApkUtils.getSourceApkPath(context,context.getPackageName());

        System.getProperty("os.name");
        String newfile = "";
        String patchfile = "patch_apk.patch";

        //合并新包
        patch(oldfile,newfile,patchfile);

        //安装
        ApkUtils.installApk(context,newfile);

    }
}
