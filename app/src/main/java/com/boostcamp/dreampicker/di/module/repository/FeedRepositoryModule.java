package com.boostcamp.dreampicker.di.module.repository;

import android.app.Application;

import com.boostcamp.dreampicker.data.local.room.AppDatabase;
import com.boostcamp.dreampicker.data.local.room.dao.VotedFeedDao;
import com.boostcamp.dreampicker.data.repository.FeedRepository;
import com.boostcamp.dreampicker.data.repository.FeedRepositoryImpl;

import javax.inject.Singleton;

import androidx.room.Room;
import dagger.Binds;
import dagger.Module;
import dagger.Provides;

@Module
public abstract class FeedRepositoryModule {
    @SuppressWarnings("unused")
    @Singleton
    @Binds
    abstract FeedRepository provideFeedRepository(FeedRepositoryImpl feedRepository);

    @Singleton
    @Provides
    static AppDatabase provideDb(Application context) {
        return Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "app_database")
                .build();
    }

    @Singleton
    @Provides
    static VotedFeedDao provideVotedFeedDao(AppDatabase db) {
        return db.votedFeedDao();
    }
}