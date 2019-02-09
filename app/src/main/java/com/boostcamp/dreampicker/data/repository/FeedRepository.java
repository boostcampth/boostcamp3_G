package com.boostcamp.dreampicker.data.repository;

import com.boostcamp.dreampicker.data.model.Feed;

import java.util.List;

import androidx.annotation.NonNull;
import io.reactivex.Completable;
import io.reactivex.Single;

public interface FeedRepository {
    /**
     * [메인 피드] 해당 position 부터 10개의 피드를 리스트로 가져온다.
     * 조건 : 마감 X, 본인의 팔로워
     * --> 본인의 팔로워 리스트와 해당 피드의 업로더 정보를 비교 */
    @NonNull
    Single<List> getFeedList(int start);

    /**
     * [메인 피드] 투표 이후 해당 피드에 대해 변경내역을 가져온다. */
    @NonNull
    Single<Feed> getFeed(@NonNull String feedId);

    /**
     * [메인 피드] 뷰모델에서 확인 후 변경된 위치에 대한 내용을 반영한다.
     * 반환값으로 성공 여부를 받는다.
     * */
    @NonNull
    Completable vote(@NonNull Feed feed);
}