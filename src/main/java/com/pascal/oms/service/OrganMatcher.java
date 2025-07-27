package com.pascal.oms.service;

import com.pascal.oms.entities.Organ;
import com.pascal.oms.entities.Recipient;
import com.pascal.oms.repo.OrganRepo;
import com.pascal.oms.repo.RecipientRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class OrganMatchingService {

    @Autowired
    private OrganRepo organRepo;

    @Autowired
    private RecipientRepo recipientRepo;

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

    public void matchOrgansToRecipients() {
        List<Organ> availableOrgans = organRepo.findByStatus(OrganStatus.AVAILABLE);
        List<Recipient> recipients = recipientRepo.findAll();

        for (Organ organ : availableOrgans) {
            List<Recipient> compatibleRecipients = recipients.stream()
                    .filter(r -> r.getRequiredOrgan().equalsIgnoreCase(organ.getOrganName()))
                    .filter(r -> isBloodCompatible(organ.getBloodGroup().name(), r.getBloodGroup()))
                    .filter(r -> r.getStatus() == null || r.getStatus().equalsIgnoreCase("WAITING"))
                    .sorted(Comparator.comparing(Recipient::getUrgencyLevel, Comparator.nullsLast(String::compareTo)))
                    .collect(Collectors.toList());

            if (!compatibleRecipients.isEmpty()) {
                Recipient bestMatch = compatibleRecipients.get(0);

                organ.setRecipientId(bestMatch.getRecipientId());
                organ.setStatus(OrganStatus.ASSIGNED);
                organ.setReceivedDate(LocalDateTime.now());

                bestMatch.setStatus("MATCHED");

                organRepo.save(organ);
                recipientRepo.save(bestMatch);
            }
        }
    }

    private boolean isBloodCompatible(String donorBlood, String recipientBlood) {
        List<String> compatible = compatibleBloodGroups.getOrDefault(donorBlood.toUpperCase(), new ArrayList<>());
        return compatible.contains(recipientBlood.toUpperCase());
    }
}
