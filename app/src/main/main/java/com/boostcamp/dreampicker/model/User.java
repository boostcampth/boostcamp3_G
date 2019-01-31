package com.boostcamp.dreampicker.model;

public class User {
    private String id;
    private String name;
    private String profileImageUrl;
    // TODO : 이후 url로 변경
    private int profileImageResource;

    public User() {
    }

    public User(String id, String name, int profileImageResource) {
        this.id = id;
        this.name = name;
        this.profileImageResource = profileImageResource;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }

    public int getProfileImageResource() {
        return profileImageResource;
    }

    public void setProfileImageResource(int profileImageResource) {
        this.profileImageResource = profileImageResource;
    }
}