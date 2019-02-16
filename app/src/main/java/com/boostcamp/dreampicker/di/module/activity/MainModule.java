package com.boostcamp.dreampicker.di.module.activity;

import com.boostcamp.dreampicker.di.module.fragment.FeedModule;
import com.boostcamp.dreampicker.di.scope.FragmentScope;
import com.boostcamp.dreampicker.presentation.feed.main.FeedFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class MainModule {
    @FragmentScope
    @ContributesAndroidInjector(modules = FeedModule.class)
    abstract FeedFragment feedFragment();
}