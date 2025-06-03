package com.pascal.oms.entities;
/*
 * Created by Ashok Kumar Pant
 * Email: asokpant@gmail.com
 * Created on 02/06/2025.
 */

public enum UserStatus {
    ACTIVE("ACTIVE"),
    INACTIVE("INACTIVE"),
    PENDING("PENDING"),
    SUSPENDED("SUSPENDED"),
    ;

    private final String status;

    UserStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return status;
    }
}
