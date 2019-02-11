package com.boostcamp.dreampicker.presentation.profile;

import com.boostcamp.dreampicker.data.repository.UserRepository;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class ProfileViewModelFactory implements ViewModelProvider.Factory {

    @NonNull
    private final UserRepository repository;
    @NonNull
    private String userId;

    ProfileViewModelFactory(@NonNull UserRepository repository,
            @NonNull String userId) {
        this.repository = repository;
        this.userId = userId;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(ProfileViewModel.class)) {
            //noinspection unchecked
            return (T) new ProfileViewModel(repository, userId);
        }
        throw new IllegalArgumentException("Unknown ViewModel class: " + modelClass.getName());
    }
}
