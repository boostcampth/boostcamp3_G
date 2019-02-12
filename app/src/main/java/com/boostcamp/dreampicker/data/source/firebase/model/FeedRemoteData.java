package com.boostcamp.dreampicker.data.source.firebase.model;

import com.boostcamp.dreampicker.data.model.User;

import java.util.Date;
import java.util.Map;

import androidx.annotation.NonNull;

public class FeedRemoteData {

    private FeedItemRemoteData itemA;
    private FeedItemRemoteData itemB;
    private User writer;
    private String content;
    private Date date;
    private Map<String, String> votedUserMap;
    private boolean isEnded;

    public FeedRemoteData() { }

    public FeedRemoteData(@NonNull FeedItemRemoteData itemA,
                          @NonNull FeedItemRemoteData itemB,
                          @NonNull User writer,
                          @NonNull String content,
                          @NonNull Map<String, String> votedUserMap,
                          @NonNull Date date,
                          @NonNull boolean isEnded) {
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
