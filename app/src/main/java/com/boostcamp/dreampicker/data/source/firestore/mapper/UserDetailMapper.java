package com.boostcamp.dreampicker.data.source.firestore.mapper;

import com.boostcamp.dreampicker.data.model.UserDetail;
import com.boostcamp.dreampicker.data.source.firestore.model.UserDetailEntity;

import androidx.annotation.NonNull;

public class UserDetailMapper {

    @NonNull
    public static UserDetail toUserDetail(@NonNull UserDetailEntity response){

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
