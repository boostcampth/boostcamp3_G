package com.boostcamp.dreampicker.data.source.firebase.model;

import java.util.List;


public class FeedRemoteVoteItem {

    private String id;
    private String imageUrl;
    private List<String> tagList;

    public FeedRemoteVoteItem() {
    }

    public FeedRemoteVoteItem(String id,
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
