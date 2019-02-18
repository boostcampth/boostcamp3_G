package com.boostcamp.dreampicker.presentation.feed.detail;

import com.boostcamp.dreampicker.data.repository.FeedRepository;
import com.boostcamp.dreampicker.di.scope.qualifier.FeedId;
import com.boostcamp.dreampicker.di.scope.qualifier.UserId;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class FeedDetailViewModelFactory implements ViewModelProvider.Factory {

    @NonNull
    private final FeedRepository repository;

    @Inject
    FeedDetailViewModelFactory(@NonNull FeedRepository repository) {
        this.repository = repository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(FeedDetailViewModel.class)) {
            //noinspection unchecked
            return (T) new FeedDetailViewModel(repository);
        } else {
            throw new IllegalArgumentException("ViewModel Not Found");
        }
    }
}
