package com.boostcamp.dreampicker.data.source.user;

import com.boostcamp.dreampicker.model.Feed;
import com.boostcamp.dreampicker.model.User;
import com.boostcamp.dreampicker.model.UserInfo;

import java.util.List;

import androidx.annotation.Nullable;
import io.reactivex.Single;

public interface UserDataSource {

    // 유저 검색 리스트 요청
    Single<List<User>> searchAllUser(String searchKey);

    // 유저 프로필 정보 요청
    Single<UserInfo> getUserInfo(String userId);

    Single<List<Feed>> searchAllFeed(String searchKey, @Nullable Boolean isEnded);
}
