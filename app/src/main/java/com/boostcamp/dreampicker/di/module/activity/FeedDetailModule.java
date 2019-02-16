package com.boostcamp.dreampicker.di.module.activity;

import com.boostcamp.dreampicker.data.common.FirebaseManager;
import com.boostcamp.dreampicker.di.scope.ActivityScoped;
import com.boostcamp.dreampicker.di.scope.qualifier.FeedId;
import com.boostcamp.dreampicker.di.scope.qualifier.UserId;
import com.boostcamp.dreampicker.presentation.feed.detail.FeedDetailActivity;
import com.boostcamp.dreampicker.presentation.feed.detail.FeedDetailViewModelFactory;

import androidx.lifecycle.ViewModelProvider;
import dagger.Binds;
import dagger.Module;
import dagger.Provides;

@Module
abstract class FeedDetailModule {
    @ActivityScoped
    @Binds
    abstract ViewModelProvider.Factory feedDetailViewModelFactory(FeedDetailViewModelFactory factory);

    @ActivityScoped
    @UserId
    @Provides
    static String provideMyId() {
        final String userId = FirebaseManager.getCurrentUserId();
        if (userId != null) {
            return userId;
        } else {
            throw new IllegalArgumentException("Not user information");
        }
    }

    @ActivityScoped
    @FeedId
    @Provides
    static String provideFeedId(FeedDetailActivity activity) {
        return activity.getIntent().getStringExtra(FeedDetailActivity.EXTRA_FEED_ID);
    }
}
