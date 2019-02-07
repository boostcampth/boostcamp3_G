package com.boostcamp.dreampicker.data.source;


import com.boostcamp.dreampicker.data.model.User;

import io.reactivex.Single;

// Todo : 이 데이터 소스는 이후 유저로 변경 예정
public interface MyProfileDataSource {
    Single<User> getMyProfile();
}
