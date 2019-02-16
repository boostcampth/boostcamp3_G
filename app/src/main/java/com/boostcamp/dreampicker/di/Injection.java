package com.boostcamp.dreampicker.di;

import android.content.Context;

import com.boostcamp.dreampicker.data.local.room.AppDatabase;
import com.boostcamp.dreampicker.data.local.room.dao.VotedFeedDao;
import com.boostcamp.dreampicker.data.repository.FeedRepository;
import com.boostcamp.dreampicker.data.repository.FeedRepositoryImpl;
import com.boostcamp.dreampicker.data.repository.UserRepository;
import com.boostcamp.dreampicker.data.repository.UserRepositoryImpl;
import com.boostcamp.dreampicker.data.source.firestore.UserDataSource;
import com.boostcamp.dreampicker.data.source.firestore.UserRemoteDataSource;
import com.boostcamp.dreampicker.presentation.feed.detail.FeedDetailViewModelFactory;
import com.boostcamp.dreampicker.presentation.feed.main.FeedViewModelFactory;
import com.boostcamp.dreampicker.presentation.feed.voted.VotedViewModelFactory;
import com.boostcamp.dreampicker.presentation.profile.ProfileViewModelFactory;
import com.boostcamp.dreampicker.presentation.upload.UploadViewModelFactory;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;

import androidx.annotation.NonNull;

public class Injection {
    public static ProfileViewModelFactory provideProfileViewModelFactory(@NonNull String userId) {
        return new ProfileViewModelFactory(provideUserRepository(), userId);
    }

    public static UploadViewModelFactory provideUploadViewModelFactory(@NonNull Context context) {
        return new UploadViewModelFactory(provideFeedRepository(context));
    }

    private static FeedRepository provideFeedRepository(@NonNull Context context) {
        return FeedRepositoryImpl.getInstance(
                provideFirebaseFirestore(),
                provideFirebaseStorage(),
                provideVotedFeedDao(context));
    }

    public static UserRepository provideUserRepository() {
        return UserRepositoryImpl.getInstance(provideUserRemoteDataSource());
    }

    private static UserDataSource provideUserRemoteDataSource() {
        return UserRemoteDataSource.getInstance(provideFirebaseFirestore());
    }

    private static FirebaseFirestore provideFirebaseFirestore() {
        return FirebaseFirestore.getInstance();
    }

    private static FirebaseStorage provideFirebaseStorage() {
        return FirebaseStorage.getInstance();
    }

    private static VotedFeedDao provideVotedFeedDao(@NonNull Context context) {
        return AppDatabase.getDatabase(context).votedFeedDao();
    }
}
