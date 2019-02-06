package com.boostcamp.dreampicker.data.model;

import android.net.Uri;

import java.util.ArrayList;
import java.util.List;

public class Image {
    private String id;

    // Todo 이후 변경 예정
    private Uri uri;

    private int image;
    // TODO : 이후 URL로 변경 예정
    private String imageUrl;
    // TODO : 이후 TagGroup 적용
    private String tag;
    private List<String> tagList;

    public Image() {
        this.tagList = new ArrayList<>();
    }

    public Image(String id, int image, String tag) {
        this.id = id;
        this.image = image;
        this.tag = tag;
    }

    public Image(String id, Uri uri, List<String> tagList) {
        this.id = id;
        this.uri = uri;
        this.tagList = tagList;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Uri getUri() {
        return uri;
    }

    public void setUri(Uri uri) {
        this.uri = uri;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public List<String> getTagList() {
        return tagList;
    }

    public void setTagList(List<String> tagList) {
        this.tagList = tagList;
    }


}