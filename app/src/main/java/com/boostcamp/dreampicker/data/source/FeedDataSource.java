package com.boostcamp.dreampicker.data.source;

import com.boostcamp.dreampicker.data.model.LegacyFeed;
import com.boostcamp.dreampicker.data.source.remote.firebase.response.PagedListResponse;
import com.boostcamp.dreampicker.utils.Constant;

import androidx.annotation.NonNull;
import io.reactivex.Completable;
import io.reactivex.Single;

public interface FeedDataSource {

     // [메인 피드] 내가 투표 안하고, 마감 안된 투표 최신순으로 페이징
    @NonNull
    Single<PagedListResponse<LegacyFeed>> addMainFeedList(int start, int display);

    // [피드공통] 투표하기 / 투표변경 / 투표취소
    @NonNull
    Completable updateFeedVote(@NonNull String feedId, @Constant.Selection int voteFlag);

    // [검색] 검색 결과 피드 리스트 페이징
    @NonNull
    Single<PagedListResponse<LegacyFeed>> addSearchFeedList(@NonNull String searchKey,
                                                            int start,
                                                            int display);

    //[프로필] 해당 유저가 업로드한 피드 리스트 페이징
    @NonNull
    Single<PagedListResponse<LegacyFeed>> addProfileFeedList(@NonNull String userId,
                                                             int start,
                                                             int display);

    // [피드공통] 해당 투표 종료/재시작 토글
    @NonNull
    Completable toggleFeedState(@NonNull String feedId,
                                boolean isEnded);

    // [업로드]
    @NonNull
    Completable upLoadFeed(@NonNull LegacyFeed feed);
}
