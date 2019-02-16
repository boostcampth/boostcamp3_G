package com.boostcamp.dreampicker.di.module.activity;

import com.boostcamp.dreampicker.di.module.fragment.FeedModule;
import com.boostcamp.dreampicker.di.module.fragment.ProfileModule;
import com.boostcamp.dreampicker.di.module.fragment.VotedFeedModule;
import com.boostcamp.dreampicker.di.scope.FragmentScope;
import com.boostcamp.dreampicker.presentation.feed.main.FeedFragment;
import com.boostcamp.dreampicker.presentation.feed.voted.VotedFragment;
import com.boostcamp.dreampicker.presentation.profile.ProfileFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
abstract class MainModule {
    @FragmentScope
    @ContributesAndroidInjector(modules = FeedModule.class)
    abstract FeedFragment feedFragment();

    @FragmentScope
    @ContributesAndroidInjector(modules = VotedFeedModule.class)
    abstract VotedFragment votedFeedFragment();

    @FragmentScope
    @ContributesAndroidInjector(modules = ProfileModule.class)
    abstract ProfileFragment profileFragment();
}