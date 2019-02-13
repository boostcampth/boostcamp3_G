package com.boostcamp.dreampicker.data.model;

import java.util.Date;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class FeedDetail {

    @NonNull
    private final String id; // 피드 ID : 해당 피드를 구별할 수 있는 키로 사용됩니다.
    @NonNull
    private final User writer; // 작성자 정보
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
    private boolean isEnded; // 투표마감 유무

    public FeedDetail(@NonNull String id,
                      @NonNull User writer,
                      @NonNull String content,
                      @NonNull Date date,
                      @NonNull VoteSelectionItem itemA,
                      @NonNull VoteSelectionItem itemB,
                      @Nullable String selectionId,
                      boolean isEnded) {
        this.id = id;
        this.writer = writer;
        this.content = content;
        this.date = date;
        this.itemA = itemA;
        this.itemB = itemB;
        this.selectionId = selectionId;
        this.isEnded = isEnded;
    }

    @NonNull
    public String getId() {
        return id;
    }

    @NonNull
    public User getWriter() {
        return writer;
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

    public boolean isEnded() {
        return isEnded;
    }
}
