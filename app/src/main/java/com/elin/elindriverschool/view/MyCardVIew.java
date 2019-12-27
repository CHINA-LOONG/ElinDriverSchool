package com.elin.elindriverschool.view;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by 17535 on 2017/10/11.
 */

public class MyCardVIew extends CardView {
    public MyCardVIew(Context context) {
        super(context);
    }

    public MyCardVIew(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {

//        return super.onInterceptTouchEvent(ev);
        return true;
    }
}
