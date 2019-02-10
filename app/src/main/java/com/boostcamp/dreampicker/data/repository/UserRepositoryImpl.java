package com.boostcamp.dreampicker.data.repository;

import com.boostcamp.dreampicker.data.model.UserDetail;
import com.boostcamp.dreampicker.data.source.firestore.mapper.UserResponseMapper;
import com.boostcamp.dreampicker.data.source.firestore.response.UserDetailResponse;
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
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) { // 쿼리 성공

                                // result 없거나, document 비었을 때 null 반환
                                final UserDetailResponse response =
                                        task.getResult() == null || !task.getResult().exists()
                                        ? null : task.getResult().toObject(UserDetailResponse.class);

                                if (response != null) { // 결과 존재
                                    emitter.onSuccess(UserResponseMapper.toUserDetail(response));
                                } else { // 결과 없음
                                    emitter.onError(new Throwable("result is empty"));
                                }
                            } else {
                                // 쿼리 실패
                                emitter.onError(task.getException());
                            }
                        })
                        .addOnFailureListener(emitter::onError));
    }
}
