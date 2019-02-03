package com.boostcamp.dreampicker.data.source.user;

import com.boostcamp.dreampicker.model.User;
import com.boostcamp.dreampicker.model.UserInfo;

import java.util.List;

import io.reactivex.Single;

public interface UserDataSource {

    // 유저 검색 리스트 요청
    Single<List<User>> searchAllUser(String searchKey);

    // 유저 프로필 정보 요청
    Single<UserInfo> getUserInfo(String userId);

    //[프로필] 해당 유저의 프로필 정보
    // TODO : userDetail로 수정
    Single<UserInfo> getProfileUserDetail(String userId);

    //[프로필] 해당 유저가 팔로잉한 유저 리스트 페이징
    Single<List<User>> addProfileFollowingList(String userId, int pageIndex, int pageUnit);

    //[프로필] 해당 유저를 팔로잉한 유저 리스트 페이징
    Single<List<User>> addProfileFollowerList(String userId, int pageIndex, int pageUnit);
}
