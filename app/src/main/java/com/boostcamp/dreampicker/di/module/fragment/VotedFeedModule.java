package com.boostcamp.dreampicker.di.module.fragment;

import com.boostcamp.dreampicker.di.scope.FragmentScoped;
import com.boostcamp.dreampicker.presentation.feed.voted.VotedViewModel;
import com.boostcamp.dreampicker.presentation.feed.voted.VotedViewModelFactory;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import dagger.Binds;
import dagger.Module;
import dagger.Provides;

@Module
public abstract class VotedFeedModule {
    @FragmentScoped
    @Provides
    static ViewModel votedViewModel(Fragment fragment, ViewModelProvider.Factory factory) {
        return ViewModelProviders.of(fragment, factory).get(VotedViewModel.class);
    }

    @FragmentScoped
    @Binds
    abstract ViewModelProvider.Factory votedViewModelFactory(VotedViewModelFactory factory);
}