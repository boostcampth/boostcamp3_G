package com.boostcamp.dreampicker.di.module.repository;

import android.content.Context;

import com.boostcamp.dreampicker.R;
import com.boostcamp.dreampicker.data.remote.firestore.UserDataSource;
import com.boostcamp.dreampicker.data.remote.firestore.UserRemoteDataSource;
import com.boostcamp.dreampicker.data.remote.google.AccountApi;
import com.boostcamp.dreampicker.data.remote.google.GoogleAccountClient;
import com.boostcamp.dreampicker.data.repository.UserRepository;
import com.boostcamp.dreampicker.data.repository.UserRepositoryImpl;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

@SuppressWarnings("unused")
@Module
public abstract class UserRepositoryModule {
    @Singleton
    @Binds
    abstract UserRepository provideUserRepository(UserRepositoryImpl userRepository);

    @Singleton
    @Binds
    abstract UserDataSource provideDataSource(UserRemoteDataSource userRemoteDataSource);

    @Singleton
    @Binds
    abstract AccountApi provideAccountApi(GoogleAccountClient client);

    @Provides
    static GoogleSignInClient provideGoogleSignInClient(Context context) {
        GoogleSignInOptions options = new GoogleSignInOptions
                .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(context.getString(R.string.default_web_client_id))
                .requestEmail()
                .requestProfile()
                .build();
        return GoogleSignIn.getClient(context, options);
    }
}