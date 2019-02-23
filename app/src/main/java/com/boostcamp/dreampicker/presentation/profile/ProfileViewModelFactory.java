package com.boostcamp.dreampicker.presentation.profile;

import com.boostcamp.dreampicker.data.repository.UserRepository;
import com.boostcamp.dreampicker.di.scope.ActivityScoped;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

@ActivityScoped
public class ProfileViewModelFactory implements ViewModelProvider.Factory {

    @NonNull
    private final UserRepository repository;

    @Inject
    ProfileViewModelFactory(@NonNull UserRepository repository) {
        this.repository = repository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(ProfileViewModel.class)) {
            //noinspection unchecked
            return (T) new ProfileViewModel(repository);
        }
        throw new IllegalArgumentException("Unknown ViewModel class: " + modelClass.getName());
    }
}
