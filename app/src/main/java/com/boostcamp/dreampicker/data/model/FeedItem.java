package com.boostcamp.dreampicker.data.model;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class FeedItem {
    @NonNull
    private String id; // 피드 ID : 해당 피드를 구별할 수 있는 키로 사용됩니다.
    @NonNull
    private String imageUrlA; // 1번 이미지 URL
    @NonNull
    private String imageUrlB; // 2번 이미지 URL
    @Nullable
    private List<String> tagListA; // 1번 이미지 태그 리스트
    @Nullable
    private List<String> tagListB; // 2번 이미지 태그 리스트
    @NonNull
    private UserItem userItem; // 작성자 정보
    @NonNull
    private String content; // 본문
    @NonNull
    private String date; // 업로드 일자

    private int countA; // 1번 투표 수
    private int countB; // 2번 투표 수
    private int voteFlag; // 본인 투표 위치

    public FeedItem() { }

    public FeedItem(@NonNull String id,
                    @NonNull String imageUrlA,
                    @NonNull String imageUrlB,
                    @Nullable List<String> tagListA,
                    @Nullable List<String> tagListB,
                    @NonNull UserItem userItem,
                    @NonNull String content,
                    @NonNull String date,
                    int countA,
                    int countB,
                    int voteFlag) {

        this.id = id;
        this.imageUrlA = imageUrlA;
        this.imageUrlB = imageUrlB;
        this.tagListA = tagListA;
        this.tagListB = tagListB;
        this.userItem = userItem;
        this.content = content;
        this.date = date;
        this.countA = countA;
        this.countB = countB;
        this.voteFlag = voteFlag;
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
    public UserItem getUserItem() {
        return userItem;
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

    public int getVoteFlag() {
        return voteFlag;
    }
}
