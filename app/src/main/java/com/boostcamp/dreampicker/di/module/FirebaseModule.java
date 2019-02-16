package com.boostcamp.dreampicker.di.module;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;

import dagger.Module;
import dagger.Provides;

@Module
public class FirebaseModule {
    @Provides
    public FirebaseFirestore provideFirestore() {
        return FirebaseFirestore.getInstance();
    }

    @Provides
    public FirebaseStorage provideFirebaseStorage() {
        return FirebaseStorage.getInstance();
    }
}
