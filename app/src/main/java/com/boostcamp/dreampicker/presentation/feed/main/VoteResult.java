package com.boostcamp.dreampicker.presentation.feed.main;

import com.boostcamp.dreampicker.data.model.LegacyFeed;
import com.boostcamp.dreampicker.utils.Constant;

import androidx.annotation.NonNull;

public class VoteResult {
    private final LegacyFeed feed;
    private final int flag;

    VoteResult(@NonNull LegacyFeed feed, @Constant.Selection int flag) {
        this.feed = feed;
        this.flag = flag;
    }

    @NonNull
    public LegacyFeed getFeed() {
        return feed;
    }

    public int getFlag() {
        return flag;
    }
}
