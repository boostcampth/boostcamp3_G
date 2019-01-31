package com.boostcamp.dreampicker.model;

import java.util.List;

// TODO. count 변수를 따로 둘지. List.size()로 쓸지 결정하기
public class UserInfo {
    private String id;
    private String name;
    private String email;
    private String profileImageUrl;

    // TODO. 삭제
    private int profileImageResource;
    private List<String> fwerList;
    private List<String> fwingList;
    private List<String> votedFeedList;
    private List<String> myFeedList;

    public UserInfo() {
    }

    public UserInfo(String id, String name, String email, String profileImageUrl, int profileImageResource, List<String> fwerList, List<String> fwingList, List<String> votedFeedList, List<String> myFeedList) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.profileImageUrl = profileImageUrl;
        this.profileImageResource = profileImageResource;
        this.fwerList = fwerList;
        this.fwingList = fwingList;
        this.votedFeedList = votedFeedList;
        this.myFeedList = myFeedList;
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

    public List<String> getFwerList() {
        return fwerList;
    }

    public void setFwerList(List<String> fwerList) {
        this.fwerList = fwerList;
    }

    public List<String> getFwingList() {
        return fwingList;
    }

    public void setFwingList(List<String> fwingList) {
        this.fwingList = fwingList;
    }

    public List<String> getVotedFeedList() {
        return votedFeedList;
    }

    public void setVotedFeedList(List<String> votedFeedList) {
        this.votedFeedList = votedFeedList;
    }

    public List<String> getMyFeedList() {
        return myFeedList;
    }

    public void setMyFeedList(List<String> myFeedList) {
        this.myFeedList = myFeedList;
    }
}