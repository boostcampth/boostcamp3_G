package com.boostcamp.dreampicker.data.source;

import com.boostcamp.dreampicker.data.model.User;
import com.boostcamp.dreampicker.data.model.UserDetail;
import com.boostcamp.dreampicker.data.source.remote.firebase.response.PagedListResponse;

import androidx.annotation.NonNull;
import io.reactivex.Completable;
import io.reactivex.Single;

public interface UserDataSource {

    // [프로필] 해당 유저의 프로필 정보
    @NonNull
    Single<UserDetail> getProfileUserDetail(@NonNull String userId);

    // [프로필] 해당 유저가 팔로잉한 유저 리스트 페이징
    @NonNull
    Single<PagedListResponse<User>> addProfileFollowingList(@NonNull String userId,
                                                            int start,
                                                            int display);

    // [프로필] 해당 유저를 팔로잉한 유저 리스트 페이징
    @NonNull
    Single<PagedListResponse<User>> addProfileFollowerList(@NonNull String userId,
                                                           int start,
                                                           int display);

    // [검색] searchKey 기준 유저 검색 결과 리스트 페이징
    @NonNull
    Single<PagedListResponse<User>> addSearchUserList(@NonNull String searchKey,
                                                      int start,
                                                      int display);

    // [유저] 해당 유저 팔로우 등록 또는 취소 토글
    @NonNull
    Completable toggleUserFollow(@NonNull String userId,
                                 @NonNull String myUserId);
    @NonNull
    Single<User> getMyProfile();
}
