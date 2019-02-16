package com.boostcamp.dreampicker.di.module.fragment;

import com.boostcamp.dreampicker.di.scope.FragmentScope;
import com.boostcamp.dreampicker.presentation.feed.main.FeedFragment;

import dagger.android.ContributesAndroidInjector;

public abstract class FragmentBindingModule {
    @FragmentScope
    @ContributesAndroidInjector(modules = FeedModule.class)
    abstract FeedFragment feedFragment();
}
