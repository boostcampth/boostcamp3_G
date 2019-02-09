package com.boostcamp.dreampicker.data.repository;

import com.boostcamp.dreampicker.data.model.UserDetail;

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
    Single<UserDetail> getUserDetail(@NonNull String userId);
}
