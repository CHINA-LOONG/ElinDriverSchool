package com.elin.elindriverschool.activity;


import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContentResolverCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.elin.elindriverschool.R;
import com.elin.elindriverschool.fragment.TabContentFragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import butterknife.Bind;
import butterknife.ButterKnife;


public class AppointmentActivity extends BaseActivity {


    private List<String> tabIndicators= Arrays.asList("Tab1","Tab2");

    @Bind(R.id.tl_tab)
    private TabLayout mTabTl;
    @Bind(R.id.vp_content)
    private ViewPager mContentVp;

    private List<Fragment> tabFragments;
    private ContentPagerAdapter contentAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment);
        ButterKnife.bind(this);


//        mTabTl = (TabLayout) findViewById(R.id.tl_tab);
//        mContentVp = (ViewPager) findViewById(R.id.vp_content);

//        initContent();
//        initTab();
    }

    private void initTab(){
        mTabTl.setTabMode(TabLayout.MODE_FIXED);
//        mTabTl.setSelectedTabIndicatorHeight(0);
//        mTabTl.setTabTextColors(ContextCompat.getColor(this,R.color.bg_gray),ContextCompat.getColor(this,R.color.white));
        ViewCompat.setElevation(mTabTl,10);
        mTabTl.setupWithViewPager(mContentVp);
    }

    private void initContent(){
        tabFragments = new ArrayList<>();
        //根据页签创建页面
        for (String s : tabIndicators) {
            tabFragments.add(TabContentFragment.newInstance(s));
        }
        contentAdapter = new ContentPagerAdapter(getSupportFragmentManager());
        mContentVp.setAdapter(contentAdapter);
    }


    class ContentPagerAdapter extends FragmentPagerAdapter{
        public ContentPagerAdapter(FragmentManager fm){
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return tabFragments.get(position);
        }

        @Override
        public int getCount() {
            return tabIndicators.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return tabIndicators.get(position);
        }
    }
}
