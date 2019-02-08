package com.boostcamp.dreampicker.data.source.remote.firebase;

import android.net.Uri;

import com.boostcamp.dreampicker.data.model.Feed;
import com.boostcamp.dreampicker.data.source.FeedDataSource;
import com.boostcamp.dreampicker.data.source.remote.firebase.response.PagedListResponse;
import com.boostcamp.dreampicker.utils.FirebaseManager;
import com.google.firebase.firestore.FieldPath;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import androidx.annotation.NonNull;
import io.reactivex.Completable;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;

public class FeedFirebaseService implements FeedDataSource {

    private static final String COLLECTION_FEED = "feed";

    private final String FIELD_USER_ID = "id";
    private final String FIELD_USER_DATE = "date";
    private final String FIELD_USER_NAME = "name";
    private final String FIELD_USER_ISENDED = "isEnded";
    private final String FIELD_USER_USER = "user";
    private final String FIELD_FEED_IMAGEMAP = "imageMap";
    private final String FIELD_FEED_IMAGEMAP_IMAGEURL = "imageUrl";

    private final String FEED_IMAGE_POSITION_LEFT = "left";
    private final String FEED_IMAGE_POSITION_RIGHT = "right";

    private final String STORAGE_FEED_IMAGE_PATH = "feedImage";

    private FirebaseStorage storage = FirebaseStorage.getInstance();

    private static volatile FeedFirebaseService INSTANCE;

    private FeedFirebaseService() {
    }

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
        return Single
                .create(emitter -> FirebaseFirestore.getInstance().collection(COLLECTION_FEED)
                        .get()
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())) {

                                    Feed feed = document.toObject(Feed.class);
                                    feeds.add(feed);
                                }
                                emitter.onSuccess(feeds);
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
                .whereEqualTo(FIELD_USER_NAME, searchKey)
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
    public Single<PagedListResponse<Feed>> addMainFeedList(
            int pageIndex,
            int pageUnit) {
        // TODO : 결과 테스트 필요 DONE
        return Single.create(emitter -> FirebaseFirestore.getInstance().collection(COLLECTION_FEED)
                .whereEqualTo(FIELD_USER_ISENDED, false)
                .startAt(pageIndex)
                .limit(pageUnit)
                .orderBy(FIELD_USER_DATE)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        final QuerySnapshot result = Objects.requireNonNull(task.getResult());
                        List<Feed> feedList = new ArrayList<>();
                        for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())) {
                            feedList.add(document.toObject(Feed.class));
                        }
                        emitter.onSuccess(new PagedListResponse<>(pageIndex, result.size(), feedList));
                    } else {
                        emitter.onError(task.getException());
                    }
                })
                .addOnFailureListener(emitter::onError));
    }

    @Override
    @NonNull
    public Completable updateFeedVote(@NonNull String feedId,
                                      int voteFlag) {

        return Completable.create(emitter -> {
            // TODO : 테스트
            FirebaseFirestore.getInstance().collection(COLLECTION_FEED)
                    .document(feedId)
                    .update("votedUserMap." + FirebaseManager.getCurrentUserId(), voteFlag)
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

        // TODO : 테스트
        return Single.create(emitter -> FirebaseFirestore.getInstance().collection(COLLECTION_FEED)

                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        final QuerySnapshot result = Objects.requireNonNull(task.getResult());
                        List<Feed> feedList = new ArrayList<>();
                        for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())) {
                            Feed feed = document.toObject(Feed.class);
                            if (feed.getContent().contains(searchKey)) feedList.add(feed);
                        }
                        emitter.onSuccess(new PagedListResponse<>(start, result.size(), feedList));
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

        return Single.create(emitter ->
                FirebaseFirestore.getInstance()
                        .collection(COLLECTION_FEED)
                        .whereEqualTo(FieldPath.of(FIELD_USER_USER, FIELD_USER_ID), userId)
                        .orderBy(FIELD_USER_DATE)
                        .startAt(start)
                        .limit(display)
                        .get()
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                // 결과 리스트
                                final QuerySnapshot result = Objects.requireNonNull(task.getResult());
                                List<Feed> feedList = new ArrayList<>();
                                for (QueryDocumentSnapshot document : result) {
                                    feedList.add(document.toObject(Feed.class));
                                }
                                emitter.onSuccess(new PagedListResponse<>(start, result.size(), feedList));
                            } else {
                                emitter.onError(task.getException());
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
                    .update(FIELD_USER_ISENDED, isEnded)
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

    @NonNull
    @Override
    public Completable upLoadFeed(@NonNull Feed feed) {
        return Completable.create(emitter -> {

            uploadImageStorage(feed, Objects.requireNonNull(feed.getImageMap().get(FEED_IMAGE_POSITION_LEFT)).getUri(), FEED_IMAGE_POSITION_LEFT);
            uploadImageStorage(feed, Objects.requireNonNull(feed.getImageMap().get(FEED_IMAGE_POSITION_RIGHT)).getUri(), FEED_IMAGE_POSITION_RIGHT);

            Objects.requireNonNull(feed.getImageMap().get(FEED_IMAGE_POSITION_LEFT)).setUri(null);
            Objects.requireNonNull(feed.getImageMap().get(FEED_IMAGE_POSITION_RIGHT)).setUri(null);
            FirebaseFirestore.getInstance().collection(COLLECTION_FEED)
                    .document(feed.getId())
                    .set(feed)
                    .addOnSuccessListener(documentReference -> emitter.onComplete())
                    .addOnFailureListener(Throwable::printStackTrace);


        }).subscribeOn(Schedulers.io());
    }

    private void uploadImageStorage(@NonNull Feed feed, Uri uri, String position) {

        StorageReference feedImages = storage.getReference()
                .child(STORAGE_FEED_IMAGE_PATH + "/" + feed.getId() + "/" + uri.getLastPathSegment());

        feedImages.putFile(uri)
                .addOnSuccessListener(taskSnapshot -> FirebaseFirestore.getInstance()
                        .collection(COLLECTION_FEED)
                        .document(feed.getId())
                        .update(FIELD_FEED_IMAGEMAP + "." + position + "." + FIELD_FEED_IMAGEMAP_IMAGEURL,
                                Objects.requireNonNull(taskSnapshot.getUploadSessionUri()).getPath()))
                .addOnProgressListener(taskSnapshot -> {
                    double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                }).addOnPausedListener(taskSnapshot -> {
        })
                .addOnFailureListener(Throwable::printStackTrace);
    }

}
