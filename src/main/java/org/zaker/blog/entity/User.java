package org.zaker.blog.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.sql.Timestamp;

public class User {
    private Long id;
    private String username;
    private String avatar;
    @JsonIgnore
    private String encryptedPassword;
    private Timestamp createdAt;
    private Timestamp updatedAt;


    public User(Long id, String username, String avatar, String encryptedPassword, Timestamp createAt, Timestamp updatedAt) {
        this.id = id;
        this.username = username;
        this.avatar = avatar;
        this.encryptedPassword = encryptedPassword;
        this.createdAt = createAt;
        this.updatedAt = updatedAt;
    }

    public User() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getEncryptedPassword() {
        return encryptedPassword;
    }

    public void setEncryptedPassword(String encryptedPassword) {
        this.encryptedPassword = encryptedPassword;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }
}
