package com.example.myandroidproject.activity;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ListView;
import android.widget.ScrollView;
//外部拦截法
public class ListScrollView extends ScrollView {

    private ListView listView;
    public ListScrollView(Context context) {
        super(context);
    }

    public ListScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ListScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    /**
     * 点击操作发生在listview区域内时，返回false让scrollview的onTouchEvent方法接受不到MotionEvent,
     * 而是把事件传递到下一级listview中处理
     * @param ev
     * @return
     */
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (listView != null && checkArea(listView,ev)){
            return false;
        }
        return super.onInterceptTouchEvent(ev);
    }

    /**
     *  测试view是否在点击范围内
     * @param
     * @param v
     * @return
     */
    private boolean checkArea(View v, MotionEvent event){
        float x = event.getRawX();
        float y = event.getRawY();
        int[] locate = new int[2];
        v.getLocationOnScreen(locate);
        int l = locate[0];
        int r = l + v.getWidth();
        int t = locate[1];
        int b = t + v.getHeight();
        if (l < x && x < r && t < y && y < b) {
            return true;
        }
        return false;
    }

    public void setListView(ListView listView){
        this.listView = listView;
    }

    public ListView getListView(){
        return listView;
    }
}
