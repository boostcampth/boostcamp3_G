package com.boostcamp.dreampicker.data.model;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class UserDetail {
    @NonNull
    private final String id; // 유저 아이디
    @NonNull
    private final String name; // 유저 이름
    @Nullable
    private final String profileImageUrl; // 유저 프로필 url
    private int feedCount; // 올린 피드 수

    public UserDetail(@NonNull String id,
                      @NonNull String name,
                      @Nullable String profileImageUrl,
                      int feedCount) {
        this.id = id;
        this.name = name;
        this.profileImageUrl = profileImageUrl;
        this.feedCount = feedCount;
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

    @Override
    public String toString() {
        return "UserDetail{" + '\n' +
                "id='" + id + '\n' +
                ", name='" + name + '\n' +
                ", profileImageUrl='" + profileImageUrl + '\n' +
                ", feedCount=" + feedCount + '\n' +
                '}' + '\n';
    }
}