package com.pascal.oms.entities;
/*
 * Created by Ashok Kumar Pant
 * Email: asokpant@gmail.com
 * Created on 02/06/2025.
 */

public enum Status {
    ACTIVE("ACTIVE"),
    INACTIVE("INACTIVE"),
    PENDING("PENDING"),
    SUSPENDED("SUSPENDED"),
    ;

    private final String status;

    Status(String status) {
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
