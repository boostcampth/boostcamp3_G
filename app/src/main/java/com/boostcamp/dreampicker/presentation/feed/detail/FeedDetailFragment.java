package com.boostcamp.dreampicker.presentation.feed.detail;

import android.os.Bundle;
import android.view.View;

import com.boostcamp.dreampicker.R;
import com.boostcamp.dreampicker.databinding.FragmentFeedDetailBinding;
import com.boostcamp.dreampicker.presentation.BaseFragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;

public class FeedDetailFragment extends BaseFragment<FragmentFeedDetailBinding, FeedDetailViewModel> {
    private static final String ARGUMENT_FEED_ID = "ARGUMENT_FEED_ID";

    private String feedId;

    public FeedDetailFragment() {}

    public static FeedDetailFragment newInstance(String feedId) {
        FeedDetailFragment fragment = new FeedDetailFragment();
        Bundle args = new Bundle();
        args.putString(ARGUMENT_FEED_ID, feedId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_feed_detail;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle args = getArguments();
        if(args != null){
            feedId = args.getString(ARGUMENT_FEED_ID);
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initView();
    }

    private void initView() {

        // TODO. 뷰 그리기
    }

    @Override
    protected FeedDetailViewModel getViewModel() {
        return ViewModelProviders.of(this).get(FeedDetailViewModel.class);
    }
}
