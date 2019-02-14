package com.boostcamp.dreampicker.data;

import com.boostcamp.dreampicker.data.source.firestore.UserRemoteDataSource;
import com.boostcamp.dreampicker.data.repository.UserRepository;
import com.boostcamp.dreampicker.data.repository.UserRepositoryImpl;
import com.google.firebase.firestore.FirebaseFirestore;

public class Injection {

    public static UserRepository provideUserRepository() {
        return UserRepositoryImpl.getInstance(
                UserRemoteDataSource.getInstance(FirebaseFirestore.getInstance())
        );
    }
}
