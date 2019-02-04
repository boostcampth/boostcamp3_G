package com.boostcamp.dreampicker.data.source.feed;

import com.boostcamp.dreampicker.model.Feed;

import java.util.List;

import io.reactivex.Single;

public interface FeedDataSource {

    // 메인 피드 요청
    Single<List<Feed>> getAllFeed();

    // 피드 검색 결과 요청
    Single<List<Feed>> searchAllFeed(String searchKey);

    // [메인 피드] 내가 투표 안하고, 마감 안된 투표 최신순으로 페이징
    Single<List<Feed>> addMainFeedList(String userId, int pageIndex, int pageUnit);

    //[프로필] 해당 유저가 업로드한 피드 리스트 페이징
    Single<List<Feed>> addProfileFeedList(String userId, int pageIndex, int pageUnit);

    // [피드공통] 투표하기 / 투표변경 / 투표취소
    void updateFeedVote(String feedId, String userId, int voteFlag);

    // [프로필] 해당 유저가 업로드한 피드 리스트 페이징
    Single<List<Feed>> addSearchFeedList(String searchKey, int pageIndex, int pageUnit);

    // [피드공통] 해당 투표 종료/재시작 토글
    void toggleFeedState(String feedId, boolean isEnded);
}
