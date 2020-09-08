package com.ts.tab;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.PagerAdapter;

import java.util.List;

public class ViewPagerManager extends FragmentPagerAdapter {

    private List<Fragment> mFragmentList;
    private List<String> mTitleList;
    public ViewPagerManager(@NonNull FragmentManager fm, int behavior,List<Fragment> fragmentListist,List<String> titleList) {
        super(fm, behavior);
        this.mFragmentList = fragmentListist;
        this.mTitleList = titleList;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {

        return mFragmentList.get(position);
    }

    @Override
    public int getCount() {

        return mFragmentList.size();
    }
    @Override
    public CharSequence getPageTitle(int position) {
        return mTitleList.get(position);
    }
}
