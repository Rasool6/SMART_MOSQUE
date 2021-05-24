package com.variable.smartmosque;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

class TabAdapter extends FragmentPagerAdapter {
    int tabcount;

    public TabAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
        tabcount=behavior;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position)
        {
            case 0: return new IslamicInfo();
            case 1: return new Mosques();
            default:  return null;
        }

    }

    @Override
    public int getCount() {
        return tabcount;
    }
}
