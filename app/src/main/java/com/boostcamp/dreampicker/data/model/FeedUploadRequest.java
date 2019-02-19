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
    private String imagePathA; // 1번 이미지 Path

    @NonNull
    private String imagePathB; // 2번 이미지 Path

    @Nullable
    private List<String> tagListA; // 1번 이미지 태그 리스트

    @Nullable
    private List<String> tagListB; // 2번 이미지 태그 리스트

    public FeedUploadRequest(@NonNull String userId,
                             @NonNull String content,
                             @NonNull String imagePathA,
                             @NonNull String imagePathB,
                             @Nullable List<String> tagListA,
                             @Nullable List<String> tagListB) {

        this.userId = userId;
        this.content = content;
        this.imagePathA = imagePathA;
        this.imagePathB = imagePathB;
        this.tagListA = tagListA;
        this.tagListB = tagListB;
    }

    @NonNull
    public String getUserId() {
        return userId;
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
    public String getImagePathA() {
        return imagePathA;
    }

    @NonNull
    public String getImagePathB() {
        return imagePathB;
    }

    @Nullable
    public List<String> getTagListA() {
        return tagListA;
    }

    @Nullable
    public List<String> getTagListB() {
        return tagListB;
    }

    public void setImagePathA(@NonNull String imagePathA) {
        this.imagePathA = imagePathA;
    }

    public void setImagePathB(@NonNull String imagePathB) {
        this.imagePathB = imagePathB;
    }
}
