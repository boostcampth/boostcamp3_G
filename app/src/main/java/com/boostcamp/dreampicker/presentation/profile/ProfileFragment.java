package com.boostcamp.dreampicker.presentation.profile;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.boostcamp.dreampicker.R;
import com.boostcamp.dreampicker.databinding.FragmentProfileBinding;
import com.boostcamp.dreampicker.di.scope.UserId;
import com.boostcamp.dreampicker.presentation.BaseFragment;
import com.boostcamp.dreampicker.presentation.feed.detail.FeedDetailActivity;
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
    @Inject
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
        observeError();
    }

    private void initViewModel() {
        binding.setVm(ViewModelProviders.of(this, factory).get(ProfileViewModel.class));
        binding.getVm().init(userId);
        binding.getVm().getIsLoggedOut().observe(this, isLoggedOut -> {
            if (isLoggedOut) {
                startActivity(new Intent(context, SplashActivity.class));
                activity.finish();
            }
        });
    }

    private void initRecyclerView() {
        MyFeedAdapter adapter = new MyFeedAdapter(
                item -> binding.getVm().toggleVoteEnded(item, !item.isEnded()),
                this::startFeedDetailActivity,
                true);

        binding.content.rvProfileFeed.setAdapter(adapter);

        binding.getVm().getIsLoading().observe(this, isLoading -> {
            if (!isLoading) {
                adapter.notifyDataSetChanged();
            }
        });
    }

    private void startFeedDetailActivity(String feedId, String imageUrlA, String imageUrlB) {
        startActivity(FeedDetailActivity.getLaunchIntent(context, feedId, imageUrlA, imageUrlB));
    }

    private void observeError(){
        binding.getVm().getError().observe(this, error ->
                showToast(getString(R.string.comment_error_message, error.getMessage())));
    }

    private void showToast(String message){
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
}
