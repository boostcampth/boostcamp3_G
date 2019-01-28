package com.boostcamp.dreampicker.view.feed;

public class User {
    private String id;
    private String name;
    // TODO : 이후 url로 변경
    private int profileImageResource;

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

    public int getProfileImageResource() {
        return profileImageResource;
    }

    public void setProfileImageResource(int profileImageResource) {
        this.profileImageResource = profileImageResource;
    }
}
