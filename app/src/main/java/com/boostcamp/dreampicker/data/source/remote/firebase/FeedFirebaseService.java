package com.boostcamp.dreampicker.data.source.remote.firebase;

import android.net.Uri;

import com.boostcamp.dreampicker.data.model.Feed;
import com.boostcamp.dreampicker.data.source.FeedDataSource;
import com.boostcamp.dreampicker.data.source.remote.firebase.response.PagedListResponse;
import com.google.firebase.firestore.FieldPath;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import androidx.annotation.NonNull;
import io.reactivex.Completable;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;

public class FeedFirebaseService implements FeedDataSource {

    private static final String COLLECTION_FEED = "feed";

    private FirebaseStorage storage = FirebaseStorage.getInstance();

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
        return Single.create(emitter -> FirebaseFirestore.getInstance().collection(COLLECTION_FEED)
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
                .addOnFailureListener(emitter::onError));
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

        // TODO: 테스트
        return Single.create(emitter -> FirebaseFirestore.getInstance().collection(COLLECTION_FEED)
                .whereEqualTo(FieldPath.of("user","id"), userId)
                .orderBy("date")
                .startAt(start)
                .limit(display)
                .get()
                .addOnCompleteListener(documentSnapshot -> {
                    if (documentSnapshot.isSuccessful()) {
                        final QuerySnapshot result = Objects.requireNonNull(documentSnapshot.getResult());
                        List<Feed> feedKeyList = new ArrayList<>();
                        for (QueryDocumentSnapshot document : Objects.requireNonNull(documentSnapshot.getResult())) {
                            feedKeyList.add(document.toObject(Feed.class));
                        }
                        emitter.onSuccess(new PagedListResponse<>(start, result.size(), feedKeyList));
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

    @NonNull
    @Override
    public Completable upLoadFeed(@NonNull Feed feed) {
        return Completable.create(emitter -> {

            uploadImageStorage(feed, feed.getImageMap().get("left").getUri());
            uploadImageStorage(feed, feed.getImageMap().get("right").getUri());
//
//            feed.getImageList().get(i).setUri(null);

            FirebaseFirestore.getInstance().collection(COLLECTION_FEED)
                    .document(feed.getId())
                    .set(feed)
                    .addOnSuccessListener(documentReference -> {})
                    .addOnFailureListener(Throwable::printStackTrace);

            emitter.onComplete();
        }).subscribeOn(Schedulers.io());
    }

    private void uploadImageStorage(@NonNull Feed feed, Uri uri){

        //임시경로
        StorageReference feedImages = storage.getReference()
                .child("feedImage/"+feed.getId()+"/"+uri.getLastPathSegment());

        feedImages.putFile(uri)
                .addOnSuccessListener(taskSnapshot -> {
                    // TODO : 구조 수정필요
                    HashMap<String,String> tmp = new HashMap<>();
                    tmp.put("leftImagePath", Objects.requireNonNull(taskSnapshot.getUploadSessionUri()).getPath());

                    FirebaseFirestore.getInstance()
                            .collection(COLLECTION_FEED)
                            .document(feed.getId())
                            .set(tmp, SetOptions.merge()); })
                .addOnProgressListener(taskSnapshot -> {
                    double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                }).addOnPausedListener(taskSnapshot -> { })
                .addOnFailureListener(Throwable::printStackTrace);
    }
}
