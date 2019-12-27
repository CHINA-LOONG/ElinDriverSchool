package com.elin.elindriverschool.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.elin.elindriverschool.R;
import com.elin.elindriverschool.adapter.ViewPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by imac on 16/12/29.
 */

public class WelcomeActivity extends Activity implements ViewPager.OnPageChangeListener {
    private ViewPager vp;
    private ViewPagerAdapter adapter;
    private List<View> views;
    private ImageView[] dots;
    private int currentIndex;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        initView();
        initDot();
    }

    private void initView() {
        LayoutInflater inflater = LayoutInflater.from(getApplicationContext());
        views = new ArrayList<View>();

        views.add(inflater.inflate(R.layout.welcome_one, null));
        views.add(inflater.inflate(R.layout.welcome_two, null));
        views.add(inflater.inflate(R.layout.welcome_three, null));


        adapter = new ViewPagerAdapter(views, this);
        vp = (ViewPager) findViewById(R.id.vp_welcome);
        vp.setAdapter(adapter);

        vp.setOnPageChangeListener(this);
    }


    private void initDot(){
        LinearLayout dotLayout = (LinearLayout) findViewById(R.id.ll_dot);
        dots = new ImageView[views.size()];

        for (int i = 0; i < views.size(); i++) {
            dots[i] = (ImageView) dotLayout.getChildAt(i);
            dots[i].setEnabled(true);

        }
        currentIndex = 0;
        dots[currentIndex].setEnabled(false);
    }
    private void setCurrentDot(int position){
        if (position < 0||position>views.size()-1|| currentIndex == position) {
            return;
        }
        dots[position].setEnabled(false);
        dots[currentIndex].setEnabled(true);
        currentIndex = position;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        setCurrentDot(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
