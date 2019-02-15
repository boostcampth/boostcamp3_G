package com.boostcamp.dreampicker.presentation.profile;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.boostcamp.dreampicker.R;
import com.boostcamp.dreampicker.data.Injection;
import com.boostcamp.dreampicker.data.common.FirebaseManager;
import com.boostcamp.dreampicker.databinding.ActivityProfileBinding;
import com.boostcamp.dreampicker.presentation.BaseActivity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.lifecycle.ViewModelProviders;

public class ProfileActivity extends BaseActivity<ActivityProfileBinding> {
    private static final String EXTRA_USER_ID = "EXTRA_USER_ID";

    public static Intent getLaunchIntent(@NonNull Context context,
                                         @NonNull String userId) {
        Intent intent = new Intent(context, ProfileActivity.class);
        intent.putExtra(EXTRA_USER_ID, userId);
        return intent;
    }

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
        } else {
            final Intent intent = getIntent();
            if (intent != null) {
                userId = intent.getStringExtra(EXTRA_USER_ID);
            }
        }

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
        ProfileViewModel viewModel = ViewModelProviders.of(this,
                new ProfileViewModelFactory(Injection.provideUserRepository(), userId))
                .get(ProfileViewModel.class);
        binding.container.setVm(viewModel);
    }

    private void initRecyclerView() {
        MyFeedAdapter adapter = new MyFeedAdapter(item ->
                binding.container.getVm().toggleVoteEnded(item, !item.isEnded()),
                userId.equals(FirebaseManager.getCurrentUserId()));

        binding.container.rvProfileFeed.setAdapter(adapter);

        binding.container.getVm().getIsLoading().observe(this, isLoading -> {
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
