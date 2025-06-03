package com.pascal.oms.service;

import com.pascal.oms.entities.Donor;
import com.pascal.oms.entities.UserStatus;
import com.pascal.oms.repo.DonorRepo;
import com.pascal.oms.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service
public class DonorService {

    private final DonorRepo donorRepo;

    @Autowired
    public DonorService(DonorRepo donorRepo) {
        this.donorRepo = donorRepo;
    }

    public Donor registerDonor(Donor donor) {
        try {
            if (!isValidDonor(donor)) {
                System.out.println("Invalid donor details.");
                return null;
            }
            donor.setDonorId(Utils.UUID());
            donor.setStatus(UserStatus.ACTIVE.name());
            donorRepo.saveDonor(donor);
            return donor;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }


    public List<Donor> getAllDonors() {
        try {
            return this.donorRepo.getAllDonors();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

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


    private boolean isValidDonor(Donor donor) {
        return donor.getName() != null && !donor.getName().isEmpty()
                && donor.getAge() > 0
                && donor.getBloodGroup() != null && !donor.getBloodGroup().isEmpty()
                && donor.getPhone() != null && !donor.getPhone().isEmpty();
    }
}
