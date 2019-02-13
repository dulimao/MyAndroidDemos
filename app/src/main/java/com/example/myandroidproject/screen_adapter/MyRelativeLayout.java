package com.example.myandroidproject.screen_adapter;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;

public class MyRelativeLayout extends RelativeLayout {

    private boolean isLayout = true;//防止onMeause二次测量，导致布局缩放两次

    public MyRelativeLayout(Context context) {
        this(context, null);
    }

    public MyRelativeLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (isLayout) {
            float scaleX = UIUtil.getInstance(getContext()).getHorizontalScaleRatio();
            float scaleY = UIUtil.getInstance(getContext()).getVerticalScaleRatio();
            int childCount = getChildCount();
            for (int i = 0; i < childCount; i++) {
                View view = getChildAt(i);
                LayoutParams params = (LayoutParams) view.getLayoutParams();
                params.width = (int) (params.width * scaleX);
                params.height = (int) (params.height * scaleY);
                params.leftMargin = (int) (params.leftMargin * scaleX);
                params.rightMargin = (int) (params.rightMargin * scaleX);
                params.topMargin = (int) (params.topMargin * scaleY);
                params.bottomMargin = (int) (params.bottomMargin * scaleY);
            }
            isLayout = false;
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
