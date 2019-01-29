package com.boostcamp.dreampicker.data.source.feed;

import com.boostcamp.dreampicker.model.Feed;

import java.util.List;

import io.reactivex.Single;

public interface FeedDataSource {
    Single<List<Feed>> getAllFeed();
}
