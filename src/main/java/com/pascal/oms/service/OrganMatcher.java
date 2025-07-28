package com.pascal.oms.service;

import com.pascal.oms.entities.Organ;
import com.pascal.oms.entities.OrganMatch;
import com.pascal.oms.entities.OrganStatus;
import com.pascal.oms.entities.Recipient;
import com.pascal.oms.repo.OrganMatchRepo;
import com.pascal.oms.repo.OrganRepo;
import com.pascal.oms.repo.RecipientRepo;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class OrganMatcher {

    private final OrganRepo organRepo;

    private final RecipientRepo recipientRepo;

    private final OrganMatchRepo organMatchRepo;

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

    public OrganMatcher(OrganRepo organRepo, RecipientRepo recipientRepo, OrganMatchRepo organMatchRepo) {
        this.organRepo = organRepo;
        this.recipientRepo = recipientRepo;
        this.organMatchRepo = organMatchRepo;
    }

    public void matchOrgansToRecipients() {
        try {
            List<Organ> availableOrgans = organRepo.findOrgansByStatus(OrganStatus.AVAILABLE);
            List<Recipient> recipients = recipientRepo.getAllRecipients();

            for (Organ organ : availableOrgans) {
                List<Recipient> compatibleRecipients = recipients.stream()
                        .filter(r -> r.getOrgans() != null && r.getOrgans().stream()
                                .anyMatch(recOrg -> recOrg.getOrganName().equalsIgnoreCase(organ.getOrganName())))
                        .filter(r -> isBloodCompatible(organ.getBloodGroup(), r.getBloodGroup()))
                        .filter(r -> r.getStatus() == null || r.getStatus().equalsIgnoreCase("REQUIRED"))
                        .sorted(Comparator.comparingInt(Recipient::getUrgencyLevel).reversed())
                        .toList();

                if (!compatibleRecipients.isEmpty()) {
                    Recipient bestMatch = compatibleRecipients.get(0);

                    OrganMatch match = new OrganMatch();
                    match.setMatchId(UUID.randomUUID().toString());
                    match.setOrganId(organ.getOrganId());
                    match.setDonorId(organ.getDonorId());
                    match.setRecipientId(bestMatch.getRecipientId());
                    match.setMatchDate(LocalDateTime.now());
                    match.setStatus("MATCHED"); // keep as matched

                    organMatchRepo.saveOrganMatch(match);
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

    public List<OrganMatch> getAllMatches() {
        return organMatchRepo.getAllOrganMatches();
    }

    public boolean approveMatch(String matchId) {
        try {
            OrganMatch match = organMatchRepo.getOrganMatchById(matchId);
            if (match == null || "APPROVED".equalsIgnoreCase(match.getStatus())) {
                return false;
            }
            // Update match status to APPROVED
            organMatchRepo.updateMatchStatus(matchId, "APPROVED");
            // Update organ status for both donor and recipient to APPROVED
            organRepo.updateOrganStatusForDonorAndRecipient(match.getOrganName(), match.getDonorId(), match.getRecipientId(), OrganStatus.APPROVED);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
