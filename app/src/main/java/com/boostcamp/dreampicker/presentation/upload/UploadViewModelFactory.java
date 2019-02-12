package com.boostcamp.dreampicker.presentation.upload;


import com.boostcamp.dreampicker.data.repository.FeedRepository;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class UploadViewModelFactory implements ViewModelProvider.Factory {

    @NonNull
    private final FeedRepository feedRepository;

    UploadViewModelFactory(@NonNull final FeedRepository feedRepository) {
        this.feedRepository = feedRepository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if(modelClass.isAssignableFrom(UploadViewModel.class)) {
            //noinspection unchecked
            return (T) new UploadViewModel(feedRepository);
        } else {
            throw new IllegalArgumentException("Unknown ViewModel class: " + modelClass.getName());
        }
    }
}
