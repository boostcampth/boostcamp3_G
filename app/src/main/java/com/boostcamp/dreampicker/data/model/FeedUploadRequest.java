package com.boostcamp.dreampicker.data.model;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class FeedUploadRequest {

    @NonNull
    private String userId; // 업로더 정보

    @NonNull
    private String content; // 작성글

    @NonNull
    private String imageUrlA; // 1번 이미지 URL

    @NonNull
    private String imageUrlB; // 2번 이미지 URL

    @Nullable
    private List<String> tagListA; // 1번 이미지 태그 리스트

    @Nullable
    private List<String> tagListB; // 2번 이미지 태그 리스트

    public FeedUploadRequest(@NonNull String userId,
                             @NonNull String content,
                             @NonNull String imageUrlA,
                             @NonNull String imageUrlB,
                             @Nullable List<String> tagListA,
                             @Nullable List<String> tagListB) {
        this.userId = userId;
        this.content = content;
        this.imageUrlA = imageUrlA;
        this.imageUrlB = imageUrlB;
        this.tagListA = tagListA;
        this.tagListB = tagListB;
    }

    @NonNull
    public String getUser() {
        return userId;
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

    @Nullable
    public List<String> getTagListA() {
        return tagListA;
    }

    @Nullable
    public List<String> getTagListB() {
        return tagListB;
    }
}
