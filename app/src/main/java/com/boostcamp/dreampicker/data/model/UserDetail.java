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
    private int followerCount; // 팔로워 수
    private int followingCount; // 팔로잉 수
    private boolean isFollow; // 내가 팔로우 한 유저인지 여부

    public UserDetail(@NonNull String id,
                      @NonNull String name,
                      @Nullable String profileImageUrl,
                      int feedCount,
                      int followerCount,
                      int followingCount,
                      boolean isFollow) {
        this.id = id;
        this.name = name;
        this.profileImageUrl = profileImageUrl;
        this.feedCount = feedCount;
        this.followerCount = followerCount;
        this.followingCount = followingCount;
        this.isFollow = isFollow;
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

    public int getFeedCount() {
        return feedCount;
    }

    public int getFollowerCount() {
        return followerCount;
    }

    public int getFollowingCount() {
        return followingCount;
    }

    public boolean isFollow() {
        return isFollow;
    }

    @Override
    public String toString() {
        return "UserDetail{" + '\n' +
                "id='" + id + '\n' +
                ", name='" + name + '\n' +
                ", profileImageUrl='" + profileImageUrl + '\n' +
                ", feedCount=" + feedCount + '\n' +
                ", followerCount=" + followerCount + '\n' +
                ", followingCount=" + followingCount + '\n' +
                ", isFollow=" + isFollow + '\n' +
                '}' + '\n';
    }
}