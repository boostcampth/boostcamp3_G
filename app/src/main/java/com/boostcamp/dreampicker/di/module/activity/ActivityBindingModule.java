package com.boostcamp.dreampicker.di.module.activity;

import com.boostcamp.dreampicker.di.module.fragment.ProfileActivityModule;
import com.boostcamp.dreampicker.di.scope.ActivityScoped;
import com.boostcamp.dreampicker.di.scope.FeedDetail;
import com.boostcamp.dreampicker.di.scope.UserDetail;
import com.boostcamp.dreampicker.presentation.feed.detail.FeedDetailActivity;
import com.boostcamp.dreampicker.presentation.main.LogInActivity;
import com.boostcamp.dreampicker.presentation.main.MainActivity;
import com.boostcamp.dreampicker.presentation.main.SplashActivity;
import com.boostcamp.dreampicker.presentation.profile.ProfileActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBindingModule {
    @ContributesAndroidInjector
    abstract SplashActivity splashActivity();

    @ContributesAndroidInjector
    abstract LogInActivity logInActivity();

    @ActivityScoped
    @ContributesAndroidInjector(modules = MainModule.class)
    abstract MainActivity mainActivity();

    @FeedDetail
    @ContributesAndroidInjector(modules = FeedDetailModule.class)
    abstract FeedDetailActivity feedDetailActivity();

    @UserDetail
    @ContributesAndroidInjector(modules = ProfileActivityModule.class)
    abstract ProfileActivity profileActivity();
}
