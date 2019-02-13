package com.boostcamp.dreampicker.data.local.room.entity;

import androidx.annotation.NonNull;

public class VotedFeed {
    @NonNull
    private final String id; // Feed ID
    @NonNull
    private final String profileImageUrl;
    @NonNull
    private final String content;
    @NonNull
    private final String imageUrlA;
    @NonNull
    private final String imageUrlB;

    public VotedFeed(@NonNull String id,
                     @NonNull String profileImageUrl,
                     @NonNull String content,
                     @NonNull String imageUrlA,
                     @NonNull String imageUrlB) {
        this.id = id;
        this.profileImageUrl = profileImageUrl;
        this.content = content;
        this.imageUrlA = imageUrlA;
        this.imageUrlB = imageUrlB;
    }

    @NonNull
    public String getId() {
        return id;
    }

    @NonNull
    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    @NonNull
    public String getContent() {
        return content;
    }

    @NonNull
    public String getImageUrlA() {
        return imageUrlA;
    }

    @NonNull
    public String getImageUrlB() {
        return imageUrlB;
    }
}
