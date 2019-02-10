package com.boostcamp.dreampicker.data.model;

import java.util.Map;
import java.util.Objects;

public class LegacyFeed {
    private String id; // 피드 아이디
    private Map<String, LegacyImage> imageMap; // 피드 이미지 리스트
    private LegacyUser user; // 업로더
    private String content; // 본문
    private String date; // 업로드 일자

    private int leftCount; // L 투표 수
    private int rightCount; // R 투표 수
    private int voteFlag; // 투표 합

    // Todo : 아직 미정
    private boolean isEnded;

    public LegacyFeed() { }

    public LegacyFeed(LegacyFeed feed) {
        this.id = feed.id;
        this.imageMap = feed.imageMap;
        this.user = feed.user;
        this.content = feed.content;
        this.date = feed.date;
        this.isEnded = feed.isEnded;
        this.leftCount = feed.leftCount;
        this.rightCount = feed.rightCount;
        this.voteFlag = feed.voteFlag;
    }

    public LegacyFeed(String id,
                      Map<String, LegacyImage> imageMap,
                      LegacyUser user,
                      String content,
                      String date,
                      boolean isEnded) {

        this.id = id;
        this.imageMap = imageMap;
        this.user = user;
        this.content = content;
        this.date = date;
        this.isEnded = isEnded;
        this.leftCount = 3;
        this.rightCount = 4;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Map<String, LegacyImage> getImageMap() {
        return imageMap;
    }

    public void setImageMap(Map<String, LegacyImage> imageMap) {
        this.imageMap = imageMap;
    }

    public LegacyUser getUser() {
        return user;
    }

    public void setUser(LegacyUser user) {
        this.user = user;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public boolean isEnded() {
        return isEnded;
    }

    public void setEnded(boolean ended) {
        isEnded = ended;
    }

    public int getLeftCount() {
        return leftCount;
    }

    public void setLeftCount(int leftCount) {
        this.leftCount = leftCount;
    }

    public int getRightCount() {
        return rightCount;
    }

    public void setRightCount(int rightCount) {
        this.rightCount = rightCount;
    }

    public int getVoteFlag() {
        return voteFlag;
    }

    public void setVoteFlag(int voteFlag) {
        this.voteFlag = voteFlag;
    }

    public int getVoteCount() {
        return leftCount + rightCount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        final LegacyFeed feed = (LegacyFeed) o;
        return voteFlag == feed.voteFlag;
    }

    public int hashCode() {
        return Objects.hash(voteFlag);
    }
}