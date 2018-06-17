package com.example.liranyehudar.emojimemorygame;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class SectionPageAdapter extends FragmentPagerAdapter {
    List<Fragment> framentList = new ArrayList<Fragment>();
    List<String> fragmentTitle = new ArrayList<String>();

    public void addFragment(Fragment f, String title) {
        framentList.add(f);
        fragmentTitle.add(title);
    }

    public SectionPageAdapter(FragmentManager fm) {
        super(fm);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return fragmentTitle.get(position);
    }

    @Override
    public Fragment getItem(int position) {
        return framentList.get(position);
    }

    @Override
    public int getCount() {
        return framentList.size();
    }
}
