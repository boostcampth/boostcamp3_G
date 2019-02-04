package com.boostcamp.dreampicker.data.source.remote.firebase;

import com.boostcamp.dreampicker.R;
import com.boostcamp.dreampicker.data.model.Feed;
import com.boostcamp.dreampicker.data.model.Image;
import com.boostcamp.dreampicker.data.model.User;
import com.boostcamp.dreampicker.data.source.FeedDataSource;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

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
        // 필터링 없이 feed 정보 다 가져옴
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

//        final List<Feed> feeds = new ArrayList<>();
//        return Single.create(emitter -> FirebaseFirestore.getInstance().collection(COLLECTION_FEED)
//                .whereEqualTo("name",searchKey)
//                .get()
//                .addOnCompleteListener(task -> {
//                    if (task.isSuccessful()) {
//                        for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())) {
//                            feeds.add(document.toObject(Feed.class));
//                            emitter.onSuccess(feeds);
//                        }
//                    } else {
//                        emitter.onError(task.getException());
//                    }
//                }));
        // TODO. 임시 코드 삭제
        List<Feed> feedList = new ArrayList<>();
        Image image1 = new Image("image-0-up", R.drawable.image1, "카페");
        Image image2 = new Image("image-0-down", R.drawable.image2, "술집");
        User user1 = new User("user-0", "박신혜", R.drawable.profile);
        Feed feed1 = new Feed(
                "feed-0",
                Arrays.asList(image1, image2),
                user1,
                "내일 소개남이랑 첫 데이트인데 장소 좀 골라주세요~!ㅎㅎ",
                "2018.01.28",
                false
        );

        Image image3 = new Image("image-1-up", R.drawable.jajang, "짜장면");
        Image image4 = new Image("image-1-down", R.drawable.jambong, "짬뽕");
        User user2 = new User("user-1", "공유", R.drawable.profile2);

        Feed feed2 = new Feed(
                "feed-1",
                Arrays.asList(image3, image4),
                user2,
                "짬뽕, 짜장면",
                "2019.01.29",
                false
        );

        feedList.add(feed1);
        feedList.add(feed2);
        feedList.add(feed1);
        feedList.add(feed2);

        return Single.just(feedList);
    }

    @Override
    public Single<List<Feed>> addMainFeedList(String userId, int pageIndex, int pageUnit) {
        // TODO : 결과 테스트 필요
        final List<Feed> feeds = new ArrayList<>();
        return Single.create(emitter -> {
            Query first = FirebaseFirestore.getInstance().collection(COLLECTION_FEED)
                    // 투표 마감 안된거
                    .whereEqualTo("isEnded",false)
                    .orderBy("date")
                    .limit(pageUnit);
            first.get()
                    .addOnSuccessListener(queryDocumentSnapshots -> {
                        DocumentSnapshot lastVisible = queryDocumentSnapshots.getDocuments()
                                .get(queryDocumentSnapshots.size()-1);

                        Query next = FirebaseFirestore.getInstance().collection(COLLECTION_FEED)
                                .orderBy("data")
                                .startAfter(lastVisible)
                                .limit(pageUnit);

                        next.addSnapshotListener((queryDocumentSnapshots1, e) -> {
                            if (e != null) {
                                emitter.onError(e);
                            }

                            if (queryDocumentSnapshots1 != null) {
                                for (DocumentChange document : Objects.requireNonNull(queryDocumentSnapshots1.getDocumentChanges())) {
                                    feeds.add(document.getDocument().toObject(Feed.class));
                                    emitter.onSuccess(feeds);
                                }
                            } else { // TODO : 결과없을때 처리
                            }

                        });

//                first.whereEqualTo("isEnded",false)
//                        .get()
//                        .addOnCompleteListener(task -> {
//                            if (task.isSuccessful()) {
//                                for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())) {
//                                    feeds.add(document.toObject(Feed.class));
//                                    emitter.onSuccess(feeds);
//                                }
//                            } else {
//                                emitter.onError(task.getException());
//                            }
//                        }));
                    });
        });
    }

    @Override
    public void updateFeedVote(String feedId, String userId, int voteFlag) {

        FirebaseFirestore.getInstance().collection(COLLECTION_FEED)
                .document(feedId)
                 //TODO : hashMap 수정 확인
                .update("votedMap",voteFlag)
                .addOnSuccessListener(aVoid -> { })
                .addOnFailureListener(e -> { });
    }

    @Override
    public Single<List<Feed>> addSearchFeedList(String searchKey, int pageIndex, int pageUnit) {
        List<Feed> feeds = new ArrayList<>();
        return Single.create(emitter -> FirebaseFirestore.getInstance().collection(COLLECTION_FEED)
                // TODO : 글 내용 검색어
                .whereEqualTo("content",searchKey)
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
    public Single<List<Feed>> addProfileFeedList(String userId, int pageIndex, int pageUnit) {
        List<Feed> feedKeyList = new ArrayList<>();
        // TODO: 리턴값확인, 인덱싱

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
                }));
    }

    @Override
    public void toggleFeedState(String feedId, boolean isEnded) {
        FirebaseFirestore.getInstance().collection(COLLECTION_FEED)
                .document(feedId)
                .update("isEnded",isEnded)
                .addOnSuccessListener(aVoid -> { })
                .addOnFailureListener(Throwable::printStackTrace);
    }
}
