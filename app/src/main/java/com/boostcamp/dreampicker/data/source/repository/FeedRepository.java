package com.boostcamp.dreampicker.data.source.repository;

import com.boostcamp.dreampicker.data.model.Feed;
import com.boostcamp.dreampicker.data.source.FeedDataSource;
import com.boostcamp.dreampicker.data.source.remote.firebase.FeedFirebaseService;

import java.util.List;

import androidx.annotation.NonNull;
import io.reactivex.Single;

public class FeedRepository implements FeedDataSource {

    private static volatile FeedRepository INSTANCE;

    @NonNull
    private FeedFirebaseService firebaseService;

    private FeedRepository(@NonNull final FeedFirebaseService firebaseService) {
        this.firebaseService = firebaseService;
    }

    public static FeedRepository getInstance(@NonNull final FeedFirebaseService firebaseService) {
        if(INSTANCE == null) {
            synchronized (FeedRepository.class) {
                if(INSTANCE == null) {
                    INSTANCE = new FeedRepository(firebaseService);
                }
            }
        }
        return INSTANCE;
    }

    @Override
    public Single<List<Feed>> getAllFeed() {

        // TODO : 테스팅 끝나면 사용
        return firebaseService.getAllFeed();

    }

    @Override
    public Single<List<Feed>> searchAllFeed(String searchKey) {

        return firebaseService.searchAllFeed(searchKey);
    }

    @Override
    public Single<List<Feed>> addMainFeedList(String userId, int pageIndex, int pageUnit) {

        return firebaseService.addMainFeedList(userId, pageIndex, pageUnit);
    }

    @Override
    public void updateFeedVote(String feedId, String userId, int voteFlag) {

        firebaseService.updateFeedVote(feedId, userId, voteFlag);
    }

    @Override
    public Single<List<Feed>> addSearchFeedList(String searchKey, int pageIndex, int pageUnit) {

        return firebaseService.addSearchFeedList(searchKey, pageIndex, pageUnit);
    }

    @Override
    public Single<List<Feed>> addProfileFeedList(String userId, int pageIndex, int pageUnit) {
        return firebaseService.addProfileFeedList(userId,pageIndex,pageUnit);
    }

    @Override
    public void toggleFeedState(String feedId, boolean isEnded) {

        firebaseService.toggleFeedState(feedId, isEnded);
    }
}
