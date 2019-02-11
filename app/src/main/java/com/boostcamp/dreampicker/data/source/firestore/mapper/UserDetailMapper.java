package com.boostcamp.dreampicker.data.source.firestore.mapper;

import com.boostcamp.dreampicker.data.model.UserDetail;
import com.boostcamp.dreampicker.data.source.firestore.model.UserDetailEntity;

import androidx.annotation.NonNull;

public class UserDetailMapper {

    @NonNull
    public static UserDetail toUserDetail(@NonNull String id, @NonNull UserDetailEntity response){

        return new UserDetail(
                id,
                response.getName(),
                response.getProfileImageUrl(),
                response.getFeedCount()
        );
    }
}
