package com.pascal.oms.service;

import com.pascal.oms.entities.Donor;
import com.pascal.oms.repo.DonorRepo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DonorService {

    private final DonorRepo donorRepo;

    public DonorService(DonorRepo donorRepo) {
        this.donorRepo = donorRepo;
    }

    public Donor registerDonor(Donor donor) {
        try {
            if (!isValidDonor(donor)) {
                System.out.println("Invalid donor details.");
                return null;
            }
            donorRepo.saveDonor(donor);
            return donor;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    //
   public Donor getDonorById(int id) {
    return donorRepo.getDonorById(id);
}

    public List<Donor> getAllDonors() {
        try {
           return this.donorRepo.getAllDonors();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
//
    public boolean updateDonor(Donor donor) {
    try {
        if (!isValidDonor(donor)) {
            System.out.println("Invalid donor details.");
            return false;
        }
        return donorRepo.updateDonor(donor);
    } catch (Exception e) {
        e.printStackTrace();
        return false;
    }
}
//
  public boolean deleteDonor(int id) {
    try {
        return donorRepo.deleteDonor(id);
    } catch (Exception e) {
        e.printStackTrace();
        return false;
    }
}
    public List<Donor> getDonorsByBloodType(String bloodType) {
        return donorRepo.findDonorsByBloodType(bloodType);
    }


    private boolean isValidDonor(Donor donor) {
        return donor.getName() != null && !donor.getName().isEmpty()
                && donor.getAge() > 0
                && donor.getBloodType() != null && !donor.getBloodType().isEmpty()
                && donor.getContact() != null && !donor.getContact().isEmpty();
    }
}
