package com.boostcamp.dreampicker.model;

public class Image {
    private String id;
    // TODO : 이후 URL로 변경 예정
    private int resourceId;
    private String[] tags;

    public Image(String id, int resourceId, String[] tags) {
        this.id = id;
        this.resourceId = resourceId;
        this.tags = tags;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getResourceId() {
        return resourceId;
    }

    public void setResourceId(int resourceId) {
        this.resourceId = resourceId;
    }

    public String[] getTags() {
        return tags;
    }

    public void setTags(String[] tags) {
        this.tags = tags;
    }
}
