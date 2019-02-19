package com.boostcamp.dreampicker.presentation.profile;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.boostcamp.dreampicker.R;
import com.boostcamp.dreampicker.databinding.FragmentProfileBinding;
import com.boostcamp.dreampicker.di.scope.UserId;
import com.boostcamp.dreampicker.presentation.BaseFragment;
import com.boostcamp.dreampicker.presentation.main.MainActivity;
import com.boostcamp.dreampicker.presentation.main.SplashActivity;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;

public class ProfileFragment extends BaseFragment<FragmentProfileBinding> {
    @Inject
    ProfileViewModelFactory factory;
    @Inject
    Context context;
    @Inject
    MainActivity activity;
    @UserId
    String userId;

    @Inject
    public ProfileFragment() {
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_profile;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViewModel();
        initRecyclerView();
        binding.getVm().loadMyFeeds(userId);
        binding.getVm().loadUserDetail(userId);
    }

    private void initViewModel() {
        final ProfileViewModel viewModel =
                ViewModelProviders.of(this, factory).get(ProfileViewModel.class);
        binding.setVm(viewModel);

        viewModel.getIsLoggedOut().observe(this, isLoggedOut -> {
            if (isLoggedOut) {
                startActivity(new Intent(context, SplashActivity.class));
                activity.finish();
            }
        });
    }

    private void initRecyclerView() {
        MyFeedAdapter adapter = new MyFeedAdapter(item ->
                binding.getVm().toggleVoteEnded(userId, item, !item.isEnded()), true);

        binding.rvProfileFeed.setAdapter(adapter);

        binding.getVm().getIsLoading().observe(this, isLoading -> {
            if (!isLoading) {
                adapter.notifyDataSetChanged();
            }
        });

        binding.swipeRefresh.setOnRefreshListener(() ->
                binding.getVm().loadMyFeeds(userId));
    }

}
