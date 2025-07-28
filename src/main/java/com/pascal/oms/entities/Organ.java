package com.pascal.oms.entities;

import java.time.LocalDateTime;
import java.util.List;

public class Organ {
    private String organId;
    private String organName;
    private String donorId;
    private String recipientId;
    private String bloodGroup;
    private OrganStatus status;
    private List<String> compatibleBloodTypes;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime donatedDate;
    private LocalDateTime expiryDate;
    private LocalDateTime receivedDate;

    public String getOrganId() {
        return organId;
    }

    public void setOrganId(String organId) {
        this.organId = organId;
    }

    public String getOrganName() {
        return organName;
    }

    public void setOrganName(String organName) {
        this.organName = organName;
    }

    public String getDonorId() {
        return donorId;
    }

    public void setDonorId(String donorId) {
        this.donorId = donorId;
    }

    public String getRecipientId() {
        return recipientId;
    }

    public void setRecipientId(String recipientId) {
        this.recipientId = recipientId;
    }


    public String getBloodGroup() {
        return bloodGroup;
    }

    public void setBloodGroup(String bloodGroup) {
        this.bloodGroup = bloodGroup;
    }

    public OrganStatus getStatus() {
        return status;
    }

    public void setStatus(OrganStatus status) {
        this.status = status;
    }

    public List<String> getCompatibleBloodTypes() {
        return compatibleBloodTypes;
    }

    public void setCompatibleBloodTypes(List<String> compatibleBloodTypes) {
        this.compatibleBloodTypes = compatibleBloodTypes;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public LocalDateTime getDonatedDate() {
        return donatedDate;
    }

    public void setDonatedDate(LocalDateTime donatedDate) {
        this.donatedDate = donatedDate;
    }

    public LocalDateTime getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(LocalDateTime expiryDate) {
        this.expiryDate = expiryDate;
    }

    public LocalDateTime getReceivedDate() {
        return receivedDate;
    }

    public void setReceivedDate(LocalDateTime receivedDate) {
        this.receivedDate = receivedDate;
    }

    @Override
    public String toString() {
        return "Organ{" +
                "organId='" + organId + '\'' +
                ", organName='" + organName + '\'' +
                ", donorId='" + donorId + '\'' +
                ", recipientId='" + recipientId + '\'' +
                ", bloodGroup=" + bloodGroup +
                ", status=" + status +
                ", compatibleBloodTypes=" + compatibleBloodTypes +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", donatedDate=" + donatedDate +
                ", expiryDate=" + expiryDate +
                ", receivedDate=" + receivedDate +
                '}';
    }
}
