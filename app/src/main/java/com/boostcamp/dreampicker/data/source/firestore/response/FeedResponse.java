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

    @StringDef({FEED_EMPTY, FEED_SUCCESS})
    @Retention(RetentionPolicy.SOURCE)
    @interface Result { }

    private @Result String result;

    @Nullable
    private List<Feed> feedList;

    public FeedResponse(@NonNull @Result String result) {
        this.result = result;
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

    @Result
    public String getResult() {
        return result;
    }
}
