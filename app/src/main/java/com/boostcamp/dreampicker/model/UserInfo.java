package com.boostcamp.dreampicker.model;

import java.util.List;

// TODO. count 변수를 따로 둘지. List.size()로 쓸지 결정하기
public class UserInfo {
    private String userId;
    private String userName;
    private String userProfileUrl;
    private List<String> fwers;
    private List<String> fwings;
    private List<String> votedFeed;
    private List<String> feeds;

    private int feedCount;
    private int followerCount;
    private int followingCount;

    public UserInfo() {
    }

    public UserInfo(String userId,
                    String userName,
                    String userProfileUrl,
                    List<String> fwers,
                    List<String> fwings,
                    List<String> votedFeed,
                    List<String> feeds) {

        this.userId = userId;
        this.userName = userName;
        this.userProfileUrl = userProfileUrl;
        this.fwers = fwers;
        this.fwings = fwings;
        this.votedFeed = votedFeed;
        this.feeds = feeds;
    }



    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserProfileUrl() {
        return userProfileUrl;
    }

    public void setUserProfileUrl(String userProfileUrl) { this.userProfileUrl = userProfileUrl; }

    public List<String> getFwers() { return fwers; }

    public void setFwers(List<String> fwers) { this.fwers = fwers; }

    public List<String> getFwings() { return fwings; }

    public void setFwings(List<String> fwings) { this.fwings = fwings; }

    public List<String> getVotedFeed() { return votedFeed; }

    public void setVotedFeed(List<String> votedFeed) { this.votedFeed = votedFeed; }

    public List<String> getFeeds() { return feeds; }

    public void setFeeds(List<String> feeds) { this.feeds = feeds; }

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
