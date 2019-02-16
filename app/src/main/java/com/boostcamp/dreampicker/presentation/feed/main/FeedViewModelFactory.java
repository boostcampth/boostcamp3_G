package com.boostcamp.dreampicker.presentation.feed.main;

import com.boostcamp.dreampicker.data.repository.FeedRepository;
import com.boostcamp.dreampicker.di.scope.UserId;

import javax.inject.Inject;
import javax.inject.Named;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class FeedViewModelFactory implements ViewModelProvider.Factory {
    @NonNull
    private final FeedRepository repository;
    @NonNull
    private final String userId;

    @Inject
    public FeedViewModelFactory(@NonNull FeedRepository repository,
                                @NonNull @UserId String userId) {
        this.repository = repository;
        this.userId = userId;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if(modelClass.isAssignableFrom(FeedViewModel.class)) {
            //noinspection unchecked
            return (T) new FeedViewModel(repository, userId);
        } else {
            throw new IllegalArgumentException("ViewModel Not Found");
        }
    }
}
