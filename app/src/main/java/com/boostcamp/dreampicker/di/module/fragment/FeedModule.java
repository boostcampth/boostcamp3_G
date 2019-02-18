package com.boostcamp.dreampicker.di.module.fragment;

import com.boostcamp.dreampicker.di.scope.FragmentScoped;
import com.boostcamp.dreampicker.presentation.feed.main.FeedFragment;
import com.boostcamp.dreampicker.presentation.feed.main.FeedViewModel;
import com.boostcamp.dreampicker.presentation.feed.main.FeedViewModelFactory;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import dagger.Binds;
import dagger.Module;
import dagger.Provides;

@Module
public abstract class FeedModule {
    @SuppressWarnings("unused")
    @FragmentScoped
    @Binds
    abstract ViewModelProvider.Factory feedViewModelFactory(FeedViewModelFactory factory);
}