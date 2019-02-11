package com.boostcamp.dreampicker.presentation.legacyupload;

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
        if(modelClass.isAssignableFrom(UploadViewModel.class)) {
            //noinspection unchecked
            return (T) new UploadViewModel(feedRepository);
        } else {
            throw new IllegalArgumentException("ViewModel Not Found");
        }
    }

}
