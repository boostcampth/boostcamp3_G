package com.boostcamp.dreampicker.data.source.local.test;

import com.boostcamp.dreampicker.R;
import com.boostcamp.dreampicker.data.model.User;
import com.boostcamp.dreampicker.data.model.UserDetail;
import com.boostcamp.dreampicker.data.source.UserDataSource;
import com.boostcamp.dreampicker.data.source.remote.firebase.request.InsertUserRequest;
import com.boostcamp.dreampicker.data.source.remote.firebase.response.PagedListResponse;
import com.google.firebase.firestore.FieldPath;
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

public class UserMockDataSource implements UserDataSource {
    private static volatile UserMockDataSource INSTANCE;
    private String COLLECTION_USER = "user";

    private UserMockDataSource() { }

    public static UserMockDataSource getInstance() {
        if (INSTANCE == null) {
            synchronized (UserMockDataSource.class) {
                if (INSTANCE == null) {
                    INSTANCE = new UserMockDataSource();
                }
            }
        }
        return INSTANCE;
    }

    @Override
    @NonNull
    public Single<UserDetail> getProfileUserDetail(@NonNull String userId) {

        return Single.create(emitter ->
                FirebaseFirestore.getInstance().collection("user")
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
                        })
                        .addOnFailureListener(emitter::onError));
    }

    @Override
    @NonNull
    public Single<PagedListResponse<User>> addProfileFollowingList(@NonNull String userId,
                                                                   int start,
                                                                   int display) {
        List<User> userList = new ArrayList<>();
        for (int i = 0; i < display; i++) {
            User user = new User(
                    userId,
                    "yeseul",
                    "https://img.sbs.co.kr/newimg/news/20170622/201061239_1280.jpg",
                    R.drawable.profile);
            userList.add(user);
        }
        return Single.just(new PagedListResponse<>(start, display, userList));
    }

    @Override
    @NonNull
    public Single<PagedListResponse<User>> addProfileFollowerList(@NonNull String userId,
                                                                  int start,
                                                                  int display) {
        List<User> userList = new ArrayList<>();
        for (int i = 0; i < display; i++) {
            User user = new User(
                    userId,
                    "yeseul",
                    "https://img.sbs.co.kr/newimg/news/20170622/201061239_1280.jpg",
                    R.drawable.profile);
            userList.add(user);
        }
        return Single.just(new PagedListResponse<>(start, display, userList));
    }

    @Override
    @NonNull
    public Single<PagedListResponse<User>> addSearchUserList(@NonNull String searchKey,
                                                             int start,
                                                             int display) {
        return Single.create(emitter ->
                FirebaseFirestore.getInstance()
                        .collection(COLLECTION_USER)
                        .whereEqualTo(FieldPath.of("name"), searchKey)
                        .orderBy("id")
                        .startAt(start)
                        .limit(display)
                        .get()
                        .addOnCompleteListener(documentSnapshot -> {
                            if (documentSnapshot.isSuccessful()) {
                                // 결과 리스트
                                final QuerySnapshot result = Objects.requireNonNull(documentSnapshot.getResult());
                                List<User> userList = new ArrayList<>();
                                for (QueryDocumentSnapshot document : result) {
                                    userList.add(document.toObject(User.class));
                                }
                                emitter.onSuccess(new PagedListResponse<>(start, result.size(), userList));
                            } else {
                                emitter.onError(documentSnapshot.getException());
                            }
                        })
                        .addOnFailureListener(emitter::onError));
    }

    @Override
    @NonNull
    public Completable toggleUserFollow(@NonNull String userId,
                                        @NonNull String myUserId) {

        return Completable.create(emitter -> {});
    }

    @NonNull
    @Override
    public Single<User> getMyProfile() {
        User user = new User("yoon",
                "라이언",
                "http://monthly.chosun.com/up_fd/Mdaily/2017-09/bimg_thumb/2017042000056_0.jpg",
                R.drawable.profile);

        return Single.just(user).subscribeOn(Schedulers.io());
    }

    @NonNull
    @Override
    public Completable insertNewUser(@NonNull InsertUserRequest request) {
        return Completable.create(emitter ->
                FirebaseFirestore.getInstance()
                        .collection("user")
                        .document(request.getId())
                        .set(request)
                        .addOnSuccessListener(__ -> emitter.onComplete())
                        .addOnFailureListener(emitter::onError)
        ).subscribeOn(Schedulers.io());
    }
}
