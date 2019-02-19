package com.boostcamp.dreampicker.di.module.repository;

import com.boostcamp.dreampicker.data.repository.UserRepository;
import com.boostcamp.dreampicker.data.repository.UserRepositoryImpl;
import com.boostcamp.dreampicker.data.source.firestore.UserDataSource;
import com.boostcamp.dreampicker.data.source.firestore.UserRemoteDataSource;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;

@SuppressWarnings("unused")
@Module
public abstract class UserRepositoryModule {
    @Singleton
    @Binds
    abstract UserRepository provideUserRepository(UserRepositoryImpl userRepository);

    @Singleton
    @Binds
    abstract UserDataSource provideDataSource(UserRemoteDataSource userRemoteDataSource);
}