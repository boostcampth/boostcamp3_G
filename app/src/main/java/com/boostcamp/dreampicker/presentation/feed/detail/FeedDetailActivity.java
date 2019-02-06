package com.boostcamp.dreampicker.presentation.feed.detail;

import android.graphics.Color;
import android.os.Bundle;

import com.boostcamp.dreampicker.R;
import com.boostcamp.dreampicker.databinding.ActivityFeedDetailBinding;
import com.boostcamp.dreampicker.presentation.BaseActivity;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.PagerAdapter;

public class FeedDetailActivity extends BaseActivity<ActivityFeedDetailBinding> {
    private static final String TOOLBAR_BG_COLOR = "#99000000";
    private static final int NUM_PAGES = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViews();
    }

    private void initViews() {
        initToolbar();
        initViewPager();
    }

    private void initToolbar() {
        binding.toolbar.rlToolbarBackground.setBackgroundColor(Color.parseColor(TOOLBAR_BG_COLOR));
        binding.toolbar.btnLeft.setImageResource(R.drawable.ic_keyboard_arrow_left_white);
        binding.toolbar.btnRight.setImageResource(R.drawable.ic_file_download_white);

        binding.toolbar.btnLeft.setOnClickListener((v) -> finish());
    }

    private void initViewPager() {
        final PagerAdapter pagerAdapter =  new FeedDetailPagerAdapter(getSupportFragmentManager());
        binding.pager.setAdapter(pagerAdapter);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_feed_detail;
    }

    private class FeedDetailPagerAdapter extends FragmentStatePagerAdapter {
        private Fragment[] fragments = new Fragment[2];

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
}
