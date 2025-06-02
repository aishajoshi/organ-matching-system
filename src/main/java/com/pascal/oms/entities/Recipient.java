package com.pascal.oms.entities;

public class Recipient {

    private int recipientID;
    private String name;
    private int age;
    private String bloodType;
    private String requiredOrgan;
    private String contact;
    private String urgencyLevel;

    // Organ.java-specific fields
    private Integer meldScore;      // For liver recipients
    private Integer waitingTime;    // In days (for kidney & heart)

    public Recipient() {}

    // Getters and Setters

    public int getRecipientID() {
        return recipientID;
    }

    public void setRecipientID(int recipientID) {
        this.recipientID = recipientID;
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

    public String getBloodType() {
        return bloodType;
    }

    public void setBloodType(String bloodType) {
        this.bloodType = bloodType;
    }

    public String getRequiredOrgan() {
        return requiredOrgan;
    }

    public void setRequiredOrgan(String requiredOrgan) {
        this.requiredOrgan = requiredOrgan;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getUrgencyLevel() {
        return urgencyLevel;
    }

    public void setUrgencyLevel(String urgencyLevel) {
        this.urgencyLevel = urgencyLevel;
    }

    public Integer getMeldScore() {
        return meldScore;
    }

    public void setMeldScore(Integer meldScore) {
        this.meldScore = meldScore;
    }

    public Integer getWaitingTime() {
        return waitingTime;
    }

    public void setWaitingTime(Integer waitingTime) {
        this.waitingTime = waitingTime;
    }
}
