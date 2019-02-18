package com.boostcamp.dreampicker.di.module.activity;

import com.boostcamp.dreampicker.di.scope.ActivityScoped;
import com.boostcamp.dreampicker.presentation.upload.UploadViewModelFactory;

import androidx.lifecycle.ViewModelProvider;
import dagger.Binds;
import dagger.Module;

@Module
abstract class UploadModule {
    @SuppressWarnings("unused")
    @ActivityScoped
    @Binds
    abstract ViewModelProvider.Factory uploadViewModelFactory(UploadViewModelFactory factory);
}
