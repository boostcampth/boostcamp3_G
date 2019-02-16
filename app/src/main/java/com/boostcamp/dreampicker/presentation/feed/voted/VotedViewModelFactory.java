package com.boostcamp.dreampicker.presentation.feed.voted;

import com.boostcamp.dreampicker.data.repository.FeedRepository;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class VotedViewModelFactory implements ViewModelProvider.Factory {
    @NonNull
    private final FeedRepository repository;

    @Inject
    public VotedViewModelFactory(@NonNull FeedRepository repository) {
        this.repository = repository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if(modelClass.isAssignableFrom(VotedViewModel.class)) {
            //noinspection unchecked
            return (T) new VotedViewModel(repository);
        } else {
            throw new IllegalArgumentException("ViewModel Not Found");
        }
    }
}
