package com.boostcamp.dreampicker.presentation.profile;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.boostcamp.dreampicker.R;
import com.boostcamp.dreampicker.databinding.ActivityProfileBinding;
import com.boostcamp.dreampicker.presentation.BaseActivity;

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
    ProfileViewModelFactory factory;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_profile;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initToolbar();
        initViewModel();
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

    private void initViewModel() {
        ProfileViewModel viewModel =
                ViewModelProviders.of(this, factory).get(ProfileViewModel.class);
        binding.setVm(viewModel);
    }

    private void initRecyclerView() {
        MyFeedAdapter adapter = new MyFeedAdapter(item ->
                binding.getVm().toggleVoteEnded(item, !item.isEnded()), false);

        binding.rvProfileFeed.setAdapter(adapter);

        binding.getVm().getIsLoading().observe(this, isLoading -> {
            if (!isLoading) {
                adapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
