package com.boostcamp.dreampicker.model;

import java.util.List;

public class Feed {
    private String feedId;
    private List<Image> images;
    private User user;
    private String content;
    private String date;
    private boolean isEnded;
    private List<String> upVotedUserIdList;
    private List<String> downVotedUserIdList;
    public Feed() {
    }

    public Feed(String feedId,
                List<Image> images,
                User user,
                String content,
                String date,
                boolean isEnded,
                List<String> upVotedUserIdList,
                List<String> downVotedUserIdList) {

        this.feedId = feedId;
        this.images = images;
        this.user = user;
        this.content = content;
        this.date = date;
        this.isEnded = isEnded;
        this.upVotedUserIdList = upVotedUserIdList;
        this.downVotedUserIdList = downVotedUserIdList;
    }

    public Feed(User user) {
        this.user = user;
    }

    public Feed(List<Image> images) {
        this.images = images;
    }

    public String getId() {
        return feedId;
    }

    public void setId(String feedId) {
        this.feedId = feedId;
    }

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
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

    public String getFeedId() { return feedId; }

    public void setFeedId(String feedId) {
        this.feedId = feedId;
    }

    public boolean isEnded() {
        return isEnded;
    }

    public void setEnded(boolean ended) {
        isEnded = ended;
    }

    public List<String> getUpVotedUserIdList() {
        return upVotedUserIdList;
    }

    public void setUpVotedUserIdList(List<String> upVotedUserIdList) { this.upVotedUserIdList = upVotedUserIdList; }

    public List<String> getDownVotedUserIdList() {
        return downVotedUserIdList;
    }

    public void setDownVotedUserIdList(List<String> downVotedUserIdList) { this.downVotedUserIdList = downVotedUserIdList; }
}
