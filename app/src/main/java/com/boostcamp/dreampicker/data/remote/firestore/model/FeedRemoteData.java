package com.boostcamp.dreampicker.data.remote.firestore.model;

import com.boostcamp.dreampicker.data.model.User;
import com.google.firebase.firestore.ServerTimestamp;

import java.util.Date;
import java.util.Map;

public class FeedRemoteData {
    private FeedItemRemoteData itemA;
    private FeedItemRemoteData itemB;
    private User writer;
    private String content;
    @ServerTimestamp
    private Date date;
    private Map<String, String> votedUserMap;
    private boolean isEnded;

    @SuppressWarnings("unused")
    public FeedRemoteData() { }

    public FeedRemoteData(FeedItemRemoteData itemA,
                          FeedItemRemoteData itemB,
                          User writer,
                          String content,
                          Map<String, String> votedUserMap,
                          Date date,
                          boolean isEnded) {
        this.itemA = itemA;
        this.itemB = itemB;
        this.writer = writer;
        this.content = content;
        this.date = date;
        this.votedUserMap = votedUserMap;
        this.isEnded = isEnded;
    }

    public FeedItemRemoteData getItemA() {
        return itemA;
    }

    public void setItemA(FeedItemRemoteData itemA) {
        this.itemA = itemA;
    }

    public FeedItemRemoteData getItemB() {
        return itemB;
    }

    public void setItemB(FeedItemRemoteData itemB) {
        this.itemB = itemB;
    }

    public User getWriter() {
        return writer;
    }

    public void setWriter(User writer) {
        this.writer = writer;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Map<String, String> getVotedUserMap() {
        return votedUserMap;
    }

    public void setVotedUserMap(Map<String, String> votedUserMap) {
        this.votedUserMap = votedUserMap;
    }

    public boolean isEnded() {
        return isEnded;
    }

    public void setEnded(boolean ended) {
        isEnded = ended;
    }
}
