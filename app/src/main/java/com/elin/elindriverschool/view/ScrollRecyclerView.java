package com.elin.elindriverschool.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

/**
 * Created by 17535 on 2017/8/3.
 */

public class ScrollRecyclerView extends RecyclerView {
    public ScrollRecyclerView(Context context) {
        super(context);
    }

    public ScrollRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthSpec, int heightSpec) {
        int height = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE>>2,MeasureSpec.AT_MOST);
        super.onMeasure(widthSpec, height);
    }
}
