package com.boostcamp.dreampicker.data.source.user;

import com.boostcamp.dreampicker.model.User;

import java.util.List;

import io.reactivex.Single;

public interface UserDataSource {

    // 유저 검색 리스트 요청
    Single<List<User>> searchAllUser(String searchKey);

    // 유저 프로필 정보 요청
    Single<UserInfo> getUserInfo(String userId);
}
