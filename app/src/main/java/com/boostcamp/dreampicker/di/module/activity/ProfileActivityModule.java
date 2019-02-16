package com.boostcamp.dreampicker.di.module.activity;

import com.boostcamp.dreampicker.di.scope.ActivityScoped;
import com.boostcamp.dreampicker.presentation.profile.ProfileActivity;
import com.boostcamp.dreampicker.presentation.profile.ProfileViewModelFactory;

import androidx.lifecycle.ViewModelProvider;
import dagger.Binds;
import dagger.Module;
import dagger.Provides;

@Module
abstract class ProfileActivityModule {
    @ActivityScoped
    @Binds
    abstract ViewModelProvider.Factory profileViewModelFactory(ProfileViewModelFactory factory);

    @ActivityScoped
    @Provides
    static String provideUserId(ProfileActivity activity) {
        return activity.getIntent().getStringExtra(ProfileActivity.EXTRA_USER_ID);
    }
}
