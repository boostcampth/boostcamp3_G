package com.boostcamp.dreampicker.data.source.remote.firebase;

import android.net.Uri;

import com.boostcamp.dreampicker.data.model.Feed;
import com.boostcamp.dreampicker.data.source.FeedDataSource;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnPausedListener;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import androidx.annotation.NonNull;
import io.reactivex.Single;

public class FeedFirebaseService implements FeedDataSource {

    private static final String COLLECTION_FEED = "feed";

    private static volatile FeedFirebaseService INSTANCE;

    private FeedFirebaseService() { }

    public static FeedFirebaseService getInstance() {
        if(INSTANCE == null){
            synchronized (FeedFirebaseService.class){
                if(INSTANCE == null){
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
                .whereEqualTo("name",searchKey)
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
    public Single<List<Feed>> addMainFeedList(String userId, int pageIndex, int pageUnit) {
        // TODO : 결과 테스트 필요 DONE
        final List<Feed> feeds = new ArrayList<>();
        return Single.create(emitter -> {
           FirebaseFirestore.getInstance().collection(COLLECTION_FEED)
                    // 투표 마감 안된거
                    .whereEqualTo("isEnded",false)
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
    public void updateFeedVote(String feedId, String userId, int voteFlag) {
        // TODO : 테스트
        FirebaseFirestore.getInstance().collection(COLLECTION_FEED)
                .document(feedId)
                .update("votedUserMap."+userId,voteFlag);
    }

    @Override
    public Single<List<Feed>> addSearchFeedList(String searchKey, int pageIndex, int pageUnit) {
        List<Feed> feeds = new ArrayList<>();
        // TODO : 테스트
        return Single.create(emitter -> FirebaseFirestore.getInstance().collection(COLLECTION_FEED)

                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())) {
                            Feed feed = document.toObject(Feed.class);
                            if(feed.getContent().contains(searchKey)) feeds.add(feed);
                        }
                        emitter.onSuccess(feeds);
                    } else {
                        emitter.onError(task.getException());
                    }
                })
                .addOnFailureListener(emitter::onError));
    }

    @Override
    public Single<List<Feed>> addProfileFeedList(String userId, int pageIndex, int pageUnit) {
        List<Feed> feedKeyList = new ArrayList<>();
        // TODO: 테스트
        return Single.create(emitter -> FirebaseFirestore.getInstance().collection(COLLECTION_FEED)
                .whereEqualTo("id", userId)
                .orderBy("date")
                .startAt(pageIndex)
                .limit(pageUnit)
                .get()
                .addOnCompleteListener(documentSnapshot -> {
                    if (documentSnapshot.isSuccessful()) {
                        for (QueryDocumentSnapshot document : Objects.requireNonNull(documentSnapshot.getResult())) {
                            feedKeyList.add(document.toObject(Feed.class));
                        }
                        emitter.onSuccess(feedKeyList);
                    } else {
                        emitter.onError(documentSnapshot.getException());
                    }
                })
                .addOnFailureListener(emitter::onError));
    }

    @Override
    public void toggleFeedState(String feedId, boolean isEnded) {
        // TODO : 테스트
        FirebaseFirestore.getInstance().collection(COLLECTION_FEED)
                .document(feedId)
                .update("isEnded",isEnded)
                .addOnFailureListener(Throwable::printStackTrace);
    }

    @Override
    public void upLoadFeed(Feed feed,String url) {
        // TODO : 스토리지에 업로드
        FirebaseStorage storage = FirebaseStorage.getInstance();
        // Create a storage reference from our app
        StorageReference storageRef = storage.getReference();
        Uri file = Uri.fromFile(new File(feed.getImageList().get(0).getImageUrl()));
        StorageReference feedImages = storageRef.child(feed.getImageList().get(0).getImageUrl());

        UploadTask uploadTask = feedImages.putFile(file);
        uploadTask.addOnSuccessListener(taskSnapshot -> feed.getImageList().get(0).setImageUrl(feedImages.getPath()))
                .addOnProgressListener(taskSnapshot -> {
                    double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                }).addOnPausedListener(taskSnapshot -> {
            Uri sessionUri = taskSnapshot.getUploadSessionUri();
            boolean mSaved = false;
            if (sessionUri != null && !mSaved) {
                mSaved = true;
                // A persisted session has begun with the server.
                // Save this to persistent storage in case the process dies.
            }
                    System.out.println("Upload is paused");
        })
                .addOnFailureListener(Throwable::printStackTrace);


        //업로드
        FirebaseFirestore.getInstance().collection(COLLECTION_FEED)
                .add(feed)
                .addOnSuccessListener(documentReference -> {})
                .addOnFailureListener(Throwable::printStackTrace);
    }
}
