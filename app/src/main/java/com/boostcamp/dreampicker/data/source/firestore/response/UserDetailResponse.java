package com.boostcamp.dreampicker.data.source.firestore.response;

public class UserDetailResponse {
    private String id; // 유저 아이디
    private String name; // 유저 이름
    private String profileImageUrl; // 유저 프로필 url
    private int feedCount; // 올린 피드 수
    private int followerCount; // 팔로워 수
    private int followingCount; // 팔로잉 수

    public UserDetailResponse() {
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

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
}
