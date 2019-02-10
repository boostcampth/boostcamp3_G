package com.boostcamp.dreampicker.data.repository;

import com.boostcamp.dreampicker.data.model.Feed;
import com.boostcamp.dreampicker.data.model.FeedUploadRequest;
import com.boostcamp.dreampicker.data.model.ProfileFeed;
import com.boostcamp.dreampicker.data.source.firestore.mapper.FeedResponseMapper;
import com.boostcamp.dreampicker.data.source.firestore.model.FeedRemoteData;
import com.boostcamp.dreampicker.data.source.firestore.response.FeedResponse;
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
import androidx.annotation.Nullable;
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
        if(INSTANCE == null) {
            synchronized (FeedRepositoryImpl.class) {
                if(INSTANCE == null) {
                    INSTANCE = new FeedRepositoryImpl(db);
                }
            }
        }
        return INSTANCE;
    }
    @NonNull
    @Override
    public Single<FeedResponse> getNotEndedMyFollowerFeedList(@Nullable List<String> followerList,
                                                              @NonNull Date startAfter,
                                                              int pageSize) {
        if(followerList == null || followerList.isEmpty()) { // 본인 팔로워가 없는 경우
            return Single.just(new FeedResponse(FeedResponse.FEED_EMPTY));
        }
        final List<Feed> feedList = new ArrayList<>();

        return Single.create(emitter ->
                db.collection(COLLECTION_FEED)
                        .whereEqualTo(FIELD_ENDED, false)
                        .orderBy(FIELD_USER_DATE, Query.Direction.DESCENDING) // 시간 정렬
                        .startAfter(startAfter)
                        .get()
                        .addOnCompleteListener(task -> {
                            if(task.isSuccessful() && task.getResult() != null) {
                                int count = 0;
                                for(final QueryDocumentSnapshot snapshots : task.getResult()) {
                                    final FeedRemoteData data = snapshots.toObject(FeedRemoteData.class);
                                    if(count >= pageSize) {
                                        break;
                                    }
                                    if (followerList.contains(data.getUser().getId())) { // 팔로워 확인
                                        feedList.add(FeedResponseMapper.getFeed(data));
                                        count++;
                                    }
                                }
                                if(feedList.isEmpty()) { // 팔로워의 피드가 없는 경우
                                    emitter.onSuccess(new FeedResponse(FeedResponse.FEED_EMPTY));
                                } else { // 피드 가져오기 성공
                                    emitter.onSuccess(new FeedResponse(FeedResponse.FEED_SUCCESS, feedList));
                                }
                            } else {
                                emitter.onError(task.getException());
                            }
                        }).addOnFailureListener(emitter::onError));
    }
    @NonNull
    @Override
    public Single<FeedResponse> vote(@NonNull String feedId, @NonNull String selectionId) {
        final DocumentReference docRef = db.collection(COLLECTION_FEED).document(feedId);
        return Single.create(emitter ->
                db.runTransaction(transaction -> {
                    final DocumentSnapshot snapshot = transaction.get(docRef);
                    final FeedRemoteData data = snapshot.toObject(FeedRemoteData.class);

                    if (data != null && FirebaseManager.getCurrentUserId() != null) {
                        if (data.isEnded()) { // 마감
                            return new FeedResponse(FeedResponse.FEED_ENDED);
                        }
                        final Map<String, String> map = data.getVotedUserMap();
                        map.put(FirebaseManager.getCurrentUserId(), selectionId);
                        transaction.set(docRef, data, SetOptions.merge());

                        return new FeedResponse(FeedResponse.FEED_SUCCESS);
                    }
                    return new FeedResponse(FeedResponse.FEED_ERROR);
                }).addOnSuccessListener(emitter::onSuccess)
                        .addOnFailureListener(emitter::onError));
    }

    @NonNull
    @Override
    public Single<FeedResponse> getFeed(@NonNull String feedId) {
        return Single.create(emitter ->
                db.collection(COLLECTION_FEED).document(feedId)
                        .get()
                        .addOnSuccessListener(snapshot -> {
                            final FeedRemoteData data = snapshot.toObject(FeedRemoteData.class);
                            if(data != null) {
                                if(data.isEnded()) {
                                    emitter.onSuccess(new FeedResponse(FeedResponse.FEED_ENDED));
                                } else {
                                    final Feed feed = FeedResponseMapper.getFeed(data);
                                    emitter.onSuccess(new FeedResponse(FeedResponse.FEED_SUCCESS, feed));
                                }
                            } else {
                                emitter.onSuccess(new FeedResponse(FeedResponse.FEED_ERROR));
                            }
                        }).addOnFailureListener(emitter::onError));
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
