package com.boostcamp.dreampicker.data.model;

import java.util.Objects;

public class User {
    private String id; // 유저 아이디
    private String name; // 유저 이름
    private String profileImageUrl; // 유저 프로필 사진 url

    public User() { }

    public User(String id, String name, String profileImageUrl) {
        this.id = id;
        this.name = name;
        this.profileImageUrl = profileImageUrl;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;
        return Objects.equals(id, user.id) &&
                Objects.equals(name, user.name) &&
                Objects.equals(profileImageUrl, user.profileImageUrl);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, profileImageUrl);
    }
}
