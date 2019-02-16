package com.boostcamp.dreampicker.di.module.fragment;

import com.boostcamp.dreampicker.presentation.feed.main.FeedViewModelFactory;

import androidx.lifecycle.ViewModelProvider;
import dagger.Binds;
import dagger.Module;

@Module
public abstract class FeedModule {
    @Binds
    abstract ViewModelProvider.Factory feedViewModelFactory(FeedViewModelFactory factory);
}