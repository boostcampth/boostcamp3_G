package com.boostcamp.dreampicker.data.source.firestore.model;

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

    public FeedItemRemoteData getItemB() {
        return itemB;
    }

    public User getWriter() {
        return writer;
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
