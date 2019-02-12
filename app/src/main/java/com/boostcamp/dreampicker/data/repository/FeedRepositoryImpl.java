package com.boostcamp.dreampicker.data.repository;

import android.net.Uri;

import com.boostcamp.dreampicker.data.model.Feed;
import com.boostcamp.dreampicker.data.model.FeedUploadRequest;
import com.boostcamp.dreampicker.data.model.MyFeed;
import com.boostcamp.dreampicker.data.model.User;
import com.boostcamp.dreampicker.data.source.firebase.model.MyFeedRemoteData;
import com.boostcamp.dreampicker.data.source.firebase.model.mapper.FeedMapper;
import com.boostcamp.dreampicker.data.source.firestore.mapper.FeedResponseMapper;
import com.boostcamp.dreampicker.data.source.firestore.model.FeedRemoteData;
import com.boostcamp.dreampicker.utils.FirebaseManager;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.firestore.WriteBatch;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import androidx.annotation.NonNull;
import io.reactivex.Completable;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;

public class FeedRepositoryImpl implements FeedRepository {


    private static final String COLLECTION_FEED = "feed";
    private static final String COLLECTION_USER = "user";
    private static final String SUBCOLLECTION_MYFEEDS = "myFeeds";
    private static final String STORAGE_FEED_IMAGE_PATH = "feedImages";

    private static final String FIELD_DATE = "date";
    private static final String FIELD_ENDED = "ended";

    @NonNull
    private final FirebaseFirestore firestore;
    @NonNull
    private final FirebaseStorage storage;

    private static volatile FeedRepositoryImpl INSTANCE = null;

    private FeedRepositoryImpl(@NonNull FirebaseFirestore firestore,
                               @NonNull FirebaseStorage storage) {
        this.firestore = firestore;
        this.storage = storage;
    }

    public static FeedRepositoryImpl getInstance(@NonNull FirebaseFirestore firestore,
                                                 @NonNull FirebaseStorage storage) {
        if (INSTANCE == null) {
            synchronized (FeedRepositoryImpl.class) {
                if (INSTANCE == null) {
                    INSTANCE = new FeedRepositoryImpl(firestore, storage);
                }
            }
        }
        return INSTANCE;
    }

    @NonNull
    @Override
    public Single<List<Feed>> getNotEndedFeedList(@NonNull final String userId,
                                                  @NonNull final Date startAfter, final int pageSize) {
        final Single<List<Feed>> single = Single.create(emitter ->
                firestore.collection(COLLECTION_FEED)
                        .whereEqualTo(FIELD_ENDED, false)
                        .orderBy(FIELD_DATE, Query.Direction.DESCENDING) // 시간 정렬
                        .startAfter(startAfter)
                        .limit(pageSize)
                        .get()
                        .addOnSuccessListener(snapshots -> {
                            final List<Feed> feedList = new ArrayList<>();
                            for (DocumentSnapshot snapshot : snapshots.getDocuments()) {
                                final FeedRemoteData data = snapshot.toObject(FeedRemoteData.class);
                                if (data != null) {
                                    feedList.add(FeedResponseMapper.toFeed(userId, snapshot.getId(), data));
                                } else {
                                    emitter.onError(new IllegalArgumentException("FeedRemoteData is Null"));
                                }
                            }
                            emitter.onSuccess(feedList);
                        }).addOnFailureListener(emitter::onError));

        return single.subscribeOn(Schedulers.io());
    }

    @NonNull
    @Override
    public Single<Feed> vote(@NonNull final String userId,
                             @NonNull final String feedId,
                             @NonNull final String selectionId) {
        final DocumentReference docRef = firestore.collection(COLLECTION_FEED).document(feedId);

        final Single<Feed> single = Completable.create(emitter ->
                firestore.runTransaction(transaction -> {
                    final DocumentSnapshot snapshot = transaction.get(docRef);
                    final FeedRemoteData data = snapshot.toObject(FeedRemoteData.class);

                    if (data != null) {
                        final Map<String, String> map = data.getVotedUserMap();
                        map.put(userId, selectionId);
                        transaction.set(docRef, data, SetOptions.merge());
                    }
                    return null;
                })
                        .addOnSuccessListener(e -> emitter.onComplete())
                        .addOnFailureListener(emitter::onError))
                .andThen(Single.create(emitter -> docRef.get()
                        .addOnSuccessListener(snapshot -> {
                            if (snapshot != null) {
                                final FeedRemoteData data = snapshot.toObject(FeedRemoteData.class);
                                if (data != null) {
                                    final Feed feed = FeedResponseMapper.toFeed(userId, feedId, data);
                                    emitter.onSuccess(feed);
                                }
                            } else {
                                emitter.onError(new IllegalArgumentException("Snapshot or FeedRemoteData error"));
                            }
                        }).addOnFailureListener(emitter::onError)));

        return single.subscribeOn(Schedulers.io());
    }


    @NonNull
    @Override
    public Completable uploadFeed(@NonNull final FeedUploadRequest uploadFeed) {

        return Single
                .zip(uploadImageStorage(Uri.parse(uploadFeed.getImagePathA())),
                        uploadImageStorage(Uri.parse(uploadFeed.getImagePathB()))
                        , (imageUrlA, imageUrlB) -> FeedMapper.toFeed(uploadFeed, imageUrlA, imageUrlB))
                .flatMapCompletable(feedRemoteData ->
                        Completable.create(emitter -> {
                            final String writerId = FirebaseManager.getCurrentUserId();
                            if(writerId==null) emitter.onError(new IllegalArgumentException("no User error"));

                            WriteBatch batch = firestore.batch();
                            DocumentReference feedRef = firestore.collection(COLLECTION_FEED).document();
                            DocumentReference userRef = firestore.collection(COLLECTION_USER).document(writerId)
                                    .collection(SUBCOLLECTION_MYFEEDS).document(feedRef.getId());

                            batch.set(feedRef, feedRemoteData);
                            batch.set(userRef, new MyFeedRemoteData(
                                    feedRemoteData.getContent(),
                                    feedRemoteData.getDate(),
                                    feedRemoteData.getItemA().getImageUrl(),
                                    feedRemoteData.getItemB().getImageUrl(),
                                    false));
                            batch.commit()
                                    .addOnSuccessListener(aVoid -> emitter.onComplete())
                                    .addOnFailureListener(emitter::onError);
                        }))
                .subscribeOn(Schedulers.io());

    }

    @NonNull
    private Single<String> uploadImageStorage(final Uri uri) {
        return Single.create(emitter -> {
            StorageReference feedImages = storage.getReference()
                    .child(STORAGE_FEED_IMAGE_PATH + "/" + uri.getLastPathSegment());

            feedImages
                    .putFile(uri)
                    .continueWithTask(task -> {
                        if (task.isSuccessful()) {
                            return feedImages.getDownloadUrl();
                        } else {
                            throw task.getException();
                        }
                    }).addOnCompleteListener(result -> {
                if (result.isSuccessful()) {
                    if (result.getResult() != null) {
                        emitter.onSuccess(result.getResult().toString());
                    }
                } else {
                    emitter.onError(new IllegalArgumentException());
                }
            });
        });
    }
}
