package com.boostcamp.dreampicker.view.feed;

import android.os.Bundle;
import android.view.View;

import com.boostcamp.dreampicker.BR;
import com.boostcamp.dreampicker.R;
import com.boostcamp.dreampicker.databinding.FragmentFeedBinding;
import com.boostcamp.dreampicker.view.BaseFragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;

public class FeedFragment extends BaseFragment<FragmentFeedBinding, FeedViewModel> {
    private FeedAdapter adapter = new FeedAdapter();

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        initView();
    }

    private void initView() {
        binding.rvFeed.setAdapter(adapter);
        model.getFeeds().observe(this, adapter::updateItems);
    }

    @Override
    protected FeedViewModel getViewModel() {
        return ViewModelProviders.of(this).get(FeedViewModel.class);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_feed;
    }

    @Override
    protected int getVariableId() {
        return BR.model;
    }
}
