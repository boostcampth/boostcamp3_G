package com.boostcamp.dreampicker.data.source.repository;

import com.boostcamp.dreampicker.data.model.Feed;
import com.boostcamp.dreampicker.data.source.FeedDataSource;
import com.boostcamp.dreampicker.data.source.local.test.FeedMockDataSource;
import com.boostcamp.dreampicker.data.source.remote.firebase.response.PagedListResponse;

import java.util.List;

import androidx.annotation.NonNull;
import io.reactivex.Completable;
import io.reactivex.Single;

public class FeedRepository implements FeedDataSource {
    private static volatile FeedRepository INSTANCE;

    private FeedRepository(@NonNull final FeedDataSource firebaseService) {
        this.mockDataSource = FeedMockDataSource.getInstance();
        this.firebaseService = firebaseService;
    }

    public static FeedRepository getInstance(@NonNull final FeedDataSource firebaseService) {
        if (INSTANCE == null) {
            synchronized (FeedRepository.class) {
                if (INSTANCE == null) {
                    INSTANCE = new FeedRepository(firebaseService);
                }
            }
        }
        return INSTANCE;
    }

    /**
     * MOCK DATA 테스트용
     * Firestore 연동 후 isTesting = false
     */
    private final boolean isTesting = true;
    private final FeedDataSource mockDataSource;

    @NonNull
    private final FeedDataSource firebaseService;

    @Override
    @NonNull
    public Single<PagedListResponse<Feed>> addMainFeedList(int start, int display) {
        if (isTesting) {
            return mockDataSource.addMainFeedList(start, display);
        }

        return firebaseService.addMainFeedList(start, display);
    }

    @Override
    @NonNull
    public Completable updateFeedVote(@NonNull String feedId, int voteFlag) {
        if (isTesting) {
            return mockDataSource.updateFeedVote(feedId, voteFlag);
        }

        return firebaseService.updateFeedVote(feedId, voteFlag);
    }

    @Override
    @NonNull
    public Single<PagedListResponse<Feed>> addSearchFeedList(@NonNull String searchKey,
                                                             int start,
                                                             int display) {
        if (isTesting) {
            return mockDataSource.addSearchFeedList(searchKey, start, display);
        }

        return firebaseService.addSearchFeedList(searchKey, start, display);
    }

    @Override
    @NonNull
    public Single<PagedListResponse<Feed>> addProfileFeedList(@NonNull String userId,
                                                              int start,
                                                              int display) {
        if (isTesting) {
            return mockDataSource.addProfileFeedList(userId, start, display);
        }

        return firebaseService.addProfileFeedList(userId, start, display);
    }

    @Override
    @NonNull
    public Completable toggleFeedState(@NonNull String feedId,
                                       boolean isEnded) {
        if (isTesting) {
            return mockDataSource.toggleFeedState(feedId, isEnded);
        }

        return firebaseService.toggleFeedState(feedId, isEnded);
    }

    @Override
    @NonNull
    public Completable upLoadFeed(@NonNull Feed feed) {
        if(isTesting){
            return mockDataSource.upLoadFeed(feed);
        }

        return firebaseService.upLoadFeed(feed);

    }

}
