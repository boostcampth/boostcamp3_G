package com.boostcamp.dreampicker.presentation.upload;


import com.boostcamp.dreampicker.data.repository.FeedRepository;
import com.boostcamp.dreampicker.di.scope.ActivityScoped;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

@ActivityScoped
public class UploadViewModelFactory implements ViewModelProvider.Factory {

    @NonNull
    private final FeedRepository feedRepository;

    @Inject
    UploadViewModelFactory(@NonNull final FeedRepository feedRepository) {
        this.feedRepository = feedRepository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(UploadViewModel.class)) {
            //noinspection unchecked
            return (T) new UploadViewModel(feedRepository);
        } else {
            throw new IllegalArgumentException("Unknown ViewModel class: " + modelClass.getName());
        }
    }
}
