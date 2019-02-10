package com.boostcamp.dreampicker.data.source.firestore.mapper;

import com.boostcamp.dreampicker.data.model.UserDetail;
import com.boostcamp.dreampicker.data.source.firestore.response.UserDetailResponse;

import androidx.annotation.NonNull;

public class UserResponseMapper {

    public static UserDetail toUserDetail(@NonNull UserDetailResponse response){

        return new UserDetail(
                response.getId(),
                response.getName(),
                response.getProfileImageUrl(),
                response.getFeedCount(),
                response.getFollowerCount(),
                response.getFollowingCount(),
                false
        );
    }
}
