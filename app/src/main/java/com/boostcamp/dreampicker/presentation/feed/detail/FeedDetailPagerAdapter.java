package com.boostcamp.dreampicker.presentation.feed.detail;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class FeedDetailPagerAdapter extends FragmentStatePagerAdapter {

    private static final int NUM_PAGES = 2;

    private Fragment[] fragments = new Fragment[NUM_PAGES];

    FeedDetailPagerAdapter(@NonNull FragmentManager fm) {
        super(fm);
        fragments[0] = FeedDetailLeftFragment.newInstance();
        fragments[1] = FeedDetailRightFragment.newInstance();
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return fragments[position];
    }

    @Override
    public int getCount() {
        return NUM_PAGES;
    }
}
