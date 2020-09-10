package com.greymatter.miner.layouts;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
//TODO: Complete the adapter class for inventory stuff

public class TabAdapter extends FragmentStatePagerAdapter {
    private ArrayList<Fragment> fragmentList;
    private ArrayList<String> fragmentTitleList;
    public TabAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
        fragmentList = new ArrayList<>();
        fragmentTitleList = new ArrayList<>();
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }
}
