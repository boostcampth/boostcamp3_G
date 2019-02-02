package com.boostcamp.dreampicker.view.feed;

import android.os.Bundle;
import android.view.View;

import com.boostcamp.dreampicker.R;
import com.boostcamp.dreampicker.data.source.feed.FeedMockDataSource;
import com.boostcamp.dreampicker.databinding.FragmentFeedBinding;
import com.boostcamp.dreampicker.view.BaseFragment;
import com.boostcamp.dreampicker.viewmodel.FeedViewModel;
import com.boostcamp.dreampicker.viewmodel.factory.FeedViewModelFactory;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;

public class FeedFragment extends BaseFragment<FragmentFeedBinding, FeedViewModel> {
    public FeedFragment() { }

    public static FeedFragment newInstance() {
        return new FeedFragment();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        final FeedAdapter adapter = new FeedAdapter(viewModel);

        binding.setViewModel(viewModel);
        binding.rvFeed.setAdapter(adapter);

        viewModel.getFeeds().observe(this, adapter::updateItems);
    }

    @Override
    protected FeedViewModel getViewModel() {
        FeedViewModelFactory factory = new FeedViewModelFactory(new FeedMockDataSource());
        return ViewModelProviders.of(this, factory).get(FeedViewModel.class);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_feed;
    }

}
