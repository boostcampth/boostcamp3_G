package com.boostcamp.dreampicker.data.source.remote.firebase;

import com.boostcamp.dreampicker.R;
import com.boostcamp.dreampicker.data.model.User;
import com.boostcamp.dreampicker.data.model.UserDetail;
import com.boostcamp.dreampicker.data.source.UserDataSource;
import com.boostcamp.dreampicker.data.source.remote.firebase.response.PagedListResponse;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import androidx.annotation.NonNull;
import io.reactivex.Completable;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;

public class UserFirebaseService implements UserDataSource {

    private static final String COLLECTION_USERDETAIL = "userDetail";
    private static final String COLLECTION_USER = "user";

    private static volatile UserFirebaseService INSTANCE;

    private UserFirebaseService() { }

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
    public Single<UserDetail> getProfileUserDetail(@NonNull String userId) {

        // TODO :테스트 필요
        return Single.create(emitter -> FirebaseFirestore.getInstance().collection(COLLECTION_USERDETAIL)
                .whereEqualTo("id", userId)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())) {
                            emitter.onSuccess(document.toObject(UserDetail.class));
                        }
                    } else {
                        emitter.onError(task.getException());
                    }
                }).addOnFailureListener(emitter::onError));
    }

    @Override
    @NonNull
    public Single<PagedListResponse<User>> addProfileFollowingList(@NonNull String userId,
                                                                   int start,
                                                                   int display) {
        final List<User> followingList = new ArrayList<>();
        // TODO :테스트 필요
        return Single.create(emitter -> FirebaseFirestore.getInstance().collection(COLLECTION_USERDETAIL)
                .whereEqualTo("id", userId)
                .orderBy("date")
                .startAt(start)
                .limit(display)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())) {
                            followingList.add(document.toObject(User.class));
                        }
                        emitter.onSuccess(new PagedListResponse<>(start, display, followingList));
                    } else {
                        emitter.onError(task.getException());
                    }
                }).addOnFailureListener(emitter::onError));
    }

    @Override
    @NonNull
    public Single<PagedListResponse<User>> addProfileFollowerList(@NonNull String userId,
                                                                  int start,
                                                                  int display) {
        final List<User> followerList = new ArrayList<>();
        // TODO :테스트 필요
        return Single.create(emitter -> FirebaseFirestore.getInstance().collection(COLLECTION_USERDETAIL)
                .whereEqualTo("id", userId)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())) {
                            followerList.add(document.toObject(User.class));
                        }
                        emitter.onSuccess(new PagedListResponse<>(start, display, followerList));
                    } else {
                        emitter.onError(task.getException());
                    }
                })
                .addOnFailureListener(emitter::onError));
    }

    @Override
    @NonNull
    public Single<PagedListResponse<User>> addSearchUserList(@NonNull String searchKey,
                                                             int start,
                                                             int display) {
        // TODO :테스트 필요
        List<User> userList = new ArrayList<>();

//        for(int i = 0 ; i < pageUnit ; i++) {
//            User user = new User("userId",
//                    "사용자 " + (i+1),
//                    "https://img.sbs.co.kr/newimg/news/20170622/201061239_1280.jpg",
//                    R.drawable.profile);
//
//            userList.add(user);
//        }
// return Single.just(userList);
        return Single.create(emitter -> FirebaseFirestore.getInstance().collection(COLLECTION_USER)
                .whereEqualTo("name", searchKey)
                .get()
                .addOnSuccessListener(queryDocumentSnapshot -> {
                    DocumentSnapshot lastVisible = queryDocumentSnapshot.getDocuments()
                            .get(queryDocumentSnapshot.size() - 1);
                    Query next = FirebaseFirestore.getInstance().collection(COLLECTION_USER)
                            .orderBy("name")
                            .limit(display)
                            .startAt(lastVisible);
                    next.get()
                            .addOnSuccessListener(task -> {
                                for (DocumentSnapshot document : Objects.requireNonNull(task.getDocuments())) {
                                    userList.add(document.toObject(User.class));
                                }
                                emitter.onSuccess(new PagedListResponse<>(start, display, userList));

                            })
                            .addOnFailureListener(emitter::onError);
                    emitter.onSuccess(new PagedListResponse<>(start, display, userList));
                })
                .addOnFailureListener(emitter::onError));
    }

    @NonNull
    public Completable toggleUserFollow(@NonNull String userId,
                                        @NonNull String myUserId) {
        return Completable.create(emitter -> {});
    }
    @NonNull
    @Override
    public Single<User> getMyProfile() {
        //TODO : 유저정보 저장소 확인
//        return Single.create(emitter -> FirebaseFirestore.getInstance().collection(COLLECTION_USER)
//                .document()
//                .get()
//                .addOnSuccessListener(documentSnapshot -> { })
//                .addOnFailureListener(emitter::onError));
        // TODO : 임시데이터 삭제
        User user = new User("yoon",
                "라이언",
                "http://monthly.chosun.com/up_fd/Mdaily/2017-09/bimg_thumb/2017042000056_0.jpg",
                R.drawable.profile);

        return Single.just(user).subscribeOn(Schedulers.io());
    }

}
