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

    public void saveRecipient(Recipient recipient) throws SQLException {
        String sql = "INSERT INTO Recipient (name, age, blood_group, phone, email, status) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = this.datasource.getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, recipient.getName());
            stmt.setInt(2, recipient.getAge());
            stmt.setString(3, recipient.getBloodGroup());
            stmt.setString(4, recipient.getPhone());
            stmt.setString(5, recipient.getEmail());
            stmt.setString(6, recipient.getStatus());

            stmt.executeUpdate();

            // Get generated ID
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    recipient.setRecipientId(rs.getInt(1));
                }
            }
        }
    }

    public List<Recipient> getAllRecipients() {
        List<Recipient> recipients = new ArrayList<>();
        String sql = "SELECT * FROM Recipient";

        try (Connection conn = this.datasource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Recipient recipient = new Recipient();
                recipient.setRecipientId(rs.getInt("recipient_id"));
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

    public boolean updateRecipient(Recipient recipient) throws SQLException {
        String sql = "UPDATE Recipient SET name=?, age=?, blood_group=?, phone=?, email=?, status=? WHERE recipient_id=?";
        try (PreparedStatement stmt = this.datasource.getConnection().prepareStatement(sql)) {
            stmt.setString(1, recipient.getName());
            stmt.setInt(2, recipient.getAge());
            stmt.setString(3, recipient.getBloodGroup());
            stmt.setString(4, recipient.getPhone());
            stmt.setString(5, recipient.getEmail());
            stmt.setString(6, recipient.getStatus());
            stmt.setInt(7, recipient.getRecipientId());
            return stmt.executeUpdate() > 0;
        }
    }
}
