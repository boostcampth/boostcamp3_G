package com.boostcamp.dreampicker.data.source.remote.firebase;

import android.net.Uri;

import com.boostcamp.dreampicker.data.model.Feed;
import com.boostcamp.dreampicker.data.source.FeedDataSource;
import com.boostcamp.dreampicker.data.source.remote.firebase.response.PagedListResponse;
import com.boostcamp.dreampicker.utils.Constant;
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

    private static final String FIELD_USER_ID = "id";
    private static final String FIELD_USER_DATE = "date";
    private static final String FIELD_USER_ISENDED = "ended";
    private static final String FIELD_USER_USER = "user";
    private static final String FIELD_FEED_IMAGEMAP = "imageMap";
    private static final String FIELD_FEED_IMAGEMAP_IMAGEURL = "imageUrl";

    private static final String STORAGE_FEED_IMAGE_PATH = "feedImage";

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
    @NonNull
    public Single<PagedListResponse<Feed>> addMainFeedList(
            int pageIndex,
            int pageUnit) {

        return Single.create(emitter -> FirebaseFirestore.getInstance().collection(COLLECTION_FEED)
                .whereEqualTo(FIELD_USER_ISENDED, false)
                //.startAt(pageIndex)
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
                .whereArrayContains("content", searchKey)
                .startAt(start)
                .limit(display)
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

            uploadImageStorage(feed, Objects.requireNonNull(feed.getImageMap().get(Constant.IMAGE_LEFT)).getUri(), Constant.IMAGE_LEFT);
            uploadImageStorage(feed, Objects.requireNonNull(feed.getImageMap().get(Constant.IMAGE_RIGHT)).getUri(), Constant.IMAGE_RIGHT);
            Objects.requireNonNull(feed.getImageMap().get(Constant.IMAGE_LEFT)).setUri(null);
            Objects.requireNonNull(feed.getImageMap().get(Constant.IMAGE_RIGHT)).setUri(null);

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

        feedImages.putFile(uri).continueWithTask(task -> {
            if (task.isSuccessful()) return feedImages.getDownloadUrl();
            else throw Objects.requireNonNull(task.getException());
        })
                .addOnCompleteListener(result -> {
                    if (result.isSuccessful()) {
                        if (result.getResult() != null) {
                            FirebaseFirestore.getInstance()
                                    .collection(COLLECTION_FEED)
                                    .document(feed.getId())
                                    .update(FIELD_FEED_IMAGEMAP + "." + position + "." + FIELD_FEED_IMAGEMAP_IMAGEURL,
                                            result.getResult().toString());
                        }
                    }
                })
                .addOnFailureListener(Throwable::printStackTrace);

    }
}
