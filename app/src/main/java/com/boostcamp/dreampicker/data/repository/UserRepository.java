package com.boostcamp.dreampicker.data.repository;

import com.boostcamp.dreampicker.data.model.MyFeed;
import com.boostcamp.dreampicker.data.model.UserDetail;
import com.boostcamp.dreampicker.data.source.firestore.model.UserDetailRemoteData;

import java.util.Date;
import java.util.List;

import androidx.annotation.NonNull;
import io.reactivex.Completable;
import io.reactivex.Single;

public interface UserRepository {

    /**
     * 유저의 프로필 상세 정보 요청
     *
     * @param userId 유저 ID
     * @return Single<UserDetail>
     */
    @NonNull
    Single<UserDetail> getUserDetail(@NonNull final String userId);

    /**
     * 유저가 업로드 한 투표 목록 페이징
     *
     * @param userId     유저 ID
     * @param startAfter 이 값의 다음 데이터를 요청
     * @param pageSize   페이지의 size
     * @return Single<List<MyFeed>>
     */
    @NonNull
    Single<List<MyFeed>> getFeedListByUserId(@NonNull final String userId, final Date startAfter, final int pageSize);

    /**
     * 유저가 업로드 한 투표 마감/재시작 토글
     *
     * @param feedId 피드 ID
     * @param isEnded true-마감 / false-재시작
     * @return Completable
     * */
    @NonNull
    Completable toggleVoteEnded(@NonNull final String userId, @NonNull final String feedId, final boolean isEnded);

    @NonNull
    Completable addNewUser(@NonNull final String userId, @NonNull final UserDetailRemoteData userDetail);
}