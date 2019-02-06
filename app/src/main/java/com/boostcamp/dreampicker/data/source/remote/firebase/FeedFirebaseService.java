package com.boostcamp.dreampicker.data.source.remote.firebase;

import com.boostcamp.dreampicker.data.model.Feed;
import com.boostcamp.dreampicker.data.source.FeedDataSource;
import com.boostcamp.dreampicker.data.source.remote.firebase.response.PagedListResponse;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import androidx.annotation.NonNull;
import io.reactivex.Completable;
import io.reactivex.Single;

public class FeedFirebaseService implements FeedDataSource {

    private static final String COLLECTION_FEED = "feed";

    private static volatile FeedFirebaseService INSTANCE;

    private FeedFirebaseService() { }

    public static FeedFirebaseService getInstance() {
        if (INSTANCE == null) {
            synchronized (FeedFirebaseService.class) {
                if (INSTANCE == null) {
                    INSTANCE = new FeedFirebaseService();
                }
            }
        }
        return INSTANCE;
    }

    @Override
    public Single<List<Feed>> getAllFeed() {
        // TODO : 삭제
        final List<Feed> feeds = new ArrayList<>();
        return Single.create(emitter -> FirebaseFirestore.getInstance().collection(COLLECTION_FEED)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())) {
                            feeds.add(document.toObject(Feed.class));
                            emitter.onSuccess(feeds);
                        }
                    } else {
                        emitter.onError(task.getException());
                    }
                }));
    }

    @Override
    public Single<List<Feed>> searchAllFeed(String searchKey) {
        // TODO : 삭제
        final List<Feed> feeds = new ArrayList<>();
        return Single.create(emitter -> FirebaseFirestore.getInstance().collection(COLLECTION_FEED)
                .whereEqualTo("name", searchKey)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())) {
                            feeds.add(document.toObject(Feed.class));
                            emitter.onSuccess(feeds);
                        }
                    } else {
                        emitter.onError(task.getException());
                    }
                }));

    }

    @Override
    @NonNull
    public Single<List<Feed>> addMainFeedList(@NonNull String userId,
                                              int pageIndex,
                                              int pageUnit) {
        // TODO : 결과 테스트 필요 DONE
        final List<Feed> feeds = new ArrayList<>();
        return Single.create(emitter -> {
            FirebaseFirestore.getInstance().collection(COLLECTION_FEED)
                    // 투표 마감 안된거
                    .whereEqualTo("isEnded", false)
                    .startAt(pageIndex)
                    .limit(pageUnit)
                    .orderBy("date")
                    .get()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())) {
                                feeds.add(document.toObject(Feed.class));
                            }
                            emitter.onSuccess(feeds);
                        } else {
                            emitter.onError(task.getException());
                        }
                    })
                    .addOnFailureListener(emitter::onError);
        });
    }

    @Override
    @NonNull
    public Completable updateFeedVote(@NonNull String feedId,
                                      @NonNull String userId,
                                      int voteFlag) {

        return Completable.create(emitter -> {
            // TODO : 테스트
            FirebaseFirestore.getInstance().collection(COLLECTION_FEED)
                    .document(feedId)
                    .update("votedUserMap." + userId, voteFlag)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            emitter.onComplete();
                        } else {
                            emitter.onError(task.getException());
                        }
                    })
                    .addOnFailureListener(emitter::onError);
        });
    }

    @Override
    @NonNull
    public Single<PagedListResponse<Feed>> addSearchFeedList(@NonNull String searchKey,
                                                             int start,
                                                             int display) {
        List<Feed> feeds = new ArrayList<>();
        // TODO : 테스트
        return Single.create(emitter -> FirebaseFirestore.getInstance().collection(COLLECTION_FEED)

                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())) {
                            Feed feed = document.toObject(Feed.class);
                            if (feed.getContent().contains(searchKey)) feeds.add(feed);
                        }
                        emitter.onSuccess(new PagedListResponse<>(start, display, feeds));
                    } else {
                        emitter.onError(task.getException());
                    }
                })
                .addOnFailureListener(emitter::onError));
    }

    @Override
    @NonNull
    public Single<PagedListResponse<Feed>> addProfileFeedList(@NonNull String userId,
                                                              int start,
                                                              int display) {
        List<Feed> feedKeyList = new ArrayList<>();
        // TODO: 테스트
        return Single.create(emitter -> FirebaseFirestore.getInstance().collection(COLLECTION_FEED)
                .whereEqualTo("id", userId)
                .orderBy("date")
                .startAt(start)
                .limit(display)
                .get()
                .addOnCompleteListener(documentSnapshot -> {
                    if (documentSnapshot.isSuccessful()) {
                        for (QueryDocumentSnapshot document : Objects.requireNonNull(documentSnapshot.getResult())) {
                            feedKeyList.add(document.toObject(Feed.class));
                        }
                        emitter.onSuccess(new PagedListResponse<>(start, display, feedKeyList));
                    } else {
                        emitter.onError(documentSnapshot.getException());
                    }
                })
                .addOnFailureListener(emitter::onError));
    }

    @Override
    @NonNull
    public Completable toggleFeedState(@NonNull String feedId,
                                       boolean isEnded) {

        return Completable.create(emitter -> {
            // TODO : 테스트
            FirebaseFirestore.getInstance().collection(COLLECTION_FEED)
                    .document(feedId)
                    .update("isEnded", isEnded)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            emitter.onComplete();
                        } else {
                            emitter.onError(task.getException());
                        }
                    })
                    .addOnFailureListener(emitter::onError);
        });
    }
}
