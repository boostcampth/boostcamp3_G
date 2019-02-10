package com.boostcamp.dreampicker.presentation.upload;

import com.boostcamp.dreampicker.data.source.repository.FeedRepository;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class UploadViewModelFactory implements ViewModelProvider.Factory {
    private final FeedRepository feedRepository;

    UploadViewModelFactory(@NonNull FeedRepository feedRepository) {
        this.feedRepository = feedRepository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if(modelClass.isAssignableFrom(LegacyUploadViewModel.class)) {
            //noinspection unchecked
            return (T) new LegacyUploadViewModel(feedRepository);
        } else {
            throw new IllegalArgumentException("ViewModel Not Found");
        }
    }

}
