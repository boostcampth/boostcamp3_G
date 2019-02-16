package com.boostcamp.dreampicker.di.module.activity;

import com.boostcamp.dreampicker.data.common.FirebaseManager;
import com.boostcamp.dreampicker.di.scope.ActivityScoped;
import com.boostcamp.dreampicker.presentation.upload.UploadViewModelFactory;

import androidx.lifecycle.ViewModelProvider;
import dagger.Binds;
import dagger.Module;
import dagger.Provides;

@Module
abstract class UploadModule {
    @ActivityScoped
    @Binds
    abstract ViewModelProvider.Factory uploadViewModelFactory(UploadViewModelFactory factory);

    @ActivityScoped
    @Provides
    static String provideMyId() {
        final String userId = FirebaseManager.getCurrentUserId();
        if (userId != null) {
            return userId;
        } else {
            throw new IllegalArgumentException("Not user information");
        }
    }
}
