package com.boostcamp.dreampicker.di.module.fragment;

import com.boostcamp.dreampicker.di.scope.FragmentScoped;
import com.boostcamp.dreampicker.presentation.feed.voted.VotedViewModelFactory;

import androidx.lifecycle.ViewModelProvider;
import dagger.Binds;
import dagger.Module;

@Module
public abstract class VotedFeedModule {
    @FragmentScoped
    @Binds
    abstract ViewModelProvider.Factory votedFeedViewModelFactory(VotedViewModelFactory factory);
}