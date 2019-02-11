package com.boostcamp.dreampicker.presentation.feed.legacymain;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.boostcamp.dreampicker.R;
import com.boostcamp.dreampicker.data.model.LegacyFeed;
import com.boostcamp.dreampicker.data.source.remote.firebase.FeedFirebaseService;
import com.boostcamp.dreampicker.data.source.repository.FeedRepository;
import com.boostcamp.dreampicker.databinding.FragmentFeedBinding;
import com.boostcamp.dreampicker.presentation.BaseFragment;
import com.boostcamp.dreampicker.presentation.feed.detail.FeedDetailActivity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;

public class FeedFragment extends BaseFragment<FragmentFeedBinding> {
    private FeedViewModel viewModel;

    public FeedFragment() { }

    public static FeedFragment newInstance() {
        return new FeedFragment();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        initViewModel();
        initRecyclerView();
        subscribeError();
    }

    private void initViewModel() {
        final FeedViewModelFactory factory = new FeedViewModelFactory(
                FeedRepository.getInstance(FeedFirebaseService.getInstance()));

        viewModel = ViewModelProviders.of(this, factory).get(FeedViewModel.class);
        binding.setViewModel(viewModel);
    }

    private void initRecyclerView() {
        //final FeedAdapter adapter = new FeedAdapter(viewModel::vote, this::startFeedDetailActivity);
        final FeedAdapter adapter = new FeedAdapter(viewModel::vote, this::startFeedDetailActivity);
        binding.rvFeed.setItemAnimator(null);
        binding.rvFeed.setAdapter(adapter);
    }

    private void startFeedDetailActivity(@NonNull LegacyFeed feed) {
        if(getContext() != null) {
            startActivity(FeedDetailActivity.getLaunchIntent(getContext(), feed.getId()));
        }
    }

    private void subscribeError() {
        binding.getViewModel()
                .getError()
                .observe(this, e -> Log.d("Melon", e.toString()));
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_feed;
    }

}
