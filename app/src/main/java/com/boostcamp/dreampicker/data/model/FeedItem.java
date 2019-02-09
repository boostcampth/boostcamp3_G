package com.boostcamp.dreampicker.data.model;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class FeedItem {
    @NonNull
    private final String id; // 피드 ID : 해당 피드를 구별할 수 있는 키로 사용됩니다.
    @NonNull
    private final String imageUrlA; // 1번 이미지 URL
    @NonNull
    private final String imageUrlB; // 2번 이미지 URL
    @Nullable
    private final List<String> tagListA; // 1번 이미지 태그 리스트
    @Nullable
    private final List<String> tagListB; // 2번 이미지 태그 리스트
    @NonNull
    private final User user; // 작성자 정보
    @NonNull
    private final String content; // 본문
    @NonNull
    private final String date; // 업로드 일자

    private final int countA; // 1번 투표 수
    private final int countB; // 2번 투표 수

    // [0] : 미투표 || [1] : 1번 투표 || [2] : 2번 투표
    private final int selection; // 본인 투표 위치

    public FeedItem(@NonNull String id,
                    @NonNull String imageUrlA,
                    @NonNull String imageUrlB,
                    @Nullable List<String> tagListA,
                    @Nullable List<String> tagListB,
                    @NonNull User user,
                    @NonNull String content,
                    @NonNull String date,
                    int countA,
                    int countB,
                    int selection) {

        this.id = id;
        this.imageUrlA = imageUrlA;
        this.imageUrlB = imageUrlB;
        this.tagListA = tagListA;
        this.tagListB = tagListB;
        this.user = user;
        this.content = content;
        this.date = date;
        this.countA = countA;
        this.countB = countB;
        this.selection = selection;
    }

    @NonNull
    public String getId() {
        return id;
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

    public int getCountA() {
        return countA;
    }

    public int getCountB() {
        return countB;
    }

    public int getSelection() {
        return selection;
    }
}
