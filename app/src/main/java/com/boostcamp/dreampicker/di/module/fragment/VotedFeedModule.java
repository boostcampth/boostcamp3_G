package com.boostcamp.dreampicker.di.module.fragment;

import com.boostcamp.dreampicker.di.scope.FragmentScope;
import com.boostcamp.dreampicker.presentation.feed.voted.VotedViewModelFactory;

import androidx.lifecycle.ViewModelProvider;
import dagger.Binds;
import dagger.Module;

@Module
public abstract class VotedFeedModule {
    @FragmentScope
    @Binds
    abstract ViewModelProvider.Factory feedViewModelFactory(VotedViewModelFactory factory);
}