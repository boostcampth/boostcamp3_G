package com.boostcamp.dreampicker.model;

public class Image {
    private String imageId;
    // TODO : 이후 URL로 변경 예정
    private int image;
    // TODO : 이후 TagGroup 적용
    private String tag;

    public Image(String id, int image, String tag) {
        this.imageId = id;
        this.image = image;
        this.tag = tag;
    }

    public String getImageId() {
        return imageId;
    }

    public void setImageId(String imageId) {
        this.imageId = imageId;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
}
