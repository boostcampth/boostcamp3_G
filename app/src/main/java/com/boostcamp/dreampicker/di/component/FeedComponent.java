package com.boostcamp.dreampicker.di.component;

import com.boostcamp.dreampicker.di.module.FeedModule;
import com.boostcamp.dreampicker.di.scope.FragmentScope;
import com.boostcamp.dreampicker.presentation.feed.main.FeedFragment;

import dagger.Subcomponent;
import dagger.android.AndroidInjector;

@FragmentScope
@Subcomponent(modules = FeedModule.class)
public interface FeedComponent extends AndroidInjector<FeedFragment> {

    @Subcomponent.Builder
    abstract class Builder extends AndroidInjector.Builder<FeedFragment> { }
}
