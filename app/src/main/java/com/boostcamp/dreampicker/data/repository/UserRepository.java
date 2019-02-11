package com.boostcamp.dreampicker.data.repository;

import com.boostcamp.dreampicker.data.model.MyFeed;
import com.boostcamp.dreampicker.data.model.UserDetail;

import java.util.Date;
import java.util.List;

import androidx.annotation.NonNull;
import io.reactivex.Single;

public interface UserRepository {

    /**
     * 유저의 프로필 상세 정보 요청
     *
     * @param userId 유저 ID
     * @return 유저 상세 정보 stream
     */
    @NonNull
    Single<UserDetail> getUserDetail(@NonNull final String userId);

    /**
     * 유저가 업로드 한 투표 목록 페이징
     *
     * @param userId 유저 ID
     * @param startAfter 이 값의 다음 데이터를 요청
     * @param pageSize 페이지의 size
     * @return 피드 리스트 stream
     */
    @NonNull
    Single<List<MyFeed>> getFeedListByUserId(@NonNull final String userId, final Date startAfter, final int pageSize);
}
