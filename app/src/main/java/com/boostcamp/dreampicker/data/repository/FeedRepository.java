package com.boostcamp.dreampicker.data.repository;

import com.boostcamp.dreampicker.data.model.Feed;

import java.util.List;

import androidx.annotation.NonNull;
import io.reactivex.Single;

public interface FeedRepository {
    // 마감되지않은 팔로워 피드 리스트 요청
    @NonNull
    Single<List> unfinishedFollowerFeedList(int startAt, int pageSize);

    // 투표 반영 및 해당 투표 요청
    @NonNull
    Single<Feed> vote(@NonNull Feed feed);
}