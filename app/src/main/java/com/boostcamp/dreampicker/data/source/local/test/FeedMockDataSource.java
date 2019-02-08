package com.boostcamp.dreampicker.data.source.local.test;

import android.net.Uri;
import android.util.Log;

import android.util.Log;

import com.boostcamp.dreampicker.R;
import com.boostcamp.dreampicker.data.model.Feed;
import com.boostcamp.dreampicker.data.model.Image;
import com.boostcamp.dreampicker.data.model.User;
import com.boostcamp.dreampicker.data.source.FeedDataSource;
import com.boostcamp.dreampicker.data.source.remote.firebase.response.PagedListResponse;
import com.boostcamp.dreampicker.utils.Constant;
import com.boostcamp.dreampicker.utils.IdCreator;
import com.google.firebase.firestore.FieldPath;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import androidx.annotation.NonNull;
import io.reactivex.Completable;
import io.reactivex.CompletableEmitter;
import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.internal.operators.observable.ObservableFlatMapSingle;
import io.reactivex.internal.operators.single.SingleFlatMap;
import io.reactivex.schedulers.Schedulers;

public class FeedMockDataSource implements FeedDataSource {
    private static final String COLLECTION_FEED = "feed";
    private static volatile FeedMockDataSource INSTANCE;
    private FirebaseStorage storage = FirebaseStorage.getInstance();

    private List<Feed> feedList = new ArrayList<>();

    private FeedMockDataSource() {
        feedList.addAll(createMockFeedData());
    }

    private int max = 0;

    public static FeedMockDataSource getInstance() {
        if (INSTANCE == null) {
            synchronized (FeedMockDataSource.class) {
                if (INSTANCE == null) {
                    INSTANCE = new FeedMockDataSource();
                }
            }
        }
        return INSTANCE;
    }

    private List<Feed> createMockFeedData() {
        final List<Feed> feedList = new ArrayList<>();

        Image image1 = new Image("image-0-up", R.drawable.image1, Arrays.asList("1번", "2번", "3번"));
        Image image2 = new Image("image-0-down", R.drawable.image2, Arrays.asList("1213112312", "2", "3", "4", "5", "6", "7"));
        User user1 = new User("user-0", "박신혜", R.drawable.profile);
        Feed feed1 = new Feed(
                IdCreator.createFeedId(),
                Arrays.asList(image1, image2),
                user1,
                "내일 소개남이랑 첫 데이트인데 장소 좀 골라주세요~!ㅎㅎ",
                "2018.01.28",
                false
        );

        Image image3 = new Image("image-1-up", R.drawable.jajang, Arrays.asList("짬뽕", "굿"));
        Image image4 = new Image("image-1-down", R.drawable.jambong, Arrays.asList("이것도짬뽕ㅎㅎ", "ㅋㅋㅋ"));
        User user2 = new User("user-1", "공유", R.drawable.profile2);

        Feed feed2 = new Feed(
                IdCreator.createFeedId(),
                Arrays.asList(image3, image4),
                user2,
                "짬뽕, 짜장면",
                "2019.01.29",
                false
        );

        feedList.add(feed1);
        feedList.add(feed2);

        return feedList;
    }

    @Override
    public Single<List<Feed>> getAllFeed() {
        return Single.just(feedList);
    }

    @Override
    public Single<List<Feed>> searchAllFeed(String searchKey) {
        return Single.just(feedList);
    }

    @Override
    @NonNull
    public Single<PagedListResponse<Feed>> addMainFeedList(int start, int display) {
        return Single.create(emitter -> {
            if(max < 1) {
                emitter.onSuccess(new PagedListResponse<>(start, display, feedList));
            } else {
                emitter.onError(new MaxPageException("마지막 페이지 에러"));
            }
        });
    }

    @Override
    @NonNull
    public Completable updateFeedVote(@NonNull String feedId, @Constant.VoteFlag int voteFlag) {
        for(int i=0; i<feedList.size(); i++) {
            final Feed feed = feedList.get(i);
            if(feed.getId().equals(feedId)) {
                final Feed f = new Feed(feed);
                Log.d("Melon", feed.getVoteFlag() + " -> " + voteFlag);
                if (f.getVoteFlag() == Constant.NONE) {
                    if(voteFlag == Constant.LEFT) {
                        f.setLeftCount(f.getLeftCount()+1);
                    } else {
                        f.setRightCount(f.getRightCount()+1);
                    }

                } else {
                    if(voteFlag == Constant.LEFT) {
                        f.setLeftCount(f.getLeftCount()+1);
                        f.setRightCount(f.getRightCount()-1);
                    } else {
                        f.setLeftCount(f.getLeftCount()-1);
                        f.setRightCount(f.getRightCount()+1);
                    }
                }
                f.setVoteFlag(voteFlag);
                feedList.set(i, f);
                break;
            }
        }
        return Completable.create(CompletableEmitter::onComplete);
    }

    @Override
    @NonNull
    public Single<PagedListResponse<Feed>> addSearchFeedList(@NonNull String searchKey,
                                                             int start,
                                                             int display) {
        List<Feed> feedList = new ArrayList<>();
        for (int i = 0; i < display; i++) {
            Image image1 = new Image("image-0-up", R.drawable.image1, "카페");
            Image image2 = new Image("image-0-down", R.drawable.image2, "술집");
            User user1 = new User("user-0", "박신혜", R.drawable.profile);
            Feed feed = new Feed(
                    "feed-0",
                    Arrays.asList(image1, image2),
                    user1,
                    "내일 소개남이랑 첫 데이트인데 장소 좀 골라주세요~!ㅎㅎ",
                    "2018.01.28",
                    false
            );

            feedList.add(feed);
        }

        return Single.just(new PagedListResponse<>(start, display, feedList));
    }

    @Override
    @NonNull
    public Single<PagedListResponse<Feed>> addProfileFeedList(@NonNull String userId,
                                                              int start,
                                                              int display) {
        return Single.create(emitter ->
                FirebaseFirestore.getInstance()
                        .collection(COLLECTION_FEED)
                        .whereEqualTo(FieldPath.of("user", "id"), userId)
                        .orderBy("date")
                        .startAt(start)
                        .limit(display)
                        .get()
                        .addOnCompleteListener(documentSnapshot -> {
                            if (documentSnapshot.isSuccessful()) {
                                // 결과 리스트
                                final QuerySnapshot result = Objects.requireNonNull(documentSnapshot.getResult());
                                List<Feed> feedList = new ArrayList<>();
                                for (QueryDocumentSnapshot document : result) {
                                    feedList.add(document.toObject(Feed.class));
                                }
                                emitter.onSuccess(new PagedListResponse<>(start, result.size(), feedList));
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

        return Completable.create(emitter -> { });
    }

    @NonNull
    @Override
    public Completable upLoadFeed(@NonNull Feed feed) {
        return Completable.create(emitter -> {

            for (int i=0;i<2;i++) {
                uploadImageStorage(feed,feed.getImageList().get(i).getUri());
                feed.getImageList().get(i).setUri(null);
            }

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
                .child("feedImage/" + feed.getId() + "/" + uri.getLastPathSegment());

        feedImages.putFile(uri)
                .addOnSuccessListener(taskSnapshot -> {
                    // TODO : 구조 수정필요
                    HashMap<String, String> tmp = new HashMap<>();
                    tmp.put("leftImagePath", uri.getPath());

                    FirebaseFirestore.getInstance()
                            .collection(COLLECTION_FEED)
                            .document(feed.getId())
                            .set(tmp, SetOptions.merge());
                })
                .addOnProgressListener(taskSnapshot -> {
                    double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                }).addOnPausedListener(taskSnapshot -> { })
                .addOnFailureListener(Throwable::printStackTrace);


    }

    private class MaxPageException extends Exception {
        public MaxPageException() {

        }

        public MaxPageException(String message) {
            super(message);
        }
    }

}
