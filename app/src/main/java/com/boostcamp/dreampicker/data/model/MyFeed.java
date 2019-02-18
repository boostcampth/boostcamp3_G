package com.boostcamp.dreampicker.data.model;

import java.util.Date;
import java.util.Objects;

import androidx.annotation.NonNull;

public class MyFeed {
    private String id; // 피드 ID
    private String content; // 내용
    private Date date; // 작성 날짜
    private String imageUrlA; // 첫번째 이미지 URL
    private String imageUrlB; // 두번째 이미지 URL
    private boolean isEnded; // 투표 마감 여부

    public MyFeed() {
    }

    public MyFeed(String id,
                  String content,
                  Date date,
                  String imageUrlA,
                  String imageUrlB,
                  boolean isEnded) {
        this.id = id;
        this.content = content;
        this.date = date;
        this.imageUrlA = imageUrlA;
        this.imageUrlB = imageUrlB;
        this.isEnded = isEnded;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public Date getDate() {
        return date;
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

    @NonNull
    @Override
    public String toString() {
        return "MyFeed{" + '\n' +
                "id='" + id + '\n' +
                ", content='" + content + '\n' +
                ", date='" + date + '\n' +
                ", imageUrlA='" + imageUrlA + '\n' +
                ", imageUrlB='" + imageUrlB + '\n' +
                ", isEnded=" + isEnded +
                '}' + '\n';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MyFeed feed = (MyFeed) o;
        return isEnded == feed.isEnded &&
                Objects.equals(id, feed.id) &&
                Objects.equals(content, feed.content) &&
                Objects.equals(date, feed.date) &&
                Objects.equals(imageUrlA, feed.imageUrlA) &&
                Objects.equals(imageUrlB, feed.imageUrlB);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, content, date, imageUrlA, imageUrlB, isEnded);
    }
}
