package com.boostcamp.dreampicker.model;

public class User {
    private String userId;
    private String name;
    private int feedCount;
    private int followerCount;
    private int followingCount;
    // TODO : 이후 url로 변경
    private String profileImageResource;

    public User() {
    }

    public User(String id,
                String name,
                String profileImageResource,
                int feedCount,
                int followerCount,
                int followingCount) {

        this.userId = id;
        this.name = name;
        this.profileImageResource = profileImageResource;
        this.feedCount = feedCount;
        this.followerCount = followerCount;
        this.followingCount = followingCount;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProfileImageResource() {
        return profileImageResource;
    }

    public void setProfileImageResource(String profileImageResource) { this.profileImageResource = profileImageResource; }

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
}
