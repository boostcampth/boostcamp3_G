package com.boostcamp.dreampicker.data.repository;

import com.boostcamp.dreampicker.data.model.MyFeed;
import com.boostcamp.dreampicker.data.model.UserDetail;
import com.boostcamp.dreampicker.data.source.firestore.mapper.UserDetailMapper;
import com.boostcamp.dreampicker.data.source.firestore.model.UserDetailEntity;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import androidx.annotation.NonNull;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;

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
    private final String SUBCOLLECTION_MYFEEDS = "myFeeds";
    private final String FIELD_DATE = "date";


    private UserRepositoryImpl(@NonNull final FirebaseFirestore firestore) {
        this.firestore = firestore;
    }

    @NonNull
    @Override
    public Single<UserDetail> getUserDetail(@NonNull final String userId) {

        Single<UserDetail> single = Single.create(emitter ->
                firestore.collection(COLLECTION_USER).document(userId)
                        .get()
                        .addOnSuccessListener(document -> {
                            final UserDetailEntity response = document.exists()
                                    ? document.toObject(UserDetailEntity.class) : null;
                            if (response != null) {
                                emitter.onSuccess(UserDetailMapper
                                        .toUserDetail(document.getId(), response));
                            } else {
                                emitter.onError(new IllegalArgumentException());
                            }
                        })
                        .addOnFailureListener(emitter::onError));

        return single.subscribeOn(Schedulers.io());
    }

    @NonNull
    @Override
    public Single<List<MyFeed>> getFeedListByUserId(@NonNull String userId, Date startAfter, int pageSize) {

        Single<List<MyFeed>> single = Single.create(emitter ->
                firestore.collection(COLLECTION_USER).document(userId)
                        .collection(SUBCOLLECTION_MYFEEDS)
                        .orderBy(FIELD_DATE, Query.Direction.DESCENDING)
                        .startAfter(startAfter)
                        .limit(pageSize)
                        .get()
                        .addOnSuccessListener(documents -> {
                            List<MyFeed> feedList = new ArrayList<>();
                            for (DocumentSnapshot document : documents.getDocuments()) {
                                final MyFeed feed = document.toObject(MyFeed.class);
                                if (feed != null) {
                                    feed.setId(document.getId());
                                    feedList.add(feed);
                                }
                            }
                            emitter.onSuccess(feedList);
                        })
                        .addOnFailureListener(emitter::onError));

        return single.subscribeOn(Schedulers.io());
    }

}
