package com.boostcamp.dreampicker.data.repository;

import android.net.Uri;

import com.boostcamp.dreampicker.data.model.Feed;
import com.boostcamp.dreampicker.data.model.FeedUploadRequest;
import com.boostcamp.dreampicker.data.model.ProfileFeed;
import com.boostcamp.dreampicker.data.source.firebase.model.FeedRemoteData;
import com.boostcamp.dreampicker.data.source.firebase.model.mapper.FeedMapper;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.Date;
import java.util.List;

import androidx.annotation.NonNull;
import io.reactivex.Completable;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;

public class FeedRepositoryImpl implements FeedRepository {

    @NonNull
    private static final String COLLECTION_FEED = "feed";
    private static final String FIELD_FEED_VOTESELECTIONITEM_IMAGEURL = "voteSelectionItem.imageURL";
    private static final String STORAGE_FEED_IMAGE_PATH = "feedImages";
    private final FirebaseFirestore firestore;

    private static FeedRepositoryImpl INSTANCE = null;

    private FeedRepositoryImpl(@NonNull FirebaseFirestore firestore) {
        this.firestore = firestore;
    }

    public static FeedRepositoryImpl getInstance(@NonNull FirebaseFirestore firestore) {
        if (INSTANCE == null) {
            synchronized (FeedRepositoryImpl.class) {
                if (INSTANCE == null) {
                    INSTANCE = new FeedRepositoryImpl(firestore);
                }
            }
        }
        return INSTANCE;
    }

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
    public Single<List<ProfileFeed>> getFeedListByUserId(@NonNull String userId, Date startAfter, int pageSize) {
        return null;
    }

    @NonNull
    @Override
    public Completable uploadFeed(@NonNull final FeedUploadRequest uploadFeed) {
        return Completable.create(emitter -> {

            FeedRemoteData feedRemoteData = FeedMapper.setFeed(uploadFeed);
            uploadImageStorage(feedRemoteData, Uri.parse(uploadFeed.getImagePathA()));
            uploadImageStorage(feedRemoteData, Uri.parse(uploadFeed.getImagePathB()));

            firestore.collection(COLLECTION_FEED)
                    .document(feedRemoteData.getId())
                    .set(feedRemoteData)
                    .addOnSuccessListener(documentReference -> emitter.onComplete())
                    .addOnFailureListener(Throwable::printStackTrace);

        }).subscribeOn(Schedulers.io());
    }

    private void uploadImageStorage(@NonNull final FeedRemoteData feed, final Uri uri) {

        StorageReference feedImages = FirebaseStorage.getInstance().getReference()
                .child(STORAGE_FEED_IMAGE_PATH + "/" + feed.getId() + "/" + uri.getLastPathSegment());

        feedImages.putFile(uri).continueWithTask(task -> {
            if (task.isSuccessful()) {
                return feedImages.getDownloadUrl();
            } else {
                throw task.getException();
            }
        }).addOnCompleteListener(result -> {
            if (result.isSuccessful()) {
                if (result.getResult() != null) {
                    firestore.collection(COLLECTION_FEED)
                            .document(feed.getId())
                            .update(FIELD_FEED_VOTESELECTIONITEM_IMAGEURL, result.getResult().toString());
                }
            }
        })
                .addOnFailureListener(Throwable::printStackTrace);

    }


}
