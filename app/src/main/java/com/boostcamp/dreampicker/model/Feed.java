package com.boostcamp.dreampicker.model;

import java.util.ArrayList;
import java.util.List;

public class Feed {
    private String feedId;
    private Image[] images;
    private User user;
    private String content;
    private String date;

    // TODO : 마감 조건 확인 이후 작업
    private boolean isEnded;

    // TODO : Firestore 연동 이후 작업
    private List<String> leftVotedUserIdList = new ArrayList<>();
    private List<String> rightVotedUserIdList = new ArrayList<>();

    public Feed() { }

    // For Mock Data
    public Feed(String feedId, Image[] images, User user, String content, String date, boolean isEnded) {
        this.feedId = feedId;
        this.images = images;
        this.user = user;
        this.content = content;
        this.date = date;
        this.isEnded = isEnded;

        leftVotedUserIdList.add("key-1");
        leftVotedUserIdList.add("key-1");
        leftVotedUserIdList.add("key-1");

        rightVotedUserIdList.add("key-2");
        rightVotedUserIdList.add("key-2");
        rightVotedUserIdList.add("key-2");
    }

    public String getId() {
        return feedId;
    }

    public void setId(String feedId) {
        this.feedId = feedId;
    }

    public Image[] getImages() {
        return images;
    }

    public void setImages(Image[] images) {
        this.images = images;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public boolean isEnded() {
        return isEnded;
    }

    public void setEnded(boolean ended) {
        isEnded = ended;
    }

    public List<String> getLeftVotedUserIdList() {
        return leftVotedUserIdList;
    }

    public void setLeftVotedUserIdList(List<String> leftVotedUserIdList) {
        this.leftVotedUserIdList = leftVotedUserIdList;
    }

    public List<String> getRightVotedUserIdList() {
        return rightVotedUserIdList;
    }

    public void setRightVotedUserIdList(List<String> rightVotedUserIdList) {
        this.rightVotedUserIdList = rightVotedUserIdList;
    }

    public int leftVotedCount() {
        return leftVotedUserIdList.size();
    }

    public int rightVotedCount() {
        return rightVotedUserIdList.size();
    }
}
