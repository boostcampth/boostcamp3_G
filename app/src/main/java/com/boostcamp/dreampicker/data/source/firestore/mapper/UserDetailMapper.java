package com.boostcamp.dreampicker.data.source.firestore.mapper;

import com.boostcamp.dreampicker.data.model.UserDetail;
import com.boostcamp.dreampicker.data.source.firestore.model.UserDetailEntity;

import androidx.annotation.NonNull;

public class UserDetailMapper {

    @NonNull
    public static UserDetail toUserDetail(@NonNull String userId, @NonNull UserDetailEntity response){

        // TODO. 팔로우 기능 구현 후 isFollow 정보 리턴
        return new UserDetail(
                userId,
                response.getName(),
                response.getProfileImageUrl(),
                response.getFeedCount(),
                response.getFollowerCount(),
                response.getFollowingCount(),
                false
        );
    }
}
