package com.boostcamp.dreampicker.data.source.firestore.model;

import java.util.Date;

public class MyFeedRemoteData {

    private String content;
    private Date date;
    private String imageUrlA;
    private String imageUrlB;
    private boolean isEnded;

    public MyFeedRemoteData() {
    }

    public MyFeedRemoteData(String content,
                            Date date,
                            String imageUrlA,
                            String imageUrlB,
                            boolean isEnded) {

        this.content = content;
        this.date = date;
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
