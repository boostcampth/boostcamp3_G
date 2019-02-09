package com.boostcamp.dreampicker.data.model;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class Image {
    @NonNull
    private final String id; // 이미지 URL
    @Nullable
    private final List<String> tagList; // 이미지 태그 리스트
    private final int voteCount; // 해당 이미지 투표 유저 수

    public Image(@NonNull String id,
                 @Nullable List<String> tagList,
                 int voteCount) {
        this.id = id;
        this.tagList = tagList;
        this.voteCount = voteCount;
    }

    @NonNull
    public String getId() {
        return id;
    }

    @Nullable
    public List<String> getTagList() {
        return tagList;
    }

    public int getVoteCount() {
        return voteCount;
    }
}
