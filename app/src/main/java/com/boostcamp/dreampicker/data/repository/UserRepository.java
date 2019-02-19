package com.boostcamp.dreampicker.data.repository;

import com.boostcamp.dreampicker.data.model.MyFeed;
import com.boostcamp.dreampicker.data.model.UserDetail;

import androidx.annotation.NonNull;
import androidx.paging.PagedList;
import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;

public interface UserRepository {

    // 유저의 프로필 상세 정보 요청
    @NonNull
    Single<UserDetail> getUserDetail(@NonNull final String userId);

    // 유저가 업로드 한 투표 목록 페이징
    @NonNull
    Observable<PagedList<MyFeed>> getFeedListByUserId(@NonNull final String userId,
                                                      final int pageSize);

    // 유저가 업로드 한 투표 마감/재시작 토글
    @NonNull
    Completable toggleVoteEnded(@NonNull final String userId,
                                @NonNull final String feedId,
                                final boolean isEnded);

    // 새로운 유저 정보 등록
    @NonNull
    Completable signIn(@NonNull final String userIdToken);

    @NonNull
    Completable signOut();
}