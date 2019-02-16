package com.boostcamp.dreampicker.di.module.fragment;

import com.boostcamp.dreampicker.data.common.FirebaseManager;
import com.boostcamp.dreampicker.di.scope.FragmentScoped;
import com.boostcamp.dreampicker.presentation.profile.ProfileFragment;
import com.boostcamp.dreampicker.presentation.profile.ProfileViewModelFactory;

import androidx.lifecycle.ViewModelProvider;
import dagger.Binds;
import dagger.Module;
import dagger.Provides;

@Module
public abstract class ProfileModule {
    @FragmentScoped
    @Binds
    abstract ViewModelProvider.Factory feedViewModelFactory(ProfileViewModelFactory factory);

    @FragmentScoped
    @Provides
    static String provideUserId(ProfileFragment fragment) {
        final String userId = FirebaseManager.getCurrentUserId();
        if (userId != null) {
            return userId;
        } else {
            throw new IllegalArgumentException("Not user information");
        }
    }
}