package com.example.myandroidproject.hotfix;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.euler.andfix.patch.PatchManager;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;


/**
*@author 杜立茂
*@date 2019/2/11 21:37
*@description 阿里AndFix:
 *                  1、新包和旧包生成差分包，
 *                  2、从服务器下载差分包到本地
 *                  3、修复bug
 *                  修改的地方加上替换注解
 *
 *  usage: apkpatch -f <new> -t <old> -o <output> -k <keystore> -p <***> -a <alias> -e <***>
 *  -a,--alias <alias>     keystore entry alias.
 *  -e,--epassword <***>   keystore entry password.
 *  -f,--from <loc>        new Apk file path.
 *  -k,--keystore <loc>    keystore path.
 *  -n,--name <name>       patch name.
 *  -o,--out <dir>         output dir.
 *  -p,--kpassword <***>   keystore password.
 *  -t,--to <loc>          old Apk file path.
 *
 *                腾旭：tinker
 *
 *
*/
public class TestBugActivity extends Activity {

    private PatchManager mPatchManager;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initPachManager();
    }

    private void initView(){
        LinearLayout linearLayout = new LinearLayout(this);
        Button testTv = new Button(this);
        testTv.setText("测试bug");
        testTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int a = 10;
                int b = 0;
                int c = a / b;
                Toast.makeText(TestBugActivity.this,"c的值：" + c,Toast.LENGTH_LONG).show();
            }
        });
        final Button fixbugBut = new Button(this);
        fixbugBut.setText("修复bug");
        fixbugBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fixBug();
            }
        });
        linearLayout.addView(testTv);
        linearLayout.addView(fixbugBut);
        setContentView(linearLayout);
    }



    //---------------------------阿里AndFix--------------------------------//

    private void initPachManager(){
        try {
            mPatchManager = new PatchManager(this);
            PackageManager packageManager = getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(getPackageName(),0);
            mPatchManager.init(packageInfo.versionName);
            mPatchManager.loadPatch();
        } catch (Exception e){
            e.printStackTrace();
        }
    }


    /**
     * 修复bug
     */
    private void fixBug(){
        try {
        File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/fix.apatch");
        if (file.exists()){
            //修复成功
            mPatchManager.addPatch(file.getPath());
            Toast.makeText(TestBugActivity.this,"修复成功",Toast.LENGTH_LONG).show();
        }else {
            Toast.makeText(TestBugActivity.this,"修复失败",Toast.LENGTH_LONG).show();
        }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //---------------------------------腾讯--------------------------------//



    private void fixBug1() {
        //目录：/data/data/packageName/odex
        File fileDir = getDir(MyConstants.DEX_DIR, Context.MODE_PRIVATE);
        //往该目录下面放置我们修复好的dex文件。
        String name = "classes2.dex";
        String filePath = fileDir.getAbsolutePath()+File.separator+name;
        File file= new File(filePath);
        if(file.exists()){
            file.delete();
        }
        //搬家：把下载好的在SD卡里面的修复了的classes2.dex搬到应用目录filePath
        InputStream is = null;
        FileOutputStream os = null;
        try {
            is = new FileInputStream(Environment.getExternalStorageDirectory().getAbsolutePath()+File.separator+name);
            os = new FileOutputStream(filePath);
            int len = 0;
            byte[] buffer = new byte[1024];
            while ((len=is.read(buffer))!=-1){
                os.write(buffer,0,len);
            }

            File f = new File(filePath);
            if(f.exists()){
                Toast.makeText(this	,"dex 重写成功", Toast.LENGTH_SHORT).show();
            }
            //热修复
            FixDexUtil.loadFixedDex(this);

        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
