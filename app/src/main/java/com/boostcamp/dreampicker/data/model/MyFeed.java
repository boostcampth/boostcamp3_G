package com.boostcamp.dreampicker.data.model;

import androidx.annotation.NonNull;

public class MyFeed {
    private String id; // 피드 ID
    private String content; // 내용
    private String imageUrlA; // 첫번째 이미지 URL
    private String imageUrlB; // 두번째 이미지 URL
    private boolean isEnded; // 투표 마감 여부

    public MyFeed() {
    }

    public MyFeed(@NonNull String id,
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

    public String getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public String getImageUrlA() {
        return imageUrlA;
    }

    public String getImageUrlB() {
        return imageUrlB;
    }

    public boolean isEnded() {
        return isEnded;
    }

    public void setEnded(boolean ended) {
        isEnded = ended;
    }

    @Override
    public String toString() {
        return "MyFeed{" + '\n' +
                "id='" + id + '\n' +
                ", content='" + content + '\n' +
                ", imageUrlA='" + imageUrlA + '\n' +
                ", imageUrlB='" + imageUrlB + '\n' +
                ", isEnded=" + isEnded +
                '}' + '\n';
    }
}
