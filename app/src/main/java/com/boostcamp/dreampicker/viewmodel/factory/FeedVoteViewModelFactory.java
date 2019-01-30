package com.boostcamp.dreampicker.viewmodel.factory;

import com.boostcamp.dreampicker.model.Feed;
import com.boostcamp.dreampicker.viewmodel.FeedVoteViewModel;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class FeedVoteViewModelFactory extends ViewModelProvider.NewInstanceFactory {
    private final Feed feed;

    public FeedVoteViewModelFactory(@NonNull Feed feed) {
        this.feed = feed;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new FeedVoteViewModel(feed);
    }
}
