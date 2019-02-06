package com.boostcamp.dreampicker.data.source.repository;

import com.boostcamp.dreampicker.data.model.Feed;
import com.boostcamp.dreampicker.data.source.FeedDataSource;
import com.boostcamp.dreampicker.data.source.local.test.FeedMockDataSource;
import com.boostcamp.dreampicker.data.source.remote.firebase.FeedFirebaseService;

import java.util.List;

import javax.annotation.Nonnull;

import androidx.annotation.NonNull;
import io.reactivex.Single;

public class FeedRepository implements FeedDataSource {
    private static volatile FeedRepository INSTANCE;

    private FeedRepository(@NonNull final FeedDataSource firebaseService) {
        this.mockDataSource = FeedMockDataSource.getInstance();
        this.firebaseService = firebaseService;
    }

    public static FeedRepository getInstance(@NonNull final FeedDataSource firebaseService) {
        if(INSTANCE == null) {
            synchronized (FeedRepository.class) {
                if(INSTANCE == null) {
                    INSTANCE = new FeedRepository(firebaseService);
                }
            }
        }
        return INSTANCE;
    }

    /** MOCK DATA 테스트용
     *  Firestore 연동 후 isTesting = false */
    private final boolean isTesting = true;
    private final FeedDataSource mockDataSource;

    @NonNull
    private final FeedDataSource firebaseService;

    @Override
    public Single<List<Feed>> getAllFeed() {
        if(isTesting){
            return mockDataSource.getAllFeed();
        }

        return firebaseService.getAllFeed();

    }

    @Override
    public Single<List<Feed>> searchAllFeed(String searchKey) {
        if(isTesting){
            return mockDataSource.searchAllFeed(searchKey);
        }

        return firebaseService.searchAllFeed(searchKey);
    }

    @Override
    public Single<List<Feed>> addMainFeedList(String userId, int pageIndex, int pageUnit) {
        if(isTesting){
            return mockDataSource.addMainFeedList(userId, pageIndex, pageUnit);
        }

        return firebaseService.addMainFeedList(userId, pageIndex, pageUnit);
    }

    @Override
    public void updateFeedVote(String feedId, String userId, int voteFlag) {
        if(isTesting){
            mockDataSource.updateFeedVote(feedId, userId, voteFlag);
            return;
        }

        firebaseService.updateFeedVote(feedId, userId, voteFlag);
    }

    @Override
    public Single<List<Feed>> addSearchFeedList(String searchKey, int pageIndex, int pageUnit) {
        if(isTesting){
            return mockDataSource.addSearchFeedList(searchKey, pageIndex, pageUnit);
        }

        return firebaseService.addSearchFeedList(searchKey, pageIndex, pageUnit);
    }

    @Override
    public Single<List<Feed>> addProfileFeedList(String userId, int pageIndex, int pageUnit) {
        return firebaseService.addProfileFeedList(userId,pageIndex,pageUnit);
    }

    @Override
    public void toggleFeedState(String feedId, boolean isEnded) {
        if(isTesting){
            mockDataSource.toggleFeedState(feedId, isEnded);
            return;
        }

        firebaseService.toggleFeedState(feedId, isEnded);
    }
}
