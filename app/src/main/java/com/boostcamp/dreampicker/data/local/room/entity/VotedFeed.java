package com.boostcamp.dreampicker.data.local.room.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "voted_feeds")
public class VotedFeed {
    @NonNull
    @PrimaryKey
    private final String id; // Feed ID
    @NonNull
    private final String name;
    @NonNull
    @ColumnInfo(name = "profile_image_url")
    private final String profileImageUrl;
    @NonNull
    private final String content;
    @NonNull
    @ColumnInfo(name ="image_url_a")
    private final String imageUrlA;
    @NonNull
    @ColumnInfo(name ="image_url_b")
    private final String imageUrlB;

    public VotedFeed(@NonNull String id,
                     @NonNull String name,
                     @NonNull String profileImageUrl,
                     @NonNull String content,
                     @NonNull String imageUrlA,
                     @NonNull String imageUrlB) {
        this.id = id;
        this.name = name;
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
    public String getName() {
        return name;
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
