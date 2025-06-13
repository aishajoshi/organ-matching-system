package com.pascal.oms.service;

import com.pascal.oms.entities.Recipient;
import com.pascal.oms.repo.RecipientRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service
public class RecipientService {

    private final RecipientRepo recipientRepo;

    @Autowired
    public RecipientService(RecipientRepo recipientRepo) {
        this.recipientRepo = recipientRepo;
    }

    // Generate a new recipient ID like REC001, REC002, etc.
    private String generateRecipientId() {
        String lastId = recipientRepo.getLastRecipientId();
        if (lastId == null || lastId.isEmpty()) {
            return "REC001";
        }

        int num = Integer.parseInt(lastId.replaceAll("[^0-9]", ""));
        return String.format("REC%03d", num + 1);
    }

    // Register a new recipient
    public Recipient registerRecipient(Recipient recipient) {
        try {
            recipient.setRecipientId(generateRecipientId());
            recipientRepo.saveRecipient(recipient);
            return recipient;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    // Get all recipients
    public List<Recipient> getAllRecipients() {
        return recipientRepo.getAllRecipients();
    }

    // Update recipient
    public boolean updateRecipient(Recipient recipient) {
        try {
            return recipientRepo.updateRecipient(recipient);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
