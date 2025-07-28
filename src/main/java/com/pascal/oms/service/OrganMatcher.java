package com.pascal.oms.service;

import com.pascal.oms.entities.Organ;
import com.pascal.oms.entities.OrganStatus;
import com.pascal.oms.entities.Recipient;
import com.pascal.oms.repo.OrganRepo;
import com.pascal.oms.repo.RecipientRepo;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class OrganMatcher {

    private final OrganRepo organRepo;

    private final RecipientRepo recipientRepo;

    private static final Map<String, List<String>> compatibleBloodGroups = new HashMap<>() {{
        put("O-", List.of("O-", "O+", "A-", "A+", "B-", "B+", "AB-", "AB+"));
        put("O+", List.of("O+", "A+", "B+", "AB+"));
        put("A-", List.of("A-", "A+", "AB-", "AB+"));
        put("A+", List.of("A+", "AB+"));
        put("B-", List.of("B-", "B+", "AB-", "AB+"));
        put("B+", List.of("B+", "AB+"));
        put("AB-", List.of("AB-", "AB+"));
        put("AB+", List.of("AB+"));
    }};

    public OrganMatcher(OrganRepo organRepo, RecipientRepo recipientRepo) {
        this.organRepo = organRepo;
        this.recipientRepo = recipientRepo;
    }

    public void matchOrgansToRecipients() {
        try {
            List<Organ> availableOrgans = organRepo.findOrgansByStatus(OrganStatus.AVAILABLE);
            List<Recipient> recipients = recipientRepo.getAllRecipients();

            for (Organ organ : availableOrgans) {
                List<Recipient> compatibleRecipients = recipients.stream()
                        .filter(r -> r.getRequiredOrgan() != null && r.getRequiredOrgan().equalsIgnoreCase(organ.getOrganName()))
                        .filter(r -> isBloodCompatible(organ.getBloodGroup().name(), r.getBloodGroup()))
                        .filter(r -> r.getStatus() == null || r.getStatus().equalsIgnoreCase("WAITING"))
                        .sorted(Comparator.comparingInt(Recipient::getUrgencyLevel).reversed())
                        .toList();

                if (!compatibleRecipients.isEmpty()) {
                    Recipient bestMatch = compatibleRecipients.get(0);

                    organ.setRecipientId(bestMatch.getRecipientId());
                    organ.setStatus(OrganStatus.MATCHED);
                    organ.setReceivedDate(LocalDateTime.now());

                    bestMatch.setStatus("MATCHED");

                    organRepo.updateOrgan(organ);
                    recipientRepo.updateRecipient(bestMatch);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean isBloodCompatible(String donorBlood, String recipientBlood) {
        List<String> compatible = compatibleBloodGroups.getOrDefault(donorBlood.toUpperCase(), new ArrayList<>());
        return compatible.contains(recipientBlood.toUpperCase());
    }
}
