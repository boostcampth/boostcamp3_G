package com.boostcamp.dreampicker.presentation.feed.main;

import android.os.Bundle;
import android.view.View;

import com.boostcamp.dreampicker.R;
import com.boostcamp.dreampicker.data.source.local.test.FeedMockDataSource;
import com.boostcamp.dreampicker.databinding.FragmentFeedBinding;
import com.boostcamp.dreampicker.presentation.BaseFragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;

public class FeedFragment extends BaseFragment<FragmentFeedBinding, FeedViewModel> {
    private FeedAdapter adapter;

    public FeedFragment() { }

    public static FeedFragment newInstance() {
        return new FeedFragment();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        initViewModel();
        initRecyclerView();

        viewModel.getFeedList().observe(this, adapter::updateItems);
    }

    private void initViewModel() {
        binding.setViewModel(viewModel);
    }

    private void initRecyclerView() {
        adapter = new FeedAdapter();
        adapter.setOnVoteListener(viewModel::vote);
        binding.rvFeed.setAdapter(adapter);
    }

    @Override
    protected FeedViewModel getViewModel() {
        final FeedViewModelFactory factory = new FeedViewModelFactory(FeedMockDataSource.getInstance());
        return ViewModelProviders.of(this, factory).get(FeedViewModel.class);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_feed;
    }

}
