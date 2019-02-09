package com.boostcamp.dreampicker.data.model;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class UserDetail {
    @NonNull
    private String id; // 유저 아이디
    @NonNull
    private String name; // 유저 이름
    @Nullable
    private String profileImageUrl; // 유저 프로필 url
    private int feedCount; // 올린 피드 수
    private int followerCount; // 팔로워 수
    private int followingCount; // 팔로잉 수
    private boolean isFollow; // 내가 팔로우 한 유저인지 여부
    private List<ProfileFeed> feedList; // 해당 유저의 피드 리스트

    public UserDetail(@NonNull String id,
                      @NonNull String name,
                      @Nullable String profileImageUrl,
                      int feedCount,
                      int followerCount,
                      int followingCount,
                      boolean isFollow,
                      List<ProfileFeed> feedList) {
        this.id = id;
        this.name = name;
        this.profileImageUrl = profileImageUrl;
        this.feedCount = feedCount;
        this.followerCount = followerCount;
        this.followingCount = followingCount;
        this.isFollow = isFollow;
        this.feedList = feedList;
    }

    @NonNull
    public String getId() {
        return id;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    @NonNull
    public String getName() {
        return name;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }

    @Nullable
    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public void setProfileImageUrl(@Nullable String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }

    public int getFeedCount() {
        return feedCount;
    }

    public void setFeedCount(int feedCount) {
        this.feedCount = feedCount;
    }

    public int getFollowerCount() {
        return followerCount;
    }

    public void setFollowerCount(int followerCount) {
        this.followerCount = followerCount;
    }

    public int getFollowingCount() {
        return followingCount;
    }

    public void setFollowingCount(int followingCount) {
        this.followingCount = followingCount;
    }

    public boolean isFollow() {
        return isFollow;
    }

    public void setFollow(boolean follow) {
        isFollow = follow;
    }

    public List<ProfileFeed> getFeedList() {
        return feedList;
    }

    public void setFeedList(List<ProfileFeed> feedList) {
        this.feedList = feedList;
    }
}