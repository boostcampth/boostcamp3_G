package com.boostcamp.dreampicker.view.feed;

import android.os.Bundle;
import android.view.View;

import com.boostcamp.dreampicker.BR;
import com.boostcamp.dreampicker.R;
import com.boostcamp.dreampicker.databinding.FragmentFeedBinding;
import com.boostcamp.dreampicker.view.BaseFragment;
import com.boostcamp.dreampicker.viewmodel.FeedViewModel;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;

public class FeedFragment extends BaseFragment<FragmentFeedBinding> {
    private FeedAdapter adapter = new FeedAdapter();
    private FeedViewModel model;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        model = ViewModelProviders.of(this).get(FeedViewModel.class);
        binding.setVariable(BR.model, model);

        initView();
    }

    private void initView() {
        binding.rvFeed.setAdapter(adapter);
        model.getFeeds().observe(this, adapter::updateItems);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_feed;
    }
}
