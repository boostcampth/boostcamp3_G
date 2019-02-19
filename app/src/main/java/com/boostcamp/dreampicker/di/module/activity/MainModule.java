package com.boostcamp.dreampicker.di.module.activity;

import com.boostcamp.dreampicker.di.scope.FragmentScoped;
import com.boostcamp.dreampicker.presentation.feed.main.FeedFragment;
import com.boostcamp.dreampicker.presentation.feed.voted.VotedFragment;
import com.boostcamp.dreampicker.presentation.profile.ProfileFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@SuppressWarnings("unused")
@Module
abstract class MainModule {
    @FragmentScoped
    @ContributesAndroidInjector
    abstract FeedFragment feedFragment();

    @FragmentScoped
    @ContributesAndroidInjector
    abstract VotedFragment votedFeedFragment();

    @FragmentScoped
    @ContributesAndroidInjector
    abstract ProfileFragment profileFragment();
}