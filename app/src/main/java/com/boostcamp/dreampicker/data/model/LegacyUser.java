package com.boostcamp.dreampicker.data.model;

public class LegacyUser {
    private String id; // 유저 아이디
    private String name; // 유저 이름
    private String profileImageUrl; // 유저 프로필 사진 url
    // TODO : 이후 url로 변경
    private int profileImageResource; // 유저 임시 사진?

    public LegacyUser() { }

    public LegacyUser(String id, String name, int profileImageResource) {
        this.id = id;
        this.name = name;
        this.profileImageResource = profileImageResource;
    }

    public LegacyUser(String id, String name, String profileImageUrl, int profileImageResource) {
        this.id = id;
        this.name = name;
        this.profileImageUrl = profileImageUrl;
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