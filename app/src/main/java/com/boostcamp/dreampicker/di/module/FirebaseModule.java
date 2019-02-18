package com.boostcamp.dreampicker.di.module;

import com.boostcamp.dreampicker.data.common.FirebaseManager;
import com.boostcamp.dreampicker.di.scope.ActivityScoped;
import com.boostcamp.dreampicker.di.scope.qualifier.UserId;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@SuppressWarnings("SpellCheckingInspection")
@Module
public class FirebaseModule {
    @Provides
    FirebaseFirestore provideFirestore() {
        return FirebaseFirestore.getInstance();
    }

    @Provides
    FirebaseStorage provideFirebaseStorage() {
        return FirebaseStorage.getInstance();
    }

    @Singleton
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
}