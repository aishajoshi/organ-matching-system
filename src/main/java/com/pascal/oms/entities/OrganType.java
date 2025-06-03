package com.pascal.oms.entities;
/*
 * Created by Ashok Kumar Pant
 * Email: asokpant@gmail.com
 * Created on 03/06/2025.
 */

public enum OrganType {
    KIDNEY("Kidney"),
    LIVER("Liver"),
    HEART("Heart"),
    LUNG("Lung"),
    PANCREAS("Pancreas"),
    INTESTINE("Intestine");

    private final String displayName;

    OrganType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }
}
