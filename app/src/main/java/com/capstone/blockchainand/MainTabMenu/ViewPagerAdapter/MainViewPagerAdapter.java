package com.capstone.blockchainand.MainTabMenu.ViewPagerAdapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.capstone.blockchainand.MainTabMenu.MenuFragements.AssetMenuFragment;
import com.capstone.blockchainand.MainTabMenu.MenuFragements.InfoMenuFragment;
import com.capstone.blockchainand.MainTabMenu.MenuFragements.UsageMenuFragment;

public class MainViewPagerAdapter extends FragmentPagerAdapter {

    public MainViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment presentFragment = null;
        switch (position) {
            case 0:
                presentFragment = new UsageMenuFragment();
                break;
            case 1:
                presentFragment = new AssetMenuFragment();
                break;
            case 2:
                presentFragment = new InfoMenuFragment();
                break;
        }
        if(presentFragment == null) {
            presentFragment = new UsageMenuFragment();
        }
        return presentFragment;
    }

    @Override
    public int getCount() {
        return 3;
    }
}
