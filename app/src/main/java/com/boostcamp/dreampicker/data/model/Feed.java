package com.boostcamp.dreampicker.data.model;

import java.util.Date;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class Feed {
    @NonNull
    private final String id; // 피드 ID : 해당 피드를 구별할 수 있는 키로 사용됩니다.
    @NonNull
    private final User user; // 작성자 정보
    @NonNull
    private final String content; // 본문
    @NonNull
    private final Date date; // 업로드 일자
    @NonNull
    private final VoteSelectionItem itemA; // 1번 이미지 정보
    @NonNull
    private final VoteSelectionItem itemB; // 2번 이미지 정보
    @Nullable
    private String selectionId; // 본인 투표 위치

    public Feed(@NonNull String id,
                @NonNull User user,
                @NonNull String content,
                @NonNull Date date,
                @NonNull VoteSelectionItem itemA,
                @NonNull VoteSelectionItem itemB,
                @Nullable String selectionId) {
        this.id = id;
        this.user = user;
        this.content = content;
        this.date = date;
        this.itemA = itemA;
        this.itemB = itemB;
        this.selectionId = selectionId;
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
    public Date getDate() {
        return date;
    }

    @NonNull
    public VoteSelectionItem getItemA() {
        return itemA;
    }

    @NonNull
    public VoteSelectionItem getItemB() {
        return itemB;
    }

    @Nullable
    public String getSelectionId() {
        return selectionId;
    }

    public void setSelectionId(@NonNull final String selectionId) {
        this.selectionId = selectionId;
    }
}
