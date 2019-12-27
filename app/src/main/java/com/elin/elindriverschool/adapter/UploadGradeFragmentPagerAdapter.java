package com.elin.elindriverschool.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;


public class UploadGradeFragmentPagerAdapter extends FragmentPagerAdapter {
    private String[] mTabTitle = new String[]{"科目一", "科目二","科目三","科目四"};
    private ArrayList<Fragment> fragmentsList;

    public UploadGradeFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    public UploadGradeFragmentPagerAdapter(FragmentManager fm, ArrayList<Fragment> fragments) {
        super(fm);
        this.fragmentsList = fragments;
    }

    @Override
    public int getCount() {
        return fragmentsList.size();
    }

    @Override
    public Fragment getItem(int arg0) {
        return fragmentsList.get(arg0);
    }

    @Override
    public int getItemPosition(Object object) {
        return super.getItemPosition(object);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTabTitle[position];
    }
}
