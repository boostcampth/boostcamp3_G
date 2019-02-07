package com.boostcamp.dreampicker.data.source;

import com.boostcamp.dreampicker.data.model.Feed;
import com.boostcamp.dreampicker.data.source.remote.firebase.response.PagedListResponse;

import java.util.List;

import androidx.annotation.NonNull;
import io.reactivex.Completable;
import io.reactivex.Single;

public interface FeedDataSource {

    // 메인 피드 요청
    Single<List<Feed>> getAllFeed();

    // 피드 검색 결과 요청
    Single<List<Feed>> searchAllFeed(String searchKey);

    // [메인 피드] 내가 투표 안하고, 마감 안된 투표 최신순으로 페이징
    @NonNull
    Single<List<Feed>> addMainFeedList(@NonNull String userId,
                                       int pageIndex,
                                       int pageUnit);

    // [피드공통] 투표하기 / 투표변경 / 투표취소
    @NonNull
    Completable updateFeedVote(@NonNull String feedId,
                               @NonNull String userId,
                               int voteFlag);

    // [검색] 검색 결과 피드 리스트 페이징
    @NonNull
    Single<PagedListResponse<Feed>> addSearchFeedList(@NonNull String searchKey,
                                                      int start,
                                                      int display);

    //[프로필] 해당 유저가 업로드한 피드 리스트 페이징
    @NonNull
    Single<PagedListResponse<Feed>> addProfileFeedList(@NonNull String userId,
                                                       int start,
                                                       int display);

    // [피드공통] 해당 투표 종료/재시작 토글
    @NonNull
    Completable toggleFeedState(@NonNull String feedId,
                                boolean isEnded);

    // [업로드]
    void upLoadFeed(Feed feed,String url);
}
