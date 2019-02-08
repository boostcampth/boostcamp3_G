package com.boostcamp.dreampicker.data.model;

import java.util.List;

public class UserDetail {
    private String id; // 유저 아이디
    private String name; // 유저 이름
    private String email; // 유저 이메일
    private String profileImageUrl; // 유저 프로필 url
    private int feedCount; // 올린 피드 수
    private int followerCount; // 팔로워 수
    private int followingCount; // 팔로잉 수
    private boolean isFollow; // 내가 이 사람을 팔로우 했는지 여부

    private List<User> followerList; // 해당 유저의 팔로워 리스트
    private List<User> followingList; // 해당 유저의 팔로잉 리스트
    private List<Feed> feedList; // 해당 유저의 피드 리스트

    public UserDetail() { }

    public UserDetail(String id,
                      String name,
                      String email,
                      String profileImageUrl,
                      int feedCount,
                      int followerCount,
                      int followingCount,
                      boolean isFollow) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.profileImageUrl = profileImageUrl;
        this.feedCount = feedCount;
        this.followerCount = followerCount;
        this.followingCount = followingCount;
        this.isFollow = isFollow;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public void setProfileImageUrl(String profileImageUrl) {
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

    public List<User> getFollowerList() {
        return followerList;
    }

    public void setFollowerList(List<User> followerList) {
        this.followerList = followerList;
    }

    public List<User> getFollowingList() {
        return followingList;
    }

    public void setFollowingList(List<User> followingList) {
        this.followingList = followingList;
    }

    public List<Feed> getFeedList() {
        return feedList;
    }

    public void setFeedList(List<Feed> feedList) {
        this.feedList = feedList;
    }
}