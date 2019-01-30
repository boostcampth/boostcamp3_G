package com.boostcamp.dreampicker.data.source.feed;

import com.boostcamp.dreampicker.model.Feed;

import java.util.List;

import io.reactivex.Single;

public interface FeedDataSource {

    // 메인 피드 요청
    Single<List<Feed>> getAllFeed();

    // 피드 검색 결과 요청
    Single<List<Feed>> searchFeed(String searchKey);
}
