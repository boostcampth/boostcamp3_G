package com.boostcamp.dreampicker.data.source.remote.firebase;

import com.boostcamp.dreampicker.data.model.LegacyUser;
import com.boostcamp.dreampicker.data.model.LegacyUserDetail;
import com.boostcamp.dreampicker.data.source.UserDataSource;
import com.boostcamp.dreampicker.data.source.remote.firebase.request.InsertUserRequest;
import com.boostcamp.dreampicker.data.source.remote.firebase.response.PagedListResponse;
import com.boostcamp.dreampicker.utils.FirebaseManager;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import androidx.annotation.NonNull;
import io.reactivex.Completable;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;

public class UserFirebaseService implements UserDataSource {

    private final String COLLECTION_USERDETAIL = "userDetail";
    private final String COLLECTION_USER = "user";

    private final String FIELD_USER_ID = "id";
    private final String FIELD_USER_DATE = "date";

    private static volatile UserFirebaseService INSTANCE;

    private UserFirebaseService() {
    }

    public static UserFirebaseService getInstance() {
        if (INSTANCE == null) {
            synchronized (UserFirebaseService.class) {
                if (INSTANCE == null) {
                    INSTANCE = new UserFirebaseService();
                }
            }
        }
        return INSTANCE;
    }

    @Override
    @NonNull
    public Single<LegacyUserDetail> getProfileUserDetail(@NonNull String userId) {

        // TODO :테스트 필요
        return Single.create(emitter -> FirebaseFirestore.getInstance().collection(COLLECTION_USERDETAIL)
                .document(userId)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (Objects.requireNonNull(document).exists()) {
                            emitter.onSuccess(document.toObject(LegacyUserDetail.class));
                        } else {
                            emitter.onError(task.getException());
                        }
                    } else {
                        emitter.onError(task.getException());
                    }
                }).addOnFailureListener(emitter::onError));
    }

    @Override
    @NonNull
    public Single<PagedListResponse<LegacyUser>> addProfileFollowingList(@NonNull String userId,
                                                                         int start,
                                                                         int display) {
        // TODO :테스트 필요
        return Single.create(emitter -> FirebaseFirestore.getInstance().collection(COLLECTION_USERDETAIL)
                .whereEqualTo(FIELD_USER_ID, userId)
                .orderBy(FIELD_USER_DATE)
                .startAt(start)
                .limit(display)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        final QuerySnapshot result = Objects.requireNonNull(task.getResult());
                        List<LegacyUser> followingList = new ArrayList<>();
                        for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())) {
                            followingList.add(document.toObject(LegacyUser.class));
                        }
                        emitter.onSuccess(new PagedListResponse<>(start, result.size(), followingList));
                    } else {
                        emitter.onError(task.getException());
                    }
                }).addOnFailureListener(emitter::onError));
    }

    @Override
    @NonNull
    public Single<PagedListResponse<LegacyUser>> addProfileFollowerList(@NonNull String userId,
                                                                        int start,
                                                                        int display) {
        // TODO :테스트 필요
        return Single.create(emitter -> FirebaseFirestore.getInstance().collection(COLLECTION_USERDETAIL)
                .whereEqualTo(FIELD_USER_ID, userId)
                .orderBy(FIELD_USER_ID)
                .startAt(start)
                .limit(display)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        final QuerySnapshot result = Objects.requireNonNull(task.getResult());
                        List<LegacyUser> followerList = new ArrayList<>();
                        for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())) {
                            followerList.add(document.toObject(LegacyUser.class));
                        }
                        emitter.onSuccess(new PagedListResponse<>(start, result.size(), followerList));
                    } else {
                        emitter.onError(task.getException());
                    }
                })
                .addOnFailureListener(emitter::onError));
    }

    @Override
    @NonNull
    public Single<PagedListResponse<LegacyUser>> addSearchUserList(@NonNull String searchKey,
                                                                   int start,
                                                                   int display) {
        // TODO :테스트 필요
        return Single.create(emitter -> FirebaseFirestore.getInstance().collection(COLLECTION_USER)
                .whereEqualTo(FIELD_USER_ID, searchKey)
                .orderBy(FIELD_USER_ID)
                .startAt(start)
                .limit(display)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        // 결과 리스트
                        final QuerySnapshot result = Objects.requireNonNull(task.getResult());
                        List<LegacyUser> userList = new ArrayList<>();
                        for (QueryDocumentSnapshot document : result) {
                            userList.add(document.toObject(LegacyUser.class));
                        }
                        emitter.onSuccess(new PagedListResponse<>(start, result.size(), userList));
                    } else {
                        emitter.onError(task.getException());
                    }
                })
                .addOnFailureListener(emitter::onError));

    }

    @NonNull
    public Completable toggleUserFollow(@NonNull String userId,
                                        @NonNull String myUserId) {
        return Completable.create(emitter -> {
            FirebaseFirestore.getInstance().collection(COLLECTION_USER)
                    .document(userId)
                    .get()
                    .addOnSuccessListener(documentSnapshot -> {

                    })
                    .addOnFailureListener(emitter::onError);
        });
    }

    @NonNull
    @Override
    public Single<LegacyUser> getMyProfile() {
        //TODO : 유저정보 저장소 확인
        return Single.create(emitter -> FirebaseFirestore.getInstance().collection(COLLECTION_USER)
                .document(Objects.requireNonNull(FirebaseManager.getCurrentUserId()))
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (Objects.requireNonNull(document).exists()) {
                            emitter.onSuccess(document.toObject(LegacyUser.class));
                        } else {
                            emitter.onError(task.getException());
                        }
                    } else {
                        emitter.onError(task.getException());
                    }
                })
                .addOnFailureListener(emitter::onError));
//        // TODO : 임시데이터 삭제
//        User user = new User("yoon",
//                "라이언",
//                "http://monthly.chosun.com/up_fd/Mdaily/2017-09/bimg_thumb/2017042000056_0.jpg",
//                R.drawable.profile);
//
//        return Single.just(user).subscribeOn(Schedulers.io());
    }

    @NonNull
    @Override
    public Completable insertNewUser(@NonNull InsertUserRequest request) {
        return Completable.create(emitter ->
                FirebaseFirestore.getInstance()
                        .collection(COLLECTION_USER)
                        .document(request.getId())
                        .set(request)
                        .addOnSuccessListener(__ -> emitter.onComplete())
                        .addOnFailureListener(emitter::onError)
        ).subscribeOn(Schedulers.io());
    }
}
