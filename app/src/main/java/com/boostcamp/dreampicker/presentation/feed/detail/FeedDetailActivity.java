package com.boostcamp.dreampicker.presentation.feed.detail;

import android.graphics.Color;
import android.os.Bundle;

import com.boostcamp.dreampicker.R;
import com.boostcamp.dreampicker.databinding.ActivityFeedDetailBinding;
import com.boostcamp.dreampicker.presentation.BaseActivity;

public class FeedDetailActivity extends BaseActivity<ActivityFeedDetailBinding> {
    private static final String TOOLBAR_BG_COLOR = "#99000000";

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
    }

    private void initViewPager() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_feed_detail;
    }
}
