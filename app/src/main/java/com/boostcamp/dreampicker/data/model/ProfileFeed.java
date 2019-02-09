package com.boostcamp.dreampicker.data.model;

import androidx.annotation.NonNull;

public class ProfileFeed {
    @NonNull
    private final String id; // 피드 ID
    @NonNull
    private final String content; // 내용
    @NonNull
    private final String imageUrlA; // 첫번째 이미지 URL
    @NonNull
    private final boolean isEnded; // 투표 마감 여부
    private final String imageUrlB; // 두번째 이미지 URL

    public ProfileFeed(@NonNull String id,
                       @NonNull String content,
                       @NonNull String imageUrlA,
                       @NonNull String imageUrlB,
                       boolean isEnded) {
        this.id = id;
        this.content = content;
        this.imageUrlA = imageUrlA;
        this.imageUrlB = imageUrlB;
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
    }

    @NonNull
    }

    public boolean isEnded() {
        return isEnded;
    }
}
