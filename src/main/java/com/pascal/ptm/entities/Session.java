package com.pascal.ptm.entities;
/*
 * Created by Ashok Kumar Pant
 * Email: asokpant@gmail.com
 * Created on 22/03/2024.
 */

public class Session {
    private User user;
    private String authToken;

    public boolean isAuthenticated() {
        return authToken != null;
    }


    public Session() {
    }

    public Session(User user, String authToken) {
        this.user = user;
        this.authToken = authToken;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    @Override
    public String toString() {
        return "Session{" +
                "user=" + user +
                ", authToken='" + authToken + '\'' +
                '}';
    }
}
