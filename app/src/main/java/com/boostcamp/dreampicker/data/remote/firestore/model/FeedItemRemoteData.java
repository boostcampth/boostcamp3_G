package com.boostcamp.dreampicker.data.remote.firestore.model;

import java.util.List;

public class FeedItemRemoteData {
    private String id;
    private String imageUrl;
    private List<String> tagList;

    @SuppressWarnings("unused")
    public FeedItemRemoteData() { }

    public FeedItemRemoteData(String id,
                              String imageUrl,
                              List<String> tagList) {
        this.id = id;
        this.imageUrl = imageUrl;
        this.tagList = tagList;
    }

    public String getId() {
        return id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public List<String> getTagList() {
        return tagList;
    }
}
