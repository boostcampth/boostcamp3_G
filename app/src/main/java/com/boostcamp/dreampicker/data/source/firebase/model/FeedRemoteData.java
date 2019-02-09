package com.boostcamp.dreampicker.data.source.firebase.model;

import com.boostcamp.dreampicker.data.model.User;

import java.util.Date;
import java.util.Map;

public class FeedRemoteData {
    private String id;
    private FeedRemoteVoteItem itemA;
    private FeedRemoteVoteItem itemB;
    private User user;
    private String content;
    private Date date;
    private Map<String, String> votedUserMap;
    private boolean isEnded;

    public FeedRemoteData() { }

    public FeedRemoteData(String id,
                          FeedRemoteVoteItem itemA,
                          FeedRemoteVoteItem itemB,
                          User user,
                          String content,
                          Map<String, String> votedUserMap,
                          Date date,
                          boolean isEnded) {
        this.id = id;
        this.itemA = itemA;
        this.itemB = itemB;
        this.user = user;
        this.content = content;
        this.date = date;
        this.votedUserMap = votedUserMap;
        this.isEnded = isEnded;
    }

    public String getId() {
        return id;
    }

    public FeedRemoteVoteItem getItemA() {
        return itemA;
    }

    public FeedRemoteVoteItem getItemB() {
        return itemB;
    }

    public User getUser() {
        return user;
    }

    public String getContent() {
        return content;
    }

    public Date getDate() {
        return date;
    }

    public Map<String, String> getVotedUserMap() {
        return votedUserMap;
    }

    public boolean isEnded() {
        return isEnded;
    }
}
