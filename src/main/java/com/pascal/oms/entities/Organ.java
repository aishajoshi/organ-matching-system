//package com.pascal.oms.entities;
//
//import jakarta.persistence.*;
//
//@Entity
//@Table(name = "organ")
//public class Organ {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "OrganID")
//    private int organId;
//
//    @Column(name = "Type", nullable = false)
//    private String type; // Heart, Kidney, Liver
//
//    @Column(name = "AvailabilityStatus", nullable = false)
//    private String availabilityStatus; // Available, Matched, Transplanted, etc.
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "DonorID", nullable = false)
//    private Donor donor;
//
//    // Constructors
//    public Organ() {}
//
//    public Organ(String type, String availabilityStatus, Donor donor) {
//        this.type = type;
//        this.availabilityStatus = availabilityStatus;
//        this.donor = donor;
//    }
//
//    // Getters and Setters
//    public int getOrganId() {
//        return organId;
//    }
//
//    public void setOrganId(int organId) {
//        this.organId = organId;
//    }
//
//    public String getType() {
//        return type;
//    }
//
//    public void setType(String type) {
//        this.type = type;
//    }
//
//    public String getAvailabilityStatus() {
//        return availabilityStatus;
//    }
//
//    public void setAvailabilityStatus(String availabilityStatus) {
//        this.availabilityStatus = availabilityStatus;
//    }
//
//    public Donor getDonor() {
//        return donor;
//    }
//
//    public void setDonor(Donor donor) {
//        this.donor = donor;
//    }
//}
