package com.boostcamp.dreampicker.data.source.firebase.model;

import com.boostcamp.dreampicker.data.model.User;

import java.util.Date;
import java.util.Map;

import androidx.annotation.NonNull;

public class FeedRemoteData {

    private FeedRemoteVoteItem itemA;
    private FeedRemoteVoteItem itemB;
    private User user;
    private String content;
    private Date date;
    private Map<String, String> votedUserMap;
    private boolean isEnded;

    public FeedRemoteData() { }

    public FeedRemoteData(@NonNull FeedRemoteVoteItem itemA,
                          @NonNull FeedRemoteVoteItem itemB,
                          @NonNull User user,
                          @NonNull String content,
                          @NonNull Map<String, String> votedUserMap,
                          @NonNull Date date,
                          @NonNull boolean isEnded) {
        this.itemA = itemA;
        this.itemB = itemB;
        this.user = user;
        this.content = content;
        this.date = date;
        this.votedUserMap = votedUserMap;
        this.isEnded = isEnded;
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
