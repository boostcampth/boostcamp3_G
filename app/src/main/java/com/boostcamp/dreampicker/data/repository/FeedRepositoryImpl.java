package com.boostcamp.dreampicker.data.repository;

import com.boostcamp.dreampicker.data.model.Feed;
import com.boostcamp.dreampicker.data.model.FeedUploadRequest;
import com.boostcamp.dreampicker.data.model.ProfileFeed;
import com.boostcamp.dreampicker.data.source.firestore.mapper.FeedResponseMapper;
import com.boostcamp.dreampicker.data.source.firestore.model.FeedRemoteData;
import com.boostcamp.dreampicker.data.source.firestore.response.FeedResponse;
import com.boostcamp.dreampicker.utils.FirebaseManager;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import io.reactivex.Completable;
import io.reactivex.Single;

public class FeedRepositoryImpl implements FeedRepository {
    private static final String COLLECTION_FEED = "feed";
    private static final String FIELD_USER_DATE = "date";

    private static volatile FeedRepositoryImpl INSTANCE;

    private FeedRepositoryImpl() { }

    public static FeedRepositoryImpl getInstance() {
        if(INSTANCE == null) {
            synchronized (FeedRepositoryImpl.class) {
                if(INSTANCE == null) {
                    INSTANCE = new FeedRepositoryImpl();
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
        final String myId = FirebaseManager.getCurrentUserId();

        return Single.create(emitter ->
                FirebaseFirestore.getInstance()
                        .collection(COLLECTION_FEED)
                        .whereEqualTo("ended", false)
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
                                    if(myId != null && myId.equals(data.getUser().getId())) { // 본인 제외
                                        continue;
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
    public Single<Feed> vote(@NonNull String feedId, @NonNull String selectionId) {
        return null;
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
