package com.boostcamp.dreampicker.presentation.feed.feedMain;

import android.os.Bundle;
import android.view.View;

import com.boostcamp.dreampicker.R;
import com.boostcamp.dreampicker.data.source.local.test.FeedMockDataSource;
import com.boostcamp.dreampicker.data.source.remote.firebase.FeedFirebaseService;
import com.boostcamp.dreampicker.data.source.repository.FeedRepository;
import com.boostcamp.dreampicker.databinding.FragmentFeedBinding;
import com.boostcamp.dreampicker.presentation.BaseFragment;

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
        FeedViewModelFactory factory = new FeedViewModelFactory(
                FeedRepository.getInstance(FeedFirebaseService.getInstance()));
        return ViewModelProviders.of(this, factory).get(FeedViewModel.class);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_feed;
    }

}
