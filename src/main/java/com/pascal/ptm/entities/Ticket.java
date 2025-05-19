package com.pascal.ptm.entities;
/*
 * Created by Ashok Kumar Pant
 * Email: asokpant@gmail.com
 * Created on 27/02/2024.
 */

import java.time.LocalDateTime;

public class Ticket {
    int ticketId;
    String ticketNumber;
    String vehicleNumber;
    LocalDateTime entryTime;
    LocalDateTime exitTime;
    long totalTime;
    String totalTimeStr;
    float totalAmount;
    String phone;
    String note;
    int createdBy;
    int updatedBy;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;

    public int getTicketId() {
        return ticketId;
    }

    public void setTicketId(int ticketId) {
        this.ticketId = ticketId;
    }

    public String getVehicleNumber() {
        return vehicleNumber;
    }

    public void setVehicleNumber(String vehicleNumber) {
        this.vehicleNumber = vehicleNumber;
    }

    public LocalDateTime getEntryTime() {
        return entryTime;
    }

    public void setEntryTime(LocalDateTime entryTime) {
        this.entryTime = entryTime;
    }

    public LocalDateTime getExitTime() {
        return exitTime;
    }

    public void setExitTime(LocalDateTime exitTime) {
        this.exitTime = exitTime;
    }

    public long getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(long totalTime) {
        this.totalTime = totalTime;
    }

    public float getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(float totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public int getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(int createdBy) {
        this.createdBy = createdBy;
    }

    public int getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(int updatedBy) {
        this.updatedBy = updatedBy;
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

    public void setTicketNumber(String parkingTicketNumber) {
        this.ticketNumber = parkingTicketNumber;
    }

    public String getTicketNumber() {
        return ticketNumber;
    }

    public String getTotalTimeStr() {
        return totalTimeStr;
    }

    public void setTotalTimeStr(String totalTimeStr) {
        this.totalTimeStr = totalTimeStr;
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "ticketId=" + ticketId +
                ", ticketNumber='" + ticketNumber + '\'' +
                ", vehicleNumber='" + vehicleNumber + '\'' +
                ", entryTime=" + entryTime +
                ", exitTime=" + exitTime +
                ", totalTime=" + totalTime +
                ", totalTimeStr='" + totalTimeStr + '\'' +
                ", totalAmount=" + totalAmount +
                ", phone='" + phone + '\'' +
                ", note='" + note + '\'' +
                ", createdBy=" + createdBy +
                ", updatedBy=" + updatedBy +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
