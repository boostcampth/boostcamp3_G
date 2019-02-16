package com.boostcamp.dreampicker.di.module.repository;

import com.boostcamp.dreampicker.data.repository.UserRepository;
import com.boostcamp.dreampicker.data.repository.UserRepositoryImpl;
import com.boostcamp.dreampicker.data.source.firestore.UserDataSource;
import com.boostcamp.dreampicker.data.source.firestore.UserRemoteDataSource;
import com.google.firebase.firestore.FirebaseFirestore;

import dagger.Module;
import dagger.Provides;

@Module
public class UserRepositoryModule {
    @Provides
    UserRepository provideRepository(UserDataSource dataSource) {
        return UserRepositoryImpl.getInstance(dataSource);
    }

    @Provides
    UserDataSource provideDataSource(FirebaseFirestore firestore) {
        return UserRemoteDataSource.getInstance(firestore);
    }
}