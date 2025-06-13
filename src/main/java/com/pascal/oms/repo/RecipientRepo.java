package com.pascal.oms.repo;

import com.pascal.oms.entities.Recipient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class RecipientRepo {

    private final Datasource datasource;

    @Autowired
    public RecipientRepo(Datasource datasource) {
        this.datasource = datasource;
    }

    // Save recipient with provided recipientId
    public void saveRecipient(Recipient recipient) throws SQLException {
        String sql = "INSERT INTO recipient (recipient_id, name, age, blood_group, phone, email, status) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = this.datasource.getConnection().prepareStatement(sql)) {
            stmt.setString(1, recipient.getRecipientId());  // Now we set the generated ID manually
            stmt.setString(2, recipient.getName());
            stmt.setInt(3, recipient.getAge());
            stmt.setString(4, recipient.getBloodGroup());
            stmt.setString(5, recipient.getPhone());
            stmt.setString(6, recipient.getEmail());
            stmt.setString(7, recipient.getStatus());

            stmt.executeUpdate();
        }
    }

    // Retrieve all recipients
    public List<Recipient> getAllRecipients() {
        List<Recipient> recipients = new ArrayList<>();
        String sql = "SELECT * FROM recipient";

        try (Connection conn = this.datasource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Recipient recipient = new Recipient();
                recipient.setRecipientId(rs.getString("recipient_id"));
                recipient.setName(rs.getString("name"));
                recipient.setAge(rs.getInt("age"));
                recipient.setBloodGroup(rs.getString("blood_group"));
                recipient.setPhone(rs.getString("phone"));
                recipient.setEmail(rs.getString("email"));
                recipient.setStatus(rs.getString("status"));
                recipients.add(recipient);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return recipients;
    }

    // Update existing recipient
    public boolean updateRecipient(Recipient recipient) throws SQLException {
        String sql = "UPDATE recipient SET name=?, age=?, blood_group=?, phone=?, email=?, status=? WHERE recipient_id=?";
        try (PreparedStatement stmt = this.datasource.getConnection().prepareStatement(sql)) {
            stmt.setString(1, recipient.getName());
            stmt.setInt(2, recipient.getAge());
            stmt.setString(3, recipient.getBloodGroup());
            stmt.setString(4, recipient.getPhone());
            stmt.setString(5, recipient.getEmail());
            stmt.setString(6, recipient.getStatus());
            stmt.setString(7, recipient.getRecipientId());
            return stmt.executeUpdate() > 0;
        }
    }

    // Get the last recipient ID to generate new ID
    public String findLastRecipientId() {
        String sql = "SELECT recipient_id FROM recipient ORDER BY recipient_id DESC LIMIT 1";
        try (Connection conn = datasource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                return rs.getString("recipient_id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // No recipients found
    }
}
