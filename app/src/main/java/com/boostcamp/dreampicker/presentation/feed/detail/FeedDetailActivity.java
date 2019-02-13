package com.boostcamp.dreampicker.presentation.feed.detail;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import com.boostcamp.dreampicker.R;
import com.boostcamp.dreampicker.data.local.room.AppDatabase;
import com.boostcamp.dreampicker.data.model.FeedDetail;
import com.boostcamp.dreampicker.data.repository.FeedRepositoryImpl;
import com.boostcamp.dreampicker.databinding.ActivityFeedDetailBinding;
import com.boostcamp.dreampicker.presentation.BaseActivity;
import com.boostcamp.dreampicker.presentation.feed.main.FeedViewModel;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

public class FeedDetailActivity extends BaseActivity<ActivityFeedDetailBinding> {

    private static final String TOOLBAR_BG_COLOR = "#99000000";
    private boolean isShowTag = true;

    private static final String EXTRA_FEED_ID = "EXTRA_FEED_ID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViewModel();
        initViews();
    }
    private void initViews() {
        initToolbar();
        initViewPager();

//        binding.tgFeedDetailTag.setTags("Assb", "SDS", "QQQQ","AASSDAS");
    }
    private void initViewModel() {
        final FeedDetailViewModel vm = ViewModelProviders.of(this,
                new FeedDetailViewModelFactory(FeedRepositoryImpl.getInstance(
                        FirebaseFirestore.getInstance(),
                        FirebaseStorage.getInstance(),
                        AppDatabase.getDatabase(getApplicationContext()).votedFeedDao())))
                .get(FeedDetailViewModel.class);

        binding.setVm(vm);
    }
    private void initToolbar() {
        binding.toolbar.rlToolbarBackground.setBackgroundColor(Color.parseColor(TOOLBAR_BG_COLOR));
        binding.toolbar.btnLeft.setImageResource(R.drawable.ic_keyboard_arrow_left_white);
        binding.toolbar.btnRight.setImageResource(R.drawable.ic_file_download_white);

        binding.toolbar.btnLeft.setOnClickListener((v) -> finish());
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
    protected int getLayoutId() { return R.layout.activity_feed_detail; }

    public static Intent getLaunchIntent(@NonNull Context context, @NonNull String feedId) {
        final Intent intent = new Intent(context, FeedDetailActivity.class);
        intent.putExtra(EXTRA_FEED_ID, feedId);
        return intent;
    }
}
