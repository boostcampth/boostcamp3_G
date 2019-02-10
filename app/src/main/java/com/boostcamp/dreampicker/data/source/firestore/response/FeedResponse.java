package com.boostcamp.dreampicker.data.source.firestore.response;

import com.boostcamp.dreampicker.data.model.Feed;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringDef;

public class FeedResponse {
    public static final String FEED_EMPTY = "FEED_EMPTY";
    public static final String FEED_SUCCESS = "FEED_SUCCESS";
    public static final String FEED_ENDED = "FEED_ENDED";
    public static final String FEED_ERROR = "FEED_ERROR";

    @StringDef({FEED_EMPTY, FEED_SUCCESS, FEED_ENDED, FEED_ERROR})
    @Retention(RetentionPolicy.SOURCE)
    @interface Result { }

    private @Result String result;

    @Nullable
    private List<Feed> feedList;

    @Nullable
    private Feed feed;

    public FeedResponse(@NonNull @Result String result) {
        this.result = result;
    }

    public FeedResponse(@NonNull @Result String result,
                        @Nullable Feed feed) {
        this.result = result;
        this.feed = feed;
    }

    public FeedResponse(@NonNull @Result String result,
                        @NonNull List<Feed> feedList) {
        this.result = result;
        this.feedList = feedList;
    }

    @Nullable
    public List<Feed> getFeedList() {
        return feedList;
    }

    @Nullable
    public Feed getFeed() {
        return feed;
    }

    @Result
    public String getResult() {
        return result;
    }
}
