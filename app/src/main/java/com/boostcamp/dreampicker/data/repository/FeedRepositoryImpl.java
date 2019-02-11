package com.boostcamp.dreampicker.data.repository;

import com.boostcamp.dreampicker.data.model.Feed;
import com.boostcamp.dreampicker.data.model.FeedUploadRequest;
import com.boostcamp.dreampicker.data.model.MyFeed;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import androidx.annotation.NonNull;
import io.reactivex.Completable;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;

public class FeedRepositoryImpl implements FeedRepository {
    private static volatile FeedRepository INSTANCE = null;

    public static FeedRepository getInstance(@NonNull FirebaseFirestore firestore) {
        if (INSTANCE == null) {
            synchronized (FeedRepositoryImpl.class) {
                if (INSTANCE == null) {
                    INSTANCE = new FeedRepositoryImpl(firestore);
                }
            }
        }
        return INSTANCE;
    }

    private FeedRepositoryImpl(@NonNull FirebaseFirestore firestore) {
        this.firestore = firestore;
    }

    @NonNull
    private final FirebaseFirestore firestore;

    @NonNull
    @Override
    public Single<List<Feed>> getNotEndedMyFollowerFeedList(@NonNull Date startAfter, int pageSize) {
        return null;
    }

    @NonNull
    @Override
    public Single<Feed> vote(@NonNull String feedId, @NonNull String selectionId) {
        return null;
    }

    @NonNull
    @Override
    public Completable uploadFeed(@NonNull FeedUploadRequest feed) {
        return null;
    }
}
