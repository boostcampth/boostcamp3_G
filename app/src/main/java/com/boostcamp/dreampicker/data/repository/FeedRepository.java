package com.boostcamp.dreampicker.data.repository;

import com.boostcamp.dreampicker.data.model.ProfileFeed;
import com.boostcamp.dreampicker.data.model.Feed;
import com.boostcamp.dreampicker.data.model.FeedUploadRequest;

import java.util.Date;
import java.util.List;

import androidx.annotation.NonNull;
import io.reactivex.Single;
import io.reactivex.Completable;

public interface FeedRepository {
    @NonNull
    Single<List<Feed>> getNotEndedMyFollowerFeedList(@NonNull final Date startAfter, final int pageSize);

    @NonNull
    Single<Feed> vote(@NonNull final String feedId, @NonNull final String selectionId);

    // [업로드] 사용자가 작성한 피드를 서버에 업로드한다.
    @NonNull
    Completable uploadFeed(@NonNull final FeedUploadRequest feed);
  
    /**
     * 유저가 업로드 한 투표 목록 페이징
     *
     * @param userId 유저 ID
     * @param startAfter 이 값의 다음 데이터를 요청
     * @param pageSize 페이지의 size
     * @return 피드 리스트 stream
     */
    @NonNull
    Single<List<ProfileFeed>> getFeedListByUserId(@NonNull final String userId, final Date startAfter, final int pageSize);
}
