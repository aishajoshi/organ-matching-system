package com.pascal.oms.repo;

import com.pascal.oms.entities.Recipient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
        String sql = "INSERT INTO Recipient (recipient_id, name, age, blood_group, phone, email, status) VALUES (?, ?,?, ?, ?,?, ?)";
        try (PreparedStatement stmt = this.datasource.getConnection().prepareStatement(sql)) {
            stmt.setString(1, recipient.getRecipientId());
            stmt.setString(2, recipient.getName());
            stmt.setInt(3, recipient.getAge());
            stmt.setString(4, recipient.getBloodGroup());
            stmt.setString(5, recipient.getPhone());
            stmt.setString(6, recipient.getEmail());
            stmt.setString(7, recipient.getStatus());
            stmt.executeUpdate();
        }
    }


    public List<Recipient> getAllRecipients() {
        List<Recipient> Recipients = new ArrayList<>();
        String sql = "SELECT * FROM Recipient";

        try (
                Connection conn = this.datasource.getConnection(); // ✅ Get connection properly
                PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()
        ) {
            while (rs.next()) {
                Recipient Recipient = new Recipient();
                Recipient.setRecipientId(rs.getString("Recipient_id"));
                Recipient.setName(rs.getString("name"));
                Recipient.setAge(rs.getInt("age"));
                Recipient.setBloodGroup(rs.getString("blood_group"));
                Recipient.setPhone(rs.getString("phone"));
                Recipient.setStatus(rs.getString("status"));
                Recipients.add(Recipient);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Recipients;
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
            stmt.setString(7, recipient.getRecipientId());
            return stmt.executeUpdate() > 0;
        }
    }
}

