package com.pascal.oms.service;

import com.pascal.oms.entities.Organ;
import com.pascal.oms.repo.OrganRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrganService {

    private final OrganRepo organRepo;

    @Autowired
    public OrganService(OrganRepo organRepo) {
        this.organRepo = organRepo;
    }

    public boolean saveOrgan(Organ organ) {
        if (!isValidOrgan(organ)) {
            System.out.println("Invalid organ details.");
            return false;
        }

        try {
            organ.setCreatedAt(LocalDateTime.now());
            organ.setUpdatedAt(LocalDateTime.now());
            organRepo.saveOrgan(organ);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateOrgan(Organ organ) {
        if (!isValidOrgan(organ)) {
            System.out.println("Invalid organ details for update.");
            return false;
        }

        try {
            organ.setUpdatedAt(LocalDateTime.now());
            return organRepo.updateOrgan(organ);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Organ> getAllOrgans() {
        return organRepo.getAllOrgans();
    }

    public Organ getOrganById(String organId) {
        return organRepo.getOrganById(organId);
    }

    private boolean isValidOrgan(Organ organ) {
        return organ.getOrganId() != null && !organ.getOrganId().isBlank()
                && organ.getOrganName() != null && !organ.getOrganName().isBlank()
                && organ.getOrganType() != null
                && organ.getBloodGroup() != null
                && organ.getStatus() != null
                && organ.getDonorId() != null && !organ.getDonorId().isBlank();
    }
}
