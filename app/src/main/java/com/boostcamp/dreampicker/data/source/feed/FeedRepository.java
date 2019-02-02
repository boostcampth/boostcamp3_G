package com.boostcamp.dreampicker.data.source.feed;

import com.boostcamp.dreampicker.model.Feed;

import java.util.List;

import io.reactivex.Single;

public class FeedRepository implements FeedDataSource {
    private static FeedRepository instance;

    private FeedRemoteDataSource remoteDataSource;

    private FeedRepository() {
        remoteDataSource = FeedRemoteDataSource.getInstance();
    }

    public static FeedRepository getInstance() {
        if(instance == null) {
            synchronized (FeedRepository.class) {
                if(instance == null) {
                    instance = new FeedRepository();
                }
            }
        }
        return instance;
    }

    @Override
    public Single<List<Feed>> getAllFeed() {

        // TODO : 테스팅 끝나면 사용
        return remoteDataSource.getAllFeed();

    }

    @Override
    public Single<List<Feed>> searchAllFeed(String searchKey) {

        return remoteDataSource.searchAllFeed(searchKey);
    }

    @Override
    public Single<List<Feed>> addMainFeedList(String userId, int pageIndex, int pageUnit) {

        return remoteDataSource.addMainFeedList(userId, pageIndex, pageUnit);
    }

    @Override
    public void updateFeedVote(String feedId, String userId, int voteFlag) {

        remoteDataSource.updateFeedVote(feedId, userId, voteFlag);
    }

    @Override
    public Single<List<Feed>> addSearchFeedList(String searchKey, int pageIndex, int pageUnit) {

        return remoteDataSource.addSearchFeedList(searchKey, pageIndex, pageUnit);
    }

    @Override
    public void toggleFeedState(String feedId, boolean isEnded) {

        remoteDataSource.toggleFeedState(feedId, isEnded);
    }
}
