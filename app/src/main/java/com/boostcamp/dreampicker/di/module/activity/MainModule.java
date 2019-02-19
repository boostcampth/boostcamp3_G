package com.boostcamp.dreampicker.di.module.activity;

import android.util.Log;

import com.boostcamp.dreampicker.data.repository.FeedRepository;
import com.boostcamp.dreampicker.di.scope.ActivityScoped;
import com.boostcamp.dreampicker.di.scope.FragmentScoped;
import com.boostcamp.dreampicker.presentation.feed.main.FeedFragment;
import com.boostcamp.dreampicker.presentation.feed.main.FeedViewModelFactory;
import com.boostcamp.dreampicker.presentation.feed.voted.VotedFragment;
import com.boostcamp.dreampicker.presentation.feed.voted.VotedViewModelFactory;
import com.boostcamp.dreampicker.presentation.profile.ProfileFragment;
import com.boostcamp.dreampicker.presentation.profile.ProfileViewModelFactory;

import androidx.lifecycle.ViewModelProvider;
import dagger.Binds;
import dagger.Module;
import dagger.Provides;
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