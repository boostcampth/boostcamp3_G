package com.boostcamp.dreampicker.di.module;

import com.boostcamp.dreampicker.di.scope.FragmentScope;
import com.boostcamp.dreampicker.presentation.feed.main.FeedFragment;

import androidx.fragment.app.Fragment;
import dagger.Binds;
import dagger.Module;

@Module
public abstract class FeedModule {
    @FragmentScope
    @Binds
    abstract Fragment bindView(FeedFragment fragment);
}