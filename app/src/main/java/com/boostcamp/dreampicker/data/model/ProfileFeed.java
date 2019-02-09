package com.boostcamp.dreampicker.data.model;

import androidx.annotation.NonNull;

public class ProfileFeed {
    @NonNull
    private final String id; // 피드 ID
    @NonNull
    private final String content; // 내용
    @NonNull
    private final String imageAUrl; // 첫번째 이미지 URL
    @NonNull
    private final String imageBUrl; // 두번째 이미지 URL
    private final boolean isEnded; // 투표 마감 여부

    public ProfileFeed(@NonNull String id,
                       @NonNull String content,
                       @NonNull String imageAUrl,
                       @NonNull String imageBUrl,
                       boolean isEnded) {
        this.id = id;
        this.content = content;
        this.imageAUrl = imageAUrl;
        this.imageBUrl = imageBUrl;
        this.isEnded = isEnded;
    }

    @NonNull
    public String getId() {
        return id;
    }

    @NonNull
    public String getContent() {
        return content;
    }

    @NonNull
    public String getImageAUrl() {
        return imageAUrl;
    }

    @NonNull
    public String getImageBUrl() {
        return imageBUrl;
    }

    public boolean isEnded() {
        return isEnded;
    }
}
