package com.pascal.oms.repo;

import com.pascal.oms.entities.Recipient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class RecipientRepo {

    private final DataSource dataSource;

    @Autowired
    public RecipientRepo(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    // Save a recipient
    public void saveRecipient(Recipient recipient) throws SQLException {
        String sql = "INSERT INTO recipient (recipient_id, name, age, blood_group, phone, email, status, urgency_level, meld_score, waiting_time) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, recipient.getRecipientId());
            stmt.setString(2, recipient.getName());
            stmt.setInt(3, recipient.getAge());
            stmt.setString(4, recipient.getBloodGroup());
            stmt.setString(5, recipient.getPhone());
            stmt.setString(6, recipient.getEmail());
            stmt.setString(7, recipient.getStatus());
            stmt.setInt(8, recipient.getUrgencyLevel());
            stmt.setFloat(9, recipient.getMeldScore());
            stmt.setInt(10, recipient.getWaitingTime());

            stmt.executeUpdate();
        }
    }

    // Fetch all recipients
    public List<Recipient> getAllRecipients() {
        List<Recipient> recipients = new ArrayList<>();
        String sql = "SELECT * FROM recipient";

        try (Connection conn = dataSource.getConnection();
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
                recipient.setUrgencyLevel(rs.getInt("urgency_level"));
                recipient.setMeldScore(rs.getFloat("meld_score"));
                recipient.setWaitingTime(rs.getInt("waiting_time"));
                recipients.add(recipient);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return recipients;
    }

    // Update recipient
    public boolean updateRecipient(Recipient recipient) throws SQLException {
        String sql = "UPDATE recipient SET name=?, age=?, blood_group=?, phone=?, email=?, status=?, urgency_level=?, meld_score=?, waiting_time=? " +
                "WHERE recipient_id=?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, recipient.getName());
            stmt.setInt(2, recipient.getAge());
            stmt.setString(3, recipient.getBloodGroup());
            stmt.setString(4, recipient.getPhone());
            stmt.setString(5, recipient.getEmail());
            stmt.setString(6, recipient.getStatus());
            stmt.setInt(7, recipient.getUrgencyLevel());
            stmt.setFloat(8, recipient.getMeldScore());
            stmt.setInt(9, recipient.getWaitingTime());
            stmt.setString(10, recipient.getRecipientId());

            return stmt.executeUpdate() > 0;
        }
    }

    // Fetch last recipient ID (e.g., REC005)
    public String getLastRecipientId() {
        String sql = "SELECT recipient_id FROM recipient ORDER BY recipient_id DESC LIMIT 1";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) {
                return rs.getString("recipient_id");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
}
