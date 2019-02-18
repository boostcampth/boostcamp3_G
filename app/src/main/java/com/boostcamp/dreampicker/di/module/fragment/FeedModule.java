package com.boostcamp.dreampicker.di.module.fragment;

import com.boostcamp.dreampicker.di.scope.FragmentScoped;
import com.boostcamp.dreampicker.presentation.feed.main.FeedFragment;
import com.boostcamp.dreampicker.presentation.feed.main.FeedViewModel;
import com.boostcamp.dreampicker.presentation.feed.main.FeedViewModelFactory;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import dagger.Binds;
import dagger.Module;
import dagger.Provides;

@Module
public abstract class FeedModule {
    @FragmentScoped
    @Provides
    static ViewModel feedViewModel(Fragment fragment, ViewModelProvider.Factory factory) {
        return ViewModelProviders.of(fragment, factory).get(FeedViewModel.class);
    }

    @SuppressWarnings("unused")
    @FragmentScoped
    @Binds
    abstract ViewModelProvider.Factory feedViewModelFactory(FeedViewModelFactory factory);
}