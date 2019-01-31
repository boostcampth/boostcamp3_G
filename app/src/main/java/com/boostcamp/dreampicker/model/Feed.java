package com.boostcamp.dreampicker.model;

import java.util.ArrayList;
import java.util.List;

public class Feed {
    private String id;
    private List<Image> imageList;
    private User user;
    private String content;
    private String date;

    // TODO : 마감 조건 확인 이후 작업
    private boolean isEnded;

    // TODO : Firestore 연동 이후 작업
    private List<String> leftUserList = new ArrayList<>();
    private List<String> rightUserList = new ArrayList<>();

    public Feed() {
    }

    // For Mock Data
    public Feed(String id, List<Image> imageList, User user, String content, String date, boolean isEnded) {
        this.id = id;
        this.imageList = imageList;
        this.user = user;
        this.content = content;
        this.date = date;
        this.isEnded = isEnded;

        leftUserList.add("key-1");
        leftUserList.add("key-1");
        leftUserList.add("key-1");

        rightUserList.add("key-2");
        rightUserList.add("key-2");
        rightUserList.add("key-2");
    }

    public String getId() {
        return id;
    }

    public void setId(String feedId) {
        this.id = feedId;
    }

    public List<Image> getImageList() {
        return imageList;
    }

    public void setImageList(List<Image> imageList) {
        this.imageList = imageList;
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

    public List<String> getLeftUserList() {
        return leftUserList;
    }

    public void setLeftUserList(List<String> leftUserList) {
        this.leftUserList = leftUserList;
    }

    public List<String> getRightUserList() {
        return rightUserList;
    }

    public void setRightUserList(List<String> rightUserList) {
        this.rightUserList = rightUserList;
    }
}