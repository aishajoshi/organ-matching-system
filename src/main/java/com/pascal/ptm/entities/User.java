package com.pascal.ptm.entities;

import java.time.LocalDateTime;

public class User {
    int userId;
    String fullName;
    String email;
    int phone;
    String password;
    String user_type;
    LocalDateTime createdAt;



    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getfullName() {
        return fullName;
    }

    public void setfullName(String fullName) {

        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String  getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +

                ", email='" + email + '\'' +
                ", phone='" + phone + '\''+
                ", password='" + password + '\'' +
                ", createdAt=" + createdAt +
                ", fullName='" + fullName + '\'' +
                '}';
    }

}


