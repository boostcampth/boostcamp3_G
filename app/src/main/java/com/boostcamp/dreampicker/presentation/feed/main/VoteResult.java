package com.boostcamp.dreampicker.presentation.feed.main;

import com.boostcamp.dreampicker.data.model.FeedPrevious;
import com.boostcamp.dreampicker.utils.Constant;

import androidx.annotation.NonNull;

public class VoteResult {
    private final FeedPrevious feed;
    private final int flag;

    VoteResult(@NonNull FeedPrevious feed, @Constant.Selection int flag) {
        this.feed = feed;
        this.flag = flag;
    }

    @NonNull
    public FeedPrevious getFeed() {
        return feed;
    }

    public int getFlag() {
        return flag;
    }
}
