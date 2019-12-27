package com.elin.elindriverschool.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;


public class MyFragmentPagerAdapter extends FragmentPagerAdapter {
    private ArrayList<Fragment> fragmentsList;
    private List<String> stringList;

    public MyFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    public MyFragmentPagerAdapter(FragmentManager fm, ArrayList<Fragment> fragments, List<String> stringList) {
        super(fm);
        this.fragmentsList = fragments;
        this.stringList = stringList;
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
        return stringList.get(position);
    }
}
