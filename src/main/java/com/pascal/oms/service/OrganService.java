package com.pascal.oms.service;

import com.pascal.oms.entities.BloodGroup;
import com.pascal.oms.entities.Organ;
import com.pascal.oms.entities.OrganStatus;
import com.pascal.oms.entities.OrganType;
import com.pascal.oms.repo.OrganRepo;
import com.pascal.oms.utils.Utils;
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
        if (!isValidOrganForSave(organ)) {
            System.out.println("Invalid organ details.");
            return false;
        }

        try {
            organ.setOrganId(Utils.UUID());
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
        if (!isValidOrganForUpdate(organ)) {
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

    public void addDonorOrgan(String donorId, String organName, String organType, String bloodGroup) {
        List<Organ> organs = new java.util.ArrayList<>();
        Organ organ = new Organ();
        organ.setOrganId(Utils.UUID());
        organ.setDonorId(donorId);
        organ.setOrganName(organName);
        organ.setOrganType(OrganType.valueOf(organType));
        organ.setBloodGroup(BloodGroup.valueOf(bloodGroup));
        organ.setStatus(OrganStatus.AVAILABLE);
        organ.setCreatedAt(LocalDateTime.now());
        organ.setUpdatedAt(LocalDateTime.now());
        organs.add(organ);
        organRepo.saveMultipleOrgans(organs);
    }

    public void saveMultipleOrgansForRecipient(String recipientId, List<String> organNames, List<String> organTypes, List<String> bloodGroups) {
        List<Organ> organs = new java.util.ArrayList<>();
        for (int i = 0; i < organNames.size(); i++) {
            Organ organ = new Organ();
            organ.setOrganId(Utils.UUID());
            organ.setRecipientId(recipientId);
            organ.setOrganName(organNames.get(i));
            organ.setOrganType(OrganType.valueOf(organTypes.get(i)));
            organ.setBloodGroup(BloodGroup.valueOf(bloodGroups.get(i)));
            organ.setStatus(OrganStatus.MATCHED);
            organ.setCreatedAt(LocalDateTime.now());
            organ.setUpdatedAt(LocalDateTime.now());
            organs.add(organ);
        }
        organRepo.saveMultipleOrgans(organs);
    }

    public void assignOrganToRecipient(String organId, String recipientId) {
        organRepo.assignOrganToRecipient(organId, recipientId);
    }

    // Validation for saving new organ (donorId must be present)
    private boolean isValidOrganForSave(Organ organ) {
        return organ != null
                && organ.getOrganName() != null && !organ.getOrganName().isBlank()
                && organ.getOrganType() != null
                && organ.getBloodGroup() != null
                && organ.getStatus() != null
                && organ.getDonorId() != null && !organ.getDonorId().isBlank();
    }

    // Validation for update (organId must be present)
    private boolean isValidOrganForUpdate(Organ organ) {
        return organ != null
                && organ.getOrganId() != null && !organ.getOrganId().isBlank()
                && organ.getOrganName() != null && !organ.getOrganName().isBlank()
                && organ.getOrganType() != null
                && organ.getBloodGroup() != null
                && organ.getStatus() != null;
    }
}
