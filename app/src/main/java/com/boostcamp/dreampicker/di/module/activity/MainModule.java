package com.boostcamp.dreampicker.di.module.activity;

import com.boostcamp.dreampicker.data.common.FirebaseManager;
import com.boostcamp.dreampicker.di.module.fragment.FeedModule;
import com.boostcamp.dreampicker.di.module.fragment.ProfileModule;
import com.boostcamp.dreampicker.di.module.fragment.VotedFeedModule;
import com.boostcamp.dreampicker.di.scope.ActivityScoped;
import com.boostcamp.dreampicker.di.scope.FragmentScoped;
import com.boostcamp.dreampicker.presentation.feed.main.FeedFragment;
import com.boostcamp.dreampicker.presentation.feed.voted.VotedFragment;
import com.boostcamp.dreampicker.presentation.profile.ProfileFragment;

import dagger.Module;
import dagger.Provides;
import dagger.android.ContributesAndroidInjector;

@SuppressWarnings("unused")
@Module
abstract class MainModule {
    @FragmentScoped
    @ContributesAndroidInjector(modules = FeedModule.class)
    abstract FeedFragment feedFragment();

    @FragmentScoped
    @ContributesAndroidInjector(modules = VotedFeedModule.class)
    abstract VotedFragment votedFeedFragment();

    @FragmentScoped
    @ContributesAndroidInjector(modules = ProfileModule.class)
    abstract ProfileFragment profileFragment();

    @ActivityScoped
    @Provides
    static String provideMyId() {
        final String userId = FirebaseManager.getCurrentUserId();
        if (userId != null) {
            return userId;
        } else {
            throw new IllegalArgumentException("Not user information");
        }
    }
}