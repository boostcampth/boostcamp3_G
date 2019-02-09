package com.boostcamp.dreampicker.data.repository;

import com.boostcamp.dreampicker.data.model.Feed;

import java.util.Date;
import java.util.List;

import androidx.annotation.NonNull;
import io.reactivex.Single;

public interface FeedRepository {
    @NonNull
    Single<List<Feed>> getNotEndedMyFollowerFeedList(@NonNull final Date endBefore, final int pageSize);

    @NonNull
    Single<Feed> vote(@NonNull final String feedId, @NonNull final String selectionId);
}