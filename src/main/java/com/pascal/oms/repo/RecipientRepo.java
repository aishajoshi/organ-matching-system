package com.pascal.oms.repo;

import com.pascal.oms.entities.Recipient;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RecipientRepo {

    private final Datasource datasource;

    public RecipientRepo(Datasource datasource) {
        this.datasource = datasource;
    }

    public void saveRecipient(Recipient recipient) throws SQLException {
        String sql = "INSERT INTO recipients (Name, Age, BloodType, RequiredOrgan, Contact, UrgencyLevel) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = datasource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, recipient.getName());
            stmt.setInt(2, recipient.getAge());
            stmt.setString(3, recipient.getBloodType());
            stmt.setString(4, recipient.getRequiredOrgan());
            stmt.setString(5, recipient.getContact());
            stmt.setString(6, recipient.getUrgencyLevel());

            stmt.executeUpdate();
        }
    }

    public List<Recipient> getAllRecipients() {
        List<Recipient> recipients = new ArrayList<>();
        String sql = "SELECT * FROM recipients";

        try (Connection conn = datasource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Recipient recipient = new Recipient();
                recipient.setRecipientID(rs.getInt("RecipientID"));
                recipient.setName(rs.getString("Name"));
                recipient.setAge(rs.getInt("Age"));
                recipient.setBloodType(rs.getString("BloodType"));
                recipient.setRequiredOrgan(rs.getString("RequiredOrgan"));
                recipient.setContact(rs.getString("Contact"));
                recipient.setUrgencyLevel(rs.getString("UrgencyLevel"));
                // Additional organ-specific fields can be added here
                recipients.add(recipient);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return recipients;
    }

    // Optional: Get recipient by ID
    public Recipient getRecipientById(int id) {
        String sql = "SELECT * FROM recipients WHERE RecipientID = ?";
        try (Connection conn = datasource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Recipient recipient = new Recipient();
                    recipient.setRecipientID(rs.getInt("RecipientID"));
                    recipient.setName(rs.getString("Name"));
                    recipient.setAge(rs.getInt("Age"));
                    recipient.setBloodType(rs.getString("BloodType"));
                    recipient.setRequiredOrgan(rs.getString("RequiredOrgan"));
                    recipient.setContact(rs.getString("Contact"));
                    recipient.setUrgencyLevel(rs.getString("UrgencyLevel"));
                    return recipient;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}

