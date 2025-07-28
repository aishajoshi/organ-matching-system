package com.pascal.oms.entities;

import java.util.List;

public class Recipient {

    private String recipientId; // Changed to String for custom ID
    private String name;
    private int age;
    private String bloodGroup;
    private String phone;
    private String email;
    private String status;
    private List<Organ> organs;
    private int urgencyLevel;
    private int waitingTime;
    private String requiredOrgan; // New field for required organ
    private float meldScore;

    public Recipient() {}

    public String getRecipientId() {
        return recipientId;
    }

    public void setRecipientId(String recipientId) {
        this.recipientId = recipientId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getBloodGroup() {
        return bloodGroup;
    }

    public void setBloodGroup(String bloodGroup) {
        this.bloodGroup = bloodGroup;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Organ> getOrgans() {
        return organs;
    }

    public void setOrgans(List<Organ> organs) {
        this.organs = organs;
    }

    public int getUrgencyLevel() {
        return urgencyLevel;
    }

    public void setUrgencyLevel(int urgencyLevel) {
        this.urgencyLevel = urgencyLevel;
    }

    public int getWaitingTime() {
        return waitingTime;
    }

    public void setWaitingTime(int waitingTime) {
        this.waitingTime = waitingTime;
    }

    public String getRequiredOrgan() {
        return requiredOrgan;
    }

    public void setRequiredOrgan(String requiredOrgan) {
        this.requiredOrgan = requiredOrgan;
    }

    public float getMeldScore() {
        return meldScore;
    }

    public void setMeldScore(float meldScore) {
        this.meldScore = meldScore;
    }
}
