package com.boostcamp.dreampicker.data.model;

import android.net.Uri;

import java.util.ArrayList;
import java.util.List;

public class Image {
    private String id; // 이미지 아이디
    private Uri uri; // 업로드 할때 들어가는 uri
    private int image; // 임시 데이터
    private String imageUrl; // 파이어베이스로부터 받아오는 경로
    private List<String> tagList; // 태그 리스트

    public Image() {
        this.tagList = new ArrayList<>();
    }

    public Image(String id, String imageUrl, List<String> tagList) {
        this.id = id;
        this.imageUrl = imageUrl;
        this.tagList = tagList;
    }

    public Image(String id, Uri uri, List<String> tagList) {
        this.id = id;
        this.uri = uri;
        this.tagList = tagList;
    }

    public Image(String id, int image, List<String> tagList) {
        this.id = id;
        this.image = image;
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

    public List<String> getTagList() {
        return tagList;
    }

    public void setTagList(List<String> tagList) {
        this.tagList = tagList;
    }


}