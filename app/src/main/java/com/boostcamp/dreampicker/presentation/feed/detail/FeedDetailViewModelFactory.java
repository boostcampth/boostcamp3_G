package com.boostcamp.dreampicker.presentation.feed.detail;

import com.boostcamp.dreampicker.data.repository.FeedRepository;

import javax.inject.Inject;
import javax.inject.Named;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class FeedDetailViewModelFactory implements ViewModelProvider.Factory {

    @NonNull
    private final FeedRepository repository;
    @NonNull
    private final String userId;
    @NonNull
    private final String feedId;

    @Inject
    public FeedDetailViewModelFactory(@NonNull FeedRepository repository,
                                      @NonNull @Named("user") String userId,
                                      @NonNull @Named("feed") String feedId) {
        this.repository = repository;
        this.userId = userId;
        this.feedId = feedId;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(FeedDetailViewModel.class)) {
            //noinspection unchecked
            return (T) new FeedDetailViewModel(repository, userId, feedId);
        } else {
            throw new IllegalArgumentException("ViewModel Not Found");
        }
    }
}
