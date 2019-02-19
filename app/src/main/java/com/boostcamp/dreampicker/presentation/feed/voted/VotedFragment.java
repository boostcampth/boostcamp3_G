package com.boostcamp.dreampicker.presentation.feed.voted;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.boostcamp.dreampicker.R;
import com.boostcamp.dreampicker.data.local.room.entity.VotedFeed;
import com.boostcamp.dreampicker.databinding.FragmentVotedBinding;
import com.boostcamp.dreampicker.presentation.BaseFragment;
import com.boostcamp.dreampicker.presentation.feed.detail.FeedDetailActivity;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;

public class VotedFragment extends BaseFragment<FragmentVotedBinding> {

    @Inject
    VotedViewModelFactory factory;
    @Inject
    Context context;

    @Inject
    public VotedFragment() {
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViewModel();
        initRecyclerView();
    }

    private void initViewModel() {
        final VotedViewModel viewModel =
                ViewModelProviders.of(this, factory).get(VotedViewModel.class);
        binding.setVm(viewModel);
    }

    private void initRecyclerView() {
        final VotedAdapter adapter = new VotedAdapter();
        adapter.setOnItemClickListener(this::startDetailActivity);
        binding.rvVotedFeed.setAdapter(adapter);
        binding.getVm().getVotedFeedList().observe(this, list -> {
            if (list.isEmpty()) {
                binding.ivVotedFeedEmptyFinger.setVisibility(View.VISIBLE);
                binding.tvVotedFeedEmptyText.setVisibility(View.VISIBLE);
            }
        });
    }

    private void startDetailActivity(@NonNull VotedFeed feed) {
        final Intent intent =
                FeedDetailActivity.getLaunchIntent(context,
                        feed.getId(),
                        feed.getImageUrlA(),
                        feed.getImageUrlB());
        startActivity(intent);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_voted;
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            binding.rvVotedFeed.smoothScrollToPosition(0);
        }
    }
}
