package com.boostcamp.dreampicker.model;

public class Feed {
    private String id;
    private Image leftImage;
    private Image rightImage;
    private User user;
    private String content;
    private String date;

    // TODO : 마감 조건 확인 이후 작업
    private boolean isEnded;

    // TODO : Firestore 연동 이후 작업
    private int leftCount;
    private int rightCount;

    private int voteFlag;

    public Feed() { }

    public Feed(String id,
                Image leftImage,
                Image rightImage,
                User user,
                String content,
                String date,
                boolean isEnded) {

        this.id = id;
        this.leftImage = leftImage;
        this.rightImage = rightImage;
        this.user = user;
        this.content = content;
        this.date = date;
        this.isEnded = isEnded;
        this.leftCount = 3;
        this.rightCount = 4;
        this.voteFlag = 0;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Image getLeftImage() {
        return leftImage;
    }

    public void setLeftImage(Image leftImage) {
        this.leftImage = leftImage;
    }

    public Image getRightImage() {
        return rightImage;
    }

    public void setRightImage(Image rightImage) {
        this.rightImage = rightImage;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
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
}