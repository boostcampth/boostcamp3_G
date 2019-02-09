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
    private List<String> tagA; // 1번 이미지 태그 리스트
    @Nullable
    private List<String> tagB; // 2번 이미지 태그 리스트
    @NonNull
    private UserItem userItem; // 작성자 정보
    @NonNull
    private String content; // 본문
    @NonNull
    private String date; // 업로드 일자

    private int countA; // 1번 투표 수
    private int countB; // 2번 투표 수
    private int totalCount; // 총합 투표 수
    private int voteFlag; // 본인 투표 위치

    public FeedItem() { }

    public FeedItem(@NonNull String id,
                    @NonNull String imageUrlA,
                    @NonNull String imageUrlB,
                    @Nullable List<String> tagA,
                    @Nullable List<String> tagB,
                    @NonNull UserItem userItem,
                    @NonNull String content,
                    @NonNull String date,
                    int countA,
                    int countB,
                    int totalCount,
                    int voteFlag) {

        this.id = id;
        this.imageUrlA = imageUrlA;
        this.imageUrlB = imageUrlB;
        this.tagA = tagA;
        this.tagB = tagB;
        this.userItem = userItem;
        this.content = content;
        this.date = date;
        this.countA = countA;
        this.countB = countB;
        this.totalCount = totalCount;
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
    public List<String> getTagA() {
        return tagA;
    }

    @Nullable
    public List<String> getTagB() {
        return tagB;
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

    public int getTotalCount() {
        return totalCount;
    }

    public int getVoteFlag() {
        return voteFlag;
    }
}
