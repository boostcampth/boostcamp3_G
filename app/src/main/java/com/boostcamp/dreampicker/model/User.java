package com.boostcamp.dreampicker.model;

public class User {
    private String userId;
    private String name;
    // TODO : 이후 url로 변경
    private int profileImageResource;

    public User(String id, String name, int profileImageResource) {
        this.userId = id;
        this.name = name;
        this.profileImageResource = profileImageResource;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getProfileImageResource() {
        return profileImageResource;
    }

    public void setProfileImageResource(int profileImageResource) {
        this.profileImageResource = profileImageResource;
    }
}
