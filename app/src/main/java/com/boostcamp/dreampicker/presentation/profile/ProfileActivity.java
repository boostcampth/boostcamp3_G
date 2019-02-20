package com.boostcamp.dreampicker.presentation.profile;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.boostcamp.dreampicker.R;
import com.boostcamp.dreampicker.databinding.ActivityProfileBinding;
import com.boostcamp.dreampicker.presentation.BaseActivity;
import com.boostcamp.dreampicker.presentation.feed.detail.FeedDetailActivity;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.lifecycle.ViewModelProviders;

public class ProfileActivity extends BaseActivity<ActivityProfileBinding> {
    public static final String EXTRA_USER_ID = "EXTRA_USER_ID";

    public static Intent getLaunchIntent(@NonNull Context context,
                                         @NonNull String userId) {
        Intent intent = new Intent(context, ProfileActivity.class);
        intent.putExtra(EXTRA_USER_ID, userId);
        return intent;
    }

    @Inject
    Context context;

    @Inject
    ProfileViewModelFactory factory;

    private String userId;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_profile;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            userId = savedInstanceState.getString(EXTRA_USER_ID);
        } else if (getIntent() != null) {
            userId = getIntent().getStringExtra(EXTRA_USER_ID);
        }
        initViewModel();
        initViews();
        observeError();
    }

    private void initViewModel() {
        binding.setVm(ViewModelProviders.of(this, factory).get(ProfileViewModel.class));
        binding.getVm().loadUserDetail(userId);
        binding.getVm().loadMyFeeds(userId);
    }

    private void initViews() {
        initToolbar();
        initRecyclerView();
    }

    private void initToolbar() {
        setSupportActionBar(binding.toolbar);
        ActionBar toolbar = getSupportActionBar();
        if (toolbar != null) {
            toolbar.setDisplayHomeAsUpEnabled(true);
            toolbar.setDisplayShowTitleEnabled(false);
        }
    }

    private void initRecyclerView() {
        final MyFeedAdapter adapter = new MyFeedAdapter(
                item -> binding.getVm().toggleVoteEnded(userId, item, !item.isEnded()),
                this::startFeedDetailActivity,
                false);

        binding.content.rvProfileFeed.setAdapter(adapter);
        binding.content.swipeRefresh.setOnRefreshListener(() ->
                binding.getVm().loadMyFeeds(userId));

        binding.getVm().getIsLoading().observe(this, isLoading -> {
            if (!isLoading) {
                adapter.notifyDataSetChanged();
            }
        });
    }

    private void startFeedDetailActivity(String feedId, String imageUrlA, String imageUrlB) {
        startActivity(FeedDetailActivity.getLaunchIntent(context, feedId, imageUrlA, imageUrlB));
    }

    private void observeError() {
        binding.getVm().getError().observe(this, error ->
                showToast(getString(R.string.common_error_message, error.getMessage())));
    }

    private void showToast(String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
