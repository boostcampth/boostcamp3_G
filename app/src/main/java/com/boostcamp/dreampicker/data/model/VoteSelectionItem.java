package com.boostcamp.dreampicker.data.model;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class VoteSelectionItem {
    @NonNull
    private final String id; // 아이템 ID
    @NonNull
    private final String imageUrl; // 이미지 URL
    @Nullable
    private final List<String> tagList; // 이미지 태그 리스트
    private final int voteCount; // 해당 이미지 투표 유저 수

    public VoteSelectionItem(@NonNull String id,
                             @NonNull String imageUrl,
                             @Nullable List<String> tagList,
                             int voteCount) {
        this.id = id;
        this.imageUrl = imageUrl;
        this.tagList = tagList;
        this.voteCount = voteCount;
    }

    @NonNull
    public String getId() {
        return id;
    }

    @NonNull
    public String getImageUrl() {
        return imageUrl;
    }

    @Nullable
    public List<String> getTagList() {
        return tagList;
    }

    public int getVoteCount() {
        return voteCount;
    }
}
