package com.example.myandroidproject.hotfix;

import android.app.IntentService;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
*@author 杜立茂
*@date 2018/12/23 9:23
*@description 热修复：
 *              dex分包：65536
*/
public class Main {

    public static void main(String[] args){
        new MyAsyncTask().execute("url");
    }


}

class MyAsyncTask extends AsyncTask<String,Integer,Void>{

    @Override
    protected Void doInBackground(String... strings) {
        return null;
    }
}
