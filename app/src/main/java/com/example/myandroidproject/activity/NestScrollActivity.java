package com.example.myandroidproject.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ScrollView;

import com.example.myandroidproject.R;

//ScrollView嵌套ListView测试
public class NestScrollActivity extends AppCompatActivity {
    private ListView listView;
   // private ListScrollView scrollView;
    private MyScrollView scrollView;
    private String[] citys = {"北京","上海","广州","北京","上海","广州","北京","上海","广州","北京","上海","广州"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nest_scroll);
        listView = findViewById(R.id.listview);
        scrollView = findViewById(R.id.scrollview);
        listView.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,citys));
        //scrollView.setListView(listView);
    }
}
