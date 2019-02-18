package com.boostcamp.dreampicker.di.module;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;

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
}