package com.boostcamp.dreampicker.di.module;

import android.app.Activity;

import com.boostcamp.dreampicker.di.component.FeedComponent;
import com.boostcamp.dreampicker.di.scope.ActivityScope;
import com.boostcamp.dreampicker.presentation.feed.main.FeedFragment;
import com.boostcamp.dreampicker.presentation.main.MainActivity;

import dagger.Binds;
import dagger.Module;
import dagger.android.AndroidInjector;
import dagger.multibindings.ClassKey;
import dagger.multibindings.IntoMap;

@Module(subcomponents = {FeedComponent.class})
public abstract class MainModule {
    @ActivityScope
    @Binds
    abstract Activity bindView(MainActivity activity);

    @Binds
    @IntoMap
    @ClassKey(FeedFragment.class)
    abstract AndroidInjector.Factory<?> bindFeedFragment(FeedComponent.Builder builder);
}