package com.boostcamp.dreampicker.presentation.feed.voted;


import android.os.Bundle;
import android.view.View;

import com.boostcamp.dreampicker.R;
import com.boostcamp.dreampicker.databinding.FragmentVotedBinding;
import com.boostcamp.dreampicker.presentation.BaseFragment;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class VotedFragment extends BaseFragment<FragmentVotedBinding> {

    @Inject
    VotedViewModel viewModel;

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
        binding.setVm(viewModel);
    }

    private void initRecyclerView() {
        final VotedAdapter adapter = new VotedAdapter();
        binding.rvVotedFeed.setAdapter(adapter);
        binding.getVm().getVotedFeedList().observe(this, list -> {
            if (list.isEmpty()) {
                binding.ivVotedFeedEmptyFinger.setVisibility(View.VISIBLE);
                binding.tvVotedFeedEmptyText.setVisibility(View.VISIBLE);
            }
        });
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
