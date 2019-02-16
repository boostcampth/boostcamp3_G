package com.boostcamp.dreampicker.di.module.activity;

import com.boostcamp.dreampicker.di.scope.ActivityScope;
import com.boostcamp.dreampicker.presentation.feed.detail.FeedDetailActivity;
import com.boostcamp.dreampicker.presentation.main.LogInActivity;
import com.boostcamp.dreampicker.presentation.main.MainActivity;
import com.boostcamp.dreampicker.presentation.main.SplashActivity;
import com.boostcamp.dreampicker.presentation.profile.ProfileActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBindingModule {
    @ActivityScope
    @ContributesAndroidInjector
    abstract SplashActivity splashActivity();

    @ActivityScope
    @ContributesAndroidInjector
    abstract LogInActivity logInActivity();

    @ActivityScope
    @ContributesAndroidInjector(modules = MainModule.class)
    abstract MainActivity mainActivity();

    @ActivityScope
    @ContributesAndroidInjector
    abstract FeedDetailActivity feedDetailActivity();

    @ActivityScope
    @ContributesAndroidInjector
    abstract ProfileActivity profileActivity();
}
