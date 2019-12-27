package com.chanven.lib.cptr;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewConfiguration;

import com.chanven.lib.cptr.loadmore.DefaultLoadMoreViewFooter;
import com.chanven.lib.cptr.loadmore.ILoadMoreViewFactory;

public class PtrClassicFrameLayout extends PtrFrameLayout {

    private int mTouchSlop;
    // 上一次触摸时的X坐标
    private float mPrevX;

    private PtrClassicDefaultHeader mPtrClassicHeader;

    public PtrClassicFrameLayout(Context context) {
        super(context);
        initViews();
    }

    public PtrClassicFrameLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initViews();
    }

    public PtrClassicFrameLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initViews();
    }

    private void initViews() {
        mPtrClassicHeader = new PtrClassicDefaultHeader(getContext());
        setHeaderView(mPtrClassicHeader);
        addPtrUIHandler(mPtrClassicHeader);

        ILoadMoreViewFactory loadMoreViewFactory = new DefaultLoadMoreViewFooter();
        setFooterView(loadMoreViewFactory);
    }

    public PtrClassicDefaultHeader getHeader() {
        return mPtrClassicHeader;
    }

    /**
     * Specify the last update time by this key string
     *
     * @param key
     */
    public void setLastUpdateTimeKey(String key) {
        if (mPtrClassicHeader != null) {
            mPtrClassicHeader.setLastUpdateTimeKey(key);
        }
    }

    /**
     * Using an object to specify the last update time.
     *
     * @param object
     */
    public void setLastUpdateTimeRelateObject(Object object) {
        if (mPtrClassicHeader != null) {
            mPtrClassicHeader.setLastUpdateTimeRelateObject(object);
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return super.onInterceptTouchEvent(ev);
    }
}
