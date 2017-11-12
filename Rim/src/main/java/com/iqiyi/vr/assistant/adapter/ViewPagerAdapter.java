package com.iqiyi.vr.assistant.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by wangyancong on 2017/8/24.
 * ViewPager适配器
 */

public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    List<String> titles;
    List<Fragment> fragments;

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
        fragments = new ArrayList<>();
        titles = new ArrayList<>();
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (titles == null || titles.size() == 0)
            return null;
        return titles.get(position);
    }

    @Override
    public int getItemPosition(Object object) {
        return PagerAdapter.POSITION_NONE;
    }

    public void setItems(List<Fragment> fragments, List<String> mTitles) {
        this.fragments = fragments;
        this.titles = mTitles;
        notifyDataSetChanged();
    }

    public void setItems(List<Fragment> fragments, String[] mTitles) {
        this.fragments = fragments;
        this.titles = Arrays.asList(mTitles);
        notifyDataSetChanged();
    }

    public void addItem(Fragment fragment, String title) {
        fragments.add(fragment);
        titles.add(title);
        notifyDataSetChanged();
    }

    public void delItem(int position) {
        titles.remove(position);
        fragments.remove(position);
        notifyDataSetChanged();
    }

    public int delItem(String title) {
        int index = titles.indexOf(title);
        if (index != -1) {
            delItem(index);
        }
        return index;
    }

    public void swapItems(int fromPos, int toPos) {
        Collections.swap(titles, fromPos, toPos);
        Collections.swap(fragments, fromPos, toPos);
        notifyDataSetChanged();
    }

    public void modifyTitle(int position, String title) {
        titles.set(position, title);
        notifyDataSetChanged();
    }
}
