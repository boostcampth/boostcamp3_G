package com.boostcamp.dreampicker.data.remote.google;

import com.google.firebase.auth.FirebaseUser;

import androidx.annotation.NonNull;
import io.reactivex.Completable;
import io.reactivex.Single;

public interface AccountApi {

    @NonNull
    Single<FirebaseUser> signIn(@NonNull String userToken);

    @NonNull
    Completable signOut();

    @NonNull
    Completable removeUser();
}
