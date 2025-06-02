package com.pascal.oms.service;

import com.pascal.oms.entities.Recipient;
import com.pascal.oms.repo.RecipientRepo;

import java.sql.SQLException;
import java.util.List;

public class RecipientService {

    private final RecipientRepo recipientRepo;

    public RecipientService(RecipientRepo recipientRepo) {
        this.recipientRepo = recipientRepo;
    }

    public Recipient registerRecipient(Recipient recipient) {
        if (!isValidRecipient(recipient)) {
            System.out.println("Invalid recipient details.");
            return null;
        }

        try {
            recipientRepo.saveRecipient(recipient);
            return recipient;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Recipient> getAllRecipients() {
        return recipientRepo.getAllRecipients();
    }

    public Recipient getRecipientById(int id) {
        return recipientRepo.getRecipientById(id);
    }

    private boolean isValidRecipient(Recipient recipient) {
        return recipient.getName() != null && !recipient.getName().isBlank()
                && recipient.getAge() > 0
                && recipient.getBloodType() != null && !recipient.getBloodType().isBlank()
                && recipient.getRequiredOrgan() != null && !recipient.getRequiredOrgan().isBlank()
                && recipient.getContact() != null && !recipient.getContact().isBlank();
    }
}
