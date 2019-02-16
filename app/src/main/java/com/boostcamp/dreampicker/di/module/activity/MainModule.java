package com.boostcamp.dreampicker.di.module.activity;

import com.boostcamp.dreampicker.di.module.fragment.FeedModule;
import com.boostcamp.dreampicker.di.module.fragment.VotedFeedModule;
import com.boostcamp.dreampicker.di.scope.FragmentScope;
import com.boostcamp.dreampicker.presentation.feed.main.FeedFragment;
import com.boostcamp.dreampicker.presentation.feed.voted.VotedFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class MainModule {
    @FragmentScope
    @ContributesAndroidInjector(modules = FeedModule.class)
    abstract FeedFragment feedFragment();

    @FragmentScope
    @ContributesAndroidInjector(modules = VotedFeedModule.class)
    abstract VotedFragment votedFeedFragment();
}