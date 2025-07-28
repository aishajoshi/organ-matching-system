package com.pascal.oms.entities;
/*
 * Created by Ashok Kumar Pant
 * Email: asokpant@gmail.com
 * Created on 02/06/2025.
 */

public enum OrganStatus {
    UNKNOWN("ACTIVE"),
    INACTIVE("UNKNOWN"),
    AVAILABLE("AVAILABLE"),
    MATCHED("MATCHED"),
    UNAVAILABLE("UNAVAILABLE"),
    APPROVED("APPROVED"),
    REQUIRED("REQUIRED"),
    REJECTED("REJECTED");;

    private final String status;

    OrganStatus(String status) {
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
