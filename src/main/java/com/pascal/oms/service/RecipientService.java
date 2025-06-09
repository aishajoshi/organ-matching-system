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

    public Recipient registerRecipient(Recipient recipient) {
        if (!isValidRecipient(recipient)) {
            System.out.println("Invalid recipient details.");
            return null;
        }

        recipient.setStatus("Waiting"); // default status
        try {
            recipientRepo.saveRecipient(recipient);
            return recipient;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean updateRecipient(Recipient recipient) {
        if (!isValidRecipient(recipient)) {
            System.out.println("Invalid recipient details.");
            return false;
        }
        try {
            return recipientRepo.updateRecipient(recipient);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Recipient> getAllRecipients() {
        return recipientRepo.getAllRecipients();
    }

    private boolean isValidRecipient(Recipient recipient) {
        return recipient.getName() != null && !recipient.getName().isEmpty()
                && recipient.getAge() > 0
                && recipient.getBloodGroup() != null && !recipient.getBloodGroup().isEmpty()
                && recipient.getPhone() != null && !recipient.getPhone().isEmpty();
    }
}
