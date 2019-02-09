package com.boostcamp.dreampicker.data.model;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class UploadFeedItem {

    @NonNull
    private String id; // Feed 구분할 ID

    @NonNull
    private User user; // 업로더 정보

    @NonNull
    private String content; // 작성글

    @NonNull
    private String date; // 업로드 날짜

    @NonNull
    private String imageUrlA; // 1번 이미지 URL

    @NonNull
    private String imageUrlB; // 2번 이미지 URL

    @Nullable
    private List<String> tagListA; // 1번 이미지 태그 리스트

    @Nullable
    private List<String> tagListB; // 2번 이미지 태그 리스트

    public UploadFeedItem(@NonNull String id,
                          @NonNull User user,
                          @NonNull String content,
                          @NonNull String date,
                          @NonNull String imageUrlA,
                          @NonNull String imageUrlB,
                          @Nullable List<String> tagListA,
                          @Nullable List<String> tagListB) {
        this.id = id;
        this.user = user;
        this.content = content;
        this.date = date;
        this.imageUrlA = imageUrlA;
        this.imageUrlB = imageUrlB;
        this.tagListA = tagListA;
        this.tagListB = tagListB;
    }

    @NonNull
    public String getId() {
        return id;
    }

    @NonNull
    public User getUser() {
        return user;
    }

    @NonNull
    public String getContent() {
        return content;
    }

    @NonNull
    public String getDate() {
        return date;
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
