package com.boostcamp.dreampicker.data.source.firestore.model;

public class UserDetailRemoteData {
    private String name; // 유저 이름
    private String profileImageUrl; // 유저 프로필 url
    private int feedCount; // 올린 피드 수

    public UserDetailRemoteData() {
    }

    public UserDetailRemoteData(String name, String profileImageUrl, int feedCount) {
        this.name = name;
        this.profileImageUrl = profileImageUrl;
        this.feedCount = feedCount;
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

}
