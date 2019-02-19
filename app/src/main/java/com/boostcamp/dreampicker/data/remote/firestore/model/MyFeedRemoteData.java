package com.boostcamp.dreampicker.data.remote.firestore.model;

import com.google.firebase.firestore.ServerTimestamp;

import java.util.Date;

public class MyFeedRemoteData {

    private String content;
    @ServerTimestamp
    private Date date;
    private String imageUrlA;
    private String imageUrlB;
    private boolean isEnded;

    @SuppressWarnings("unused")
    public MyFeedRemoteData() {
    }

    public MyFeedRemoteData(String content,
                            String imageUrlA,
                            String imageUrlB,
                            boolean isEnded) {

        this.content = content;
        this.date = null;
        this.imageUrlA = imageUrlA;
        this.imageUrlB = imageUrlB;
        this.isEnded = isEnded;
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
}
