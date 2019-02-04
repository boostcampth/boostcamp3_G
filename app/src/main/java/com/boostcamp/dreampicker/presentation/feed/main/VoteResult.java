package com.boostcamp.dreampicker.presentation.feed.main;

import com.boostcamp.dreampicker.data.model.Feed;
import com.boostcamp.dreampicker.utils.Constant;

import androidx.annotation.NonNull;

public class VoteResult {
    private final Feed feed;
    private final int flag;

    public VoteResult(@NonNull Feed feed, @Constant.VoteFlag int flag) {
        this.feed = feed;
        this.flag = flag;
    }

    @NonNull
    public Feed getFeed() {
        return feed;
    }

    public int getFlag() {
        return flag;
    }
}
