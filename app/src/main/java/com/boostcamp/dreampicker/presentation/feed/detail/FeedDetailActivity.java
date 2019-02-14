package com.boostcamp.dreampicker.presentation.feed.detail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.util.Log;
import android.view.View;

import com.boostcamp.dreampicker.R;
import com.boostcamp.dreampicker.data.local.room.AppDatabase;
import com.boostcamp.dreampicker.data.model.FeedDetail;
import com.boostcamp.dreampicker.data.repository.FeedRepositoryImpl;
import com.boostcamp.dreampicker.databinding.ActivityFeedDetailBinding;
import com.boostcamp.dreampicker.presentation.BaseActivity;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

public class FeedDetailActivity extends BaseActivity<ActivityFeedDetailBinding> {
    private static final int NUM_PAGES = 2;

    private static final String EXTRA_FEED_ID = "EXTRA_FEED_ID";

    private boolean isShowTag = true;

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
        binding.btnFeedDetailVote.setOnClickListener(__ -> binding.getVm().vote());
    }

    private void initViewModel() {
        final FeedDetailViewModel vm = ViewModelProviders.of(this,
                new FeedDetailViewModelFactory(FeedRepositoryImpl.getInstance(
                        FirebaseFirestore.getInstance(),
                        FirebaseStorage.getInstance(),
                        AppDatabase.getDatabase(getApplicationContext()).votedFeedDao())))
                .get(FeedDetailViewModel.class);

        binding.setLifecycleOwner(this);
        binding.setVm(vm);
        binding.getVm().getFeedDetail().observe(this, this::initViewPager);
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

    private void initViewPager(@NonNull final FeedDetail feedDetail) {
        final Fragment[] fragments = new Fragment[2];
        fragments[0] = FeedDetailFragmentA.newInstance(feedDetail.getItemA().getImageUrl());
        fragments[1] = FeedDetailFragmentB.newInstance(feedDetail.getItemB().getImageUrl());
        final PagerAdapter pagerAdapter = new FeedDetailPagerAdapter(getSupportFragmentManager(), fragments);
        binding.pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 0) {
                    binding.viewDetailPage1.setBackgroundResource(R.drawable.ic_radio_button_checked_black_8dp);
                    binding.viewDetailPage2.setBackgroundResource(R.drawable.ic_radio_button_unchecked_black_8dp);
                    binding.getVm().changePosition();
                } else {
                    binding.viewDetailPage1.setBackgroundResource(R.drawable.ic_radio_button_unchecked_black_8dp);
                    binding.viewDetailPage2.setBackgroundResource(R.drawable.ic_radio_button_checked_black_8dp);
                    binding.getVm().changePosition();
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        binding.pager.setAdapter(pagerAdapter);
    }

    public void onTagToggleButtonClick(View view) {
        if (isShowTag) {
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

    private void subscribeViewModel() {
        binding.getVm().getIsLoading().observe(this, isLoading -> {
            if(isLoading) {
                binding.loading.setVisibility(View.VISIBLE);
            } else {
                binding.loading.setVisibility(View.GONE);
            }
        });
    }
    private void loadFeedDetail(){
        binding.getVm().loadFeedDetail(getIntent().getStringExtra(EXTRA_FEED_ID));
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_feed_detail;
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
