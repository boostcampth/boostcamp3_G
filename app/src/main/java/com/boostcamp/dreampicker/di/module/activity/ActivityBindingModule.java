package com.boostcamp.dreampicker.di.module.activity;

import com.boostcamp.dreampicker.di.scope.ActivityScoped;
import com.boostcamp.dreampicker.presentation.feed.detail.FeedDetailActivity;
import com.boostcamp.dreampicker.presentation.main.LogInActivity;
import com.boostcamp.dreampicker.presentation.main.MainActivity;
import com.boostcamp.dreampicker.presentation.main.SplashActivity;
import com.boostcamp.dreampicker.presentation.profile.ProfileActivity;
import com.boostcamp.dreampicker.presentation.upload.UploadActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@SuppressWarnings("unused")
@Module
public abstract class ActivityBindingModule {
    @ActivityScoped
    @ContributesAndroidInjector
    abstract SplashActivity splashActivity();

    @ActivityScoped
    @ContributesAndroidInjector
    abstract LogInActivity logInActivity();

    @ActivityScoped
    @ContributesAndroidInjector(modules = MainModule.class)
    abstract MainActivity mainActivity();

    @ActivityScoped
    @ContributesAndroidInjector
    abstract FeedDetailActivity feedDetailActivity();

    @ActivityScoped
    @ContributesAndroidInjector
    abstract ProfileActivity profileActivity();

    @ActivityScoped
    @ContributesAndroidInjector
    abstract UploadActivity uploadActivity();
}
