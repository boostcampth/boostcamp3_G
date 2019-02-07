package com.boostcamp.dreampicker.presentation.upload;

import com.boostcamp.dreampicker.data.source.FeedDataSource;
import com.boostcamp.dreampicker.data.source.UserDataSource;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class UploadViewModelFactory implements ViewModelProvider.Factory {
    private final FeedDataSource feedRepository;
    private final UserDataSource userRepository;

    UploadViewModelFactory(FeedDataSource feedRepository, UserDataSource userRepositry) {
        this.feedRepository = feedRepository;
        this.userRepository = userRepositry;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if(modelClass.isAssignableFrom(UploadViewModel.class)) {
            //noinspection unchecked
            // Todo : 인터페이스 변경시 수정
            return (T) new UploadViewModel(feedRepository, userRepository);
        } else {
            throw new IllegalArgumentException("ViewModel Not Found");
        }
    }

}
