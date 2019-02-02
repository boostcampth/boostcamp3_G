package com.boostcamp.dreampicker.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class UserDetail {
    private String id;
    private String name;
    private String email;
    private String profileImageUrl;

    // TODO. 삭제
    private int profileImageResource;

    private int feedCount;
    private int followerCount;
    private int followingCount;

    private List<User> followerList;
    private List<User> followingList;
    private List<Feed> feedList;

    public UserDetail() {}

    public UserDetail(String id,
                      String name,
                      String email,
                      String profileImageUrl,
                      int profileImageResource,
                      int feedCount,
                      int followerCount,
                      int followingCount) {

        this.id = id;
        this.name = name;
        this.email = email;
        this.profileImageUrl = profileImageUrl;
        this.profileImageResource = profileImageResource;
        this.feedCount = feedCount;
        this.followerCount = followerCount;
        this.followingCount = followingCount;
        this.feedList = new ArrayList<>();
        this.followerList = new ArrayList<>();
        this.followingList = new ArrayList<>();
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

    public int getProfileImageResource() {
        return profileImageResource;
    }

    public void setProfileImageResource(int profileImageResource) {
        this.profileImageResource = profileImageResource;
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