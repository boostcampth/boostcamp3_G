package com.boostcamp.dreampicker.data.model;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class UserItem {
    @NonNull
    private String id; // 유저 아이디
    @NonNull
    private  String name; // 유저 이름
    @Nullable
    private String profileImageUrl ; // 프로필 이미지 URL

    public UserItem() { }

    public UserItem(@NonNull String id,
                    @NonNull String name,
                    @Nullable String profileImageUrl) {
        this.id = id;
        this.name = name;
        this.profileImageUrl = profileImageUrl;
    }

    @NonNull
    public String getId() {
        return id;
    }

    @NonNull
    public String getName() {
        return name;
    }

    @Nullable
    public String getProfileImageUrl() {
        return profileImageUrl;
    }
}
