package com.boostcamp.dreampicker.data.repository;

import com.boostcamp.dreampicker.data.local.room.entity.VotedFeed;
import com.boostcamp.dreampicker.data.model.Feed;
import com.boostcamp.dreampicker.data.model.FeedDetail;
import com.boostcamp.dreampicker.data.model.FeedUploadRequest;

import java.util.Date;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.paging.DataSource;
import io.reactivex.Completable;
import io.reactivex.Single;

public interface FeedRepository {
    @NonNull
    Single<List<Feed>> getNotEndedFeedList(@NonNull final String userId,
                                           @NonNull final Date startAfter, final int pageSize);

    @NonNull
    Completable vote(@NonNull final String userId,
                     @NonNull final String feedId,
                     @NonNull final String selectionId);

    Single<Feed> getFeed(@NonNull final String userId, @NonNull final String feedId);

    // [업로드] 사용자가 작성한 피드를 서버에 업로드한다.
    @NonNull
    Completable uploadFeed(@NonNull final FeedUploadRequest feed);

    @NonNull
    Single<FeedDetail> getFeedDetail(@NonNull final String userId,
                                     @NonNull final String feedId);

    DataSource.Factory<Integer, VotedFeed> getMyVotedFeedList();
}
