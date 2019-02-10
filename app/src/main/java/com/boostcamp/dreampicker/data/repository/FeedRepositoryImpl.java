package com.boostcamp.dreampicker.data.repository;

import com.boostcamp.dreampicker.data.model.Feed;
import com.boostcamp.dreampicker.data.model.FeedUploadRequest;
import com.boostcamp.dreampicker.data.model.ProfileFeed;
import com.boostcamp.dreampicker.data.source.firestore.mapper.FeedResponseMapper;
import com.boostcamp.dreampicker.data.source.firestore.model.FeedRemoteData;
import com.boostcamp.dreampicker.utils.FirebaseManager;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.SetOptions;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import androidx.annotation.NonNull;
import io.reactivex.Completable;
import io.reactivex.Single;

public class FeedRepositoryImpl implements FeedRepository {
    private static final String COLLECTION_FEED = "feed";

    private static final String FIELD_USER_DATE = "date";
    private static final String FIELD_ENDED = "ended";

    @NonNull
    private final FirebaseFirestore db;

    private static volatile FeedRepositoryImpl INSTANCE;

    private FeedRepositoryImpl(@NonNull FirebaseFirestore db) {
        this.db = db;
    }

    public static FeedRepositoryImpl getInstance(@NonNull FirebaseFirestore db) {
        if (INSTANCE == null) {
            synchronized (FeedRepositoryImpl.class) {
                if (INSTANCE == null) {
                    INSTANCE = new FeedRepositoryImpl(db);
                }
            }
        }
        return INSTANCE;
    }

    @NonNull
    @Override
    public Single<List<Feed>> getNotEndedFeedList(@NonNull Date startAfter, int pageSize) {
        return Single.create(emitter -> db.collection(COLLECTION_FEED)
                .whereEqualTo(FIELD_ENDED, false)
                .orderBy(FIELD_USER_DATE, Query.Direction.DESCENDING) // 시간 정렬
                .startAfter(startAfter)
                .limit(pageSize)
                .get()
                .addOnCompleteListener(task -> {
                    final List<Feed> feedList = new ArrayList<>();
                    if (task.isSuccessful() && task.getResult() != null) {
                        for (final QueryDocumentSnapshot snapshots : task.getResult()) {
                            final FeedRemoteData data = snapshots.toObject(FeedRemoteData.class);
                            feedList.add(FeedResponseMapper.toFeed(snapshots.getId(), data));
                        }
                        emitter.onSuccess(feedList);
                    } else {
                        emitter.onError(task.getException());
                    }
                }));
    }

    @NonNull
    @Override
    public Completable vote(@NonNull String feedId, @NonNull String selectionId) {
        final DocumentReference docRef = db.collection(COLLECTION_FEED).document(feedId);

        return Completable.create(emitter ->
                db.runTransaction(transaction -> {
                    final DocumentSnapshot snapshot = transaction.get(docRef);
                    final FeedRemoteData data = snapshot.toObject(FeedRemoteData.class);

                    final String myId = FirebaseManager.getCurrentUserId();
                    if (data != null) {
                        final Map<String, String> map = data.getVotedUserMap();
                        map.put(myId, selectionId);
                        transaction.set(docRef, data, SetOptions.merge());
                    }
                    return null;
                }).addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        emitter.onComplete();
                    } else {
                        emitter.onError(task.getException());
                    }
                }));
    }

    @NonNull
    @Override
    public Single<Feed> getFeed(@NonNull String feedId) {
        return Single.create(emitter -> db.collection(COLLECTION_FEED)
                .document(feedId)
                .get()
                .addOnCompleteListener(task -> {
                    if(task.isSuccessful() && task.getResult() != null) {
                        final FeedRemoteData data = task.getResult().toObject(FeedRemoteData.class);
                        if(data != null && !data.isEnded()) {
                            emitter.onSuccess(FeedResponseMapper.toFeed(feedId, data));
                        } else {
                            emitter.onError(task.getException());
                        }
                    }
                }));
    }

    @NonNull
    @Override
    public Completable uploadFeed(@NonNull FeedUploadRequest feed) {
        return null;
    }

    @NonNull
    @Override
    public Single<List<ProfileFeed>> getFeedListByUserId(@NonNull String userId, Date startAfter, int pageSize) {
        return null;
    }
}
