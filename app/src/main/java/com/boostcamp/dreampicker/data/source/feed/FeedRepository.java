package com.boostcamp.dreampicker.data.source.feed;

import com.boostcamp.dreampicker.model.Feed;
import java.util.List;

import io.reactivex.Single;

public class FeedRepository implements FeedDataSource {
    private static FeedDataSource instance;

    private FeedRepository() { }

    public static FeedDataSource getInstance() {
        if(instance == null) {
            synchronized (FeedRepository.class) {
                if(instance == null) {
                    instance = new FeedRepository();
                }
            }
        }
        return instance;
    }

    @Override
    public Single<List<Feed>> getAllFeed() {
        FirestoreRepository firestoreRepository = FirestoreRepository.getInstance();
        return firestoreRepository.getAllFeed();
    }
}
