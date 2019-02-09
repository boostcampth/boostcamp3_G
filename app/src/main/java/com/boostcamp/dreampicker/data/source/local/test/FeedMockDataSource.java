package com.boostcamp.dreampicker.data.source.local.test;

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
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import androidx.annotation.NonNull;
import io.reactivex.Completable;
import io.reactivex.CompletableEmitter;
import io.reactivex.Single;

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

        Image image1 = new Image("image-0-up",
                "http://monthly.chosun.com/up_fd/Mdaily/2017-09/bimg_thumb/2017042000056_0.jpg",
                Arrays.asList("1번", "2번", "3번"));
        Image image2 = new Image("image-0-down",
                "https://post-phinf.pstatic.net/MjAxNzA2MjhfODQg/MDAxNDk4NjU2MzgyNzcx.TVy7np7EVlKjuntLKN_qdwrGB8lGBdfMwkX6p3WV6rQg.6oHbF6lWH0iINgljB7CF3jSeYX342hsdl8fekB8yawsg.PNG/9.PNG?type=w1200",
                Arrays.asList("1213112312", "2"));

        Map<String, Image> imageMap = new HashMap<>();
        imageMap.put("left", image1);
        imageMap.put("right", image2);

        User user1 = new User("user-0", "박신혜", R.drawable.profile);
        Feed feed1 = new Feed(
                IdCreator.createFeedId(),
                imageMap,
                user1,
                "내일 소개남이랑 첫 데이트인데 장소 좀 골라주세요~!ㅎㅎ",
                "2018.01.28",
                false
        );

        Image image3 = new Image("image-1-up",
                "https://t1.daumcdn.net/friends/prod/category/M201_theme_flying11.png",
                Arrays.asList("짬뽕", "굿"));
        Image image4 = new Image("image-1-down",
                "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQkH0BPwI8ZSms_dTXMMrc9lU8OaO6oo729MzRe6HmjH7nUFDTn",
                Arrays.asList("이것도짬뽕ㅎㅎ", "ㅋㅋㅋ"));

        User user2 = new User("user-1", "공유", R.drawable.profile2);
        Map<String, Image> imageMap2 = new HashMap<>();
        imageMap2.put("left", image3);
        imageMap2.put("right", image4);

        Feed feed2 = new Feed(
                IdCreator.createFeedId(),
                imageMap2,
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
    @NonNull
    public Single<PagedListResponse<Feed>> addMainFeedList(int start, int display) {
        return Single.create(emitter -> {
            if (max < 1) {
                emitter.onSuccess(new PagedListResponse<>(start, display, feedList));
            } else {
                emitter.onError(new MaxPageException("마지막 페이지 에러"));
            }
        });
    }

    @Override
    @NonNull
    public Completable updateFeedVote(@NonNull String feedId, @Constant.VoteFlag int voteFlag) {
        for (int i = 0; i < feedList.size(); i++) {
            final Feed feed = feedList.get(i);
            if (feed.getId().equals(feedId)) {
                final Feed f = new Feed(feed);
                Log.d("Melon", feed.getVoteFlag() + " -> " + voteFlag);
                if (f.getVoteFlag() == Constant.NONE) {
                    if (voteFlag == Constant.LEFT) {
                        f.setLeftCount(f.getLeftCount() + 1);
                    } else {
                        f.setRightCount(f.getRightCount() + 1);
                    }

                } else {
                    if (voteFlag == Constant.LEFT) {
                        f.setLeftCount(f.getLeftCount() + 1);
                        f.setRightCount(f.getRightCount() - 1);
                    } else {
                        f.setLeftCount(f.getLeftCount() - 1);
                        f.setRightCount(f.getRightCount() + 1);
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
        return Single.create(emitter ->
                FirebaseFirestore.getInstance()
                        .collection(COLLECTION_FEED)
                        .whereEqualTo(FieldPath.of("content"), searchKey)
                        .orderBy("date")
                        .startAt(start)
                        .limit(display)
                        .get()
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                final QuerySnapshot result = Objects.requireNonNull(task.getResult());
                                List<Feed> feedList = new ArrayList<>();
                                for (QueryDocumentSnapshot document : result) {
                                    feedList.add(document.toObject(Feed.class));
                                }
                                Log.d("degkjdfsnk", feedList.toString());
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

        return Completable.create(emitter -> {
        });
    }

    @NonNull
    @Override
    public Completable upLoadFeed(@NonNull Feed feed) {
        return Completable.create(CompletableEmitter::onComplete);
    }

    private class MaxPageException extends Exception {
        public MaxPageException() {

        }

        public MaxPageException(String message) {
            super(message);
        }
    }

}
