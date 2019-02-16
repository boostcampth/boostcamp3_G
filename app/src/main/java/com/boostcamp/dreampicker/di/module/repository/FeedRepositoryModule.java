package com.boostcamp.dreampicker.di.module.repository;

import android.app.Application;

import com.boostcamp.dreampicker.data.local.room.AppDatabase;
import com.boostcamp.dreampicker.data.local.room.dao.VotedFeedDao;
import com.boostcamp.dreampicker.data.repository.FeedRepository;
import com.boostcamp.dreampicker.data.repository.FeedRepositoryImpl;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;

import dagger.Module;
import dagger.Provides;

@Module
public class FeedRepositoryModule {
    @Provides
    public FeedRepository provideRepository(FirebaseFirestore firestore,
                                            FirebaseStorage storage,
                                            VotedFeedDao votedFeedDao) {
        return FeedRepositoryImpl.getInstance(firestore, storage, votedFeedDao);
    }

    @Provides
    public AppDatabase provideDb(Application context) {
        return AppDatabase.getDatabase(context);
    }

    @Provides
    public VotedFeedDao provideVotedFeedDao(AppDatabase db) {
        return db.votedFeedDao();
    }
}
