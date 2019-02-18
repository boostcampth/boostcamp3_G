package com.boostcamp.dreampicker.presentation.upload;


import com.boostcamp.dreampicker.data.repository.FeedRepository;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class UploadViewModelFactory implements ViewModelProvider.Factory {

    @NonNull
    private final FeedRepository feedRepository;
    @NonNull
    private final String userId;

    @Inject
    public UploadViewModelFactory(@NonNull final FeedRepository feedRepository,
                                  @NonNull final String userId) {
        this.feedRepository = feedRepository;
        this.userId = userId;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if(modelClass.isAssignableFrom(UploadViewModel.class)) {
            //noinspection unchecked
            return (T) new UploadViewModel(feedRepository, userId);
        } else {
            throw new IllegalArgumentException("Unknown ViewModel class: " + modelClass.getName());
        }
    }
}
