package com.boostcamp.dreampicker.presentation.feed.detail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.boostcamp.dreampicker.R;
import com.boostcamp.dreampicker.databinding.ActivityFeedDetailBinding;
import com.boostcamp.dreampicker.presentation.BaseActivity;

import javax.inject.Inject;
import javax.inject.Named;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

public class FeedDetailActivity extends BaseActivity<ActivityFeedDetailBinding> {

    private static final int NUM_PAGES = 2;

    public static final String EXTRA_FEED_ID = "EXTRA_FEED_ID";
    public static final String EXTRA_IMAGEURL_A = "EXTRA_IMAGEURL_A";
    public static final String EXTRA_IMAGEURL_B = "EXTRA_IMAGEURL_B";

    @Inject
    @Named("imageA")
    String imageUrlA;
    @Inject
    @Named("imageB")
    String imageUrlB;
    @Inject
    FeedDetailViewModelFactory factory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViewModel();
        initViews();
        subscribeViewModel();
        loadFeedDetail();
    }

    private void initViews() {
        initToolbar();
        initViewPager(imageUrlA, imageUrlB);
        initVoteButton();
    }

    private void initViewModel() {
        final FeedDetailViewModel vm =
                ViewModelProviders.of(this, factory).get(FeedDetailViewModel.class);
        binding.setVm(vm);
    }

    private void initToolbar() {
        setSupportActionBar(binding.toolbar);
        ActionBar toolbar = getSupportActionBar();
        if (toolbar != null) {
            toolbar.setDisplayHomeAsUpEnabled(true);
            toolbar.setHomeAsUpIndicator(R.drawable.btn_toolbar_close);
            toolbar.setDisplayShowTitleEnabled(false);
        }
    }

    private void initVoteButton() {
        binding.btnFeedDetailVote.setOnClickListener(__ -> binding.getVm().vote());
    }

    private void initViewPager(@NonNull String imageUrlA, @NonNull String imageUrlB) {
        final Fragment[] fragments = new Fragment[NUM_PAGES];
        fragments[0] = FeedDetailFragment.newInstance(imageUrlA);
        fragments[1] = FeedDetailFragment.newInstance(imageUrlB);
        final PagerAdapter pagerAdapter = new FeedDetailPagerAdapter(getSupportFragmentManager(), fragments);
        binding.pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                binding.getVm().changePosition();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        binding.pager.setAdapter(pagerAdapter);
    }

    private void subscribeViewModel() {
        binding.getVm().getIsLoading().observe(this, isLoading -> {
            if (isLoading) {
                binding.loading.setVisibility(View.VISIBLE);
            } else {
                binding.loading.setVisibility(View.GONE);
            }
        });
    }

    private void loadFeedDetail() {
        binding.getVm().loadFeedDetail();
    }

    private void showToast(String msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }

    private void closeActivity() {
        final Intent intent = new Intent();
        setResult(RESULT_OK, intent);
        finish();
    }

    public static Intent getLaunchIntent(@NonNull Context context,
                                         @NonNull String feedId,
                                         @NonNull String imageUrlA,
                                         @NonNull String imageUrlB) {

        final Intent intent = new Intent(context, FeedDetailActivity.class);
        intent.putExtra(EXTRA_FEED_ID, feedId);
        intent.putExtra(EXTRA_IMAGEURL_A, imageUrlA);
        intent.putExtra(EXTRA_IMAGEURL_B, imageUrlB);
        return intent;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_feed_detail;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            closeActivity();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        closeActivity();
    }
}
