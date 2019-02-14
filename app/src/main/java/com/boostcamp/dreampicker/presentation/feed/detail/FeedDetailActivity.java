package com.boostcamp.dreampicker.presentation.feed.detail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.boostcamp.dreampicker.R;
import com.boostcamp.dreampicker.databinding.ActivityFeedDetailBinding;
import com.boostcamp.dreampicker.presentation.BaseActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

public class FeedDetailActivity extends BaseActivity<ActivityFeedDetailBinding> {
    private static final int NUM_PAGES = 2;

    private static final String EXTRA_FEED_ID = "EXTRA_FEED_ID";

    private boolean isShowTag = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViews();
    }

    private void initViews() {
        initToolbar();
        initViewPager();

        binding.tgFeedDetailTag.setTags("Assb", "SDS", "QQQQ","AASSDAS");
    }

    private void initToolbar() {
        setSupportActionBar(binding.toolbar);
        ActionBar toolbar = getSupportActionBar();
        if(toolbar != null) {
            toolbar.setDisplayHomeAsUpEnabled(true);
            toolbar.setHomeAsUpIndicator(R.drawable.ic_keyboard_arrow_left_white);
            toolbar.setDisplayShowTitleEnabled(false);
        }
    }

    private void initViewPager() {
        final PagerAdapter pagerAdapter =  new FeedDetailPagerAdapter(getSupportFragmentManager());
        binding.pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }
            @Override
            public void onPageSelected(int position) {
                if(position == 0) {
                    binding.viewDetailPage1.setBackgroundResource(R.drawable.ic_radio_button_checked_white_8dp);
                    binding.viewDetailPage2.setBackgroundResource(R.drawable.ic_radio_button_unchecked_white_8dp);
                } else {
                    binding.viewDetailPage1.setBackgroundResource(R.drawable.ic_radio_button_unchecked_white_8dp);
                    binding.viewDetailPage2.setBackgroundResource(R.drawable.ic_radio_button_checked_white_8dp);
                }
            }
            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        binding.pager.setAdapter(pagerAdapter);
    }

    public void onTagToggleButtonClick(View view) {
        if(isShowTag) {
            binding.tgFeedDetailTag.setVisibility(View.GONE);
            binding.tvDetailTagToggle.setCompoundDrawablesWithIntrinsicBounds(
                    R.drawable.ic_keyboard_arrow_up_white, 0, 0, 0);
        } else {
            binding.tgFeedDetailTag.setVisibility(View.VISIBLE);
            binding.tvDetailTagToggle.setCompoundDrawablesWithIntrinsicBounds(
                    R.drawable.ic_keyboard_arrow_down_white, 0, 0, 0);
        }
        isShowTag = !isShowTag;
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

    public static Intent getLaunchIntent(@NonNull Context context, @NonNull String feedId) {
        final Intent intent = new Intent(context, FeedDetailActivity.class);
        intent.putExtra(EXTRA_FEED_ID, feedId);
        return intent;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
