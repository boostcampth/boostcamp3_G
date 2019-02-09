package com.boostcamp.dreampicker.data.repository;

import com.boostcamp.dreampicker.data.model.ProfileFeed;

import java.util.List;

import androidx.annotation.NonNull;
import io.reactivex.Single;

public interface FeedRepository {

    /**
     * 유저가 업로드 한 투표 목록 페이징
     *
     * @param userId 유저 ID
     * @param startAt 시작 index
     * @param pageSize 페이지 size
     * @return 피드 리스트 stream
     */
    @NonNull
    Single<List<ProfileFeed>> getProfileFeed(@NonNull String userId, int startAt, int pageSize);
}
