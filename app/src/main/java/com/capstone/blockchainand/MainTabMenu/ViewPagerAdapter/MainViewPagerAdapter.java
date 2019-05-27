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
        switch (position) {
            case 0:
                return new UsageMenuFragment();
            case 1:
                return new AssetMenuFragment();
//            case 2:
//                return new InfoMenuFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 2;
    }
}
