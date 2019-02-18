package com.boostcamp.dreampicker.di.module.activity;

import com.boostcamp.dreampicker.di.scope.ActivityScoped;
import com.boostcamp.dreampicker.presentation.feed.detail.FeedDetailViewModelFactory;

import androidx.lifecycle.ViewModelProvider;
import dagger.Binds;
import dagger.Module;

@Module
abstract class FeedDetailModule {
    @SuppressWarnings("unused")
    @ActivityScoped
    @Binds
    abstract ViewModelProvider.Factory feedDetailViewModelFactory(FeedDetailViewModelFactory factory);
}
