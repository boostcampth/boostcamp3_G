package com.boostcamp.dreampicker.data.repository;

import com.boostcamp.dreampicker.data.model.UserDetail;
import com.boostcamp.dreampicker.data.source.firestore.mapper.UserDetailMapper;
import com.boostcamp.dreampicker.data.source.firestore.model.UserDetailEntity;
import com.google.firebase.firestore.FirebaseFirestore;

import androidx.annotation.NonNull;
import io.reactivex.Single;

public class UserRepositoryImpl implements UserRepository {
    private static volatile UserRepository INSTANCE = null;

    public static UserRepository getInstance(@NonNull final FirebaseFirestore firestore) {
        if (INSTANCE == null) {
            synchronized (UserRepositoryImpl.class) {
                if (INSTANCE == null) {
                    INSTANCE = new UserRepositoryImpl(firestore);
                }
            }
        }
        return INSTANCE;
    }

    @NonNull
    private final FirebaseFirestore firestore;
    private final String COLLECTION_USER = "user";

    private UserRepositoryImpl(@NonNull final FirebaseFirestore firestore) {
        this.firestore = firestore;
    }

    @NonNull
    @Override
    public Single<UserDetail> getUserDetail(@NonNull final String userId) {

        return Single.create(emitter ->
                firestore.collection(COLLECTION_USER).document(userId)
                        .get()
                        .addOnSuccessListener(task -> {
                            final UserDetailEntity response = task.exists()
                                    ? task.toObject(UserDetailEntity.class) : null;
                            if (response != null) {
                                emitter.onSuccess(UserDetailMapper
                                        .toUserDetail(response.getId(), response));
                            } else {
                                emitter.onError(new IllegalArgumentException());
                            }
                        })
                        .addOnFailureListener(emitter::onError));
    }
}
