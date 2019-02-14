package com.boostcamp.dreampicker.presentation.profile;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.boostcamp.dreampicker.R;
import com.boostcamp.dreampicker.data.Injection;
import com.boostcamp.dreampicker.databinding.FragmentProfileBinding;
import com.boostcamp.dreampicker.presentation.BaseActivity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;

public class ProfileActivity extends BaseActivity<FragmentProfileBinding> {
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
        return R.layout.fragment_profile;
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

        initViewModel();
        initRecyclerView();
    }

    private void initViewModel() {
        ProfileViewModel viewModel = ViewModelProviders.of(this,
                new ProfileViewModelFactory(Injection.provideUserRepository(), userId))
                .get(ProfileViewModel.class);
        binding.setVm(viewModel);
    }

    private void initRecyclerView() {
        MyFeedAdapter adapter = new MyFeedAdapter(item ->
                binding.getVm().toggleVoteEnded(item, !item.isEnded()));
        binding.rvProfileFeed.setAdapter(adapter);

        // TODO. item 갱신
        binding.getVm().getIsLoading().observe(this, isLoading -> {
            if (!isLoading) {
                adapter.notifyDataSetChanged();
            }
        });
    }

}
