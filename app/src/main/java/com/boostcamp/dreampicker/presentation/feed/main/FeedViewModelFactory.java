package com.boostcamp.dreampicker.presentation.feed.main;

import com.boostcamp.dreampicker.data.source.FeedDataSource;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class FeedViewModelFactory implements ViewModelProvider.Factory {
    private final FeedDataSource feedRepository;

    public FeedViewModelFactory(FeedDataSource feedRepository) {
        this.feedRepository = feedRepository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if(modelClass.isAssignableFrom(FeedViewModel.class)) {
            return (T) new FeedViewModel(feedRepository);
        } else {
            throw new IllegalArgumentException("ViewModel Not Found");
        }
    }
}
