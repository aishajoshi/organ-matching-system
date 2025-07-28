package com.pascal.oms.repo;

import com.pascal.oms.entities.Organ;
import com.pascal.oms.entities.OrganStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class OrganRepo {

    private final Datasource datasource;

    @Autowired
    public OrganRepo(Datasource datasource) {
        this.datasource = datasource;
    }

    public void saveOrgan(Organ organ) throws SQLException {
        String sql = "INSERT INTO organ (organ_id, organ_name, donor_id, recipient_id, blood_group, status, created_at, updated_at, donated_date,  received_date) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?,  ?, ?)";

        try (Connection conn = datasource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, organ.getOrganId());
            stmt.setString(2, organ.getOrganName());
            stmt.setString(3, organ.getDonorId());
            stmt.setString(4, organ.getRecipientId());
            stmt.setString(5, organ.getBloodGroup());
            stmt.setString(6, organ.getStatus() != null ? organ.getStatus().name() : null);
            stmt.setTimestamp(7, organ.getCreatedAt() != null ? Timestamp.valueOf(organ.getCreatedAt()) : new Timestamp(System.currentTimeMillis()));
            stmt.setTimestamp(8, organ.getUpdatedAt() != null ? Timestamp.valueOf(organ.getUpdatedAt()) : new Timestamp(System.currentTimeMillis()));
            stmt.setTimestamp(9, organ.getDonatedDate() != null ? Timestamp.valueOf(organ.getDonatedDate()) : null);
            stmt.setTimestamp(10, organ.getReceivedDate() != null ? Timestamp.valueOf(organ.getReceivedDate()) : null);
            stmt.executeUpdate();
        }
    }

    public void saveMultipleOrgans(List<Organ> organs) {
        String sql = "INSERT INTO organ (organ_id, organ_name, donor_id, recipient_id, blood_group, status, donated_date, received_date) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?,  ?)";

        try (Connection conn = datasource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            for (Organ organ : organs) {
                stmt.setString(1, organ.getOrganId());
                stmt.setString(2, organ.getOrganName());
                stmt.setString(3, organ.getDonorId());
                stmt.setString(4, organ.getRecipientId());
                stmt.setString(5, organ.getBloodGroup());
                stmt.setString(6, organ.getStatus().name());
                stmt.setTimestamp(7, organ.getDonatedDate() != null ? Timestamp.valueOf(organ.getDonatedDate()) : null);
                stmt.setTimestamp(8, organ.getReceivedDate() != null ? Timestamp.valueOf(organ.getReceivedDate()) : null);
                stmt.addBatch();
            }

            stmt.executeBatch();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Organ> getAllOrgans() {
        List<Organ> organs = new ArrayList<>();
        String sql = "SELECT * FROM organ";

        try (Connection conn = datasource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Organ organ = new Organ();
                organ.setOrganId(rs.getString("organ_id"));
                organ.setOrganName(rs.getString("organ_name"));
                organ.setDonorId(rs.getString("donor_id"));
                organ.setRecipientId(rs.getString("recipient_id"));
                organ.setBloodGroup(rs.getString("blood_group"));
                organ.setStatus(OrganStatus.valueOf(rs.getString("status")));

                Timestamp created = rs.getTimestamp("created_at");
                Timestamp updated = rs.getTimestamp("updated_at");
                Timestamp donated = rs.getTimestamp("donated_date");
                Timestamp received = rs.getTimestamp("received_date");

                organ.setCreatedAt(created != null ? created.toLocalDateTime() : null);
                organ.setUpdatedAt(updated != null ? updated.toLocalDateTime() : null);
                organ.setDonatedDate(donated != null ? donated.toLocalDateTime() : null);
                organ.setReceivedDate(received != null ? received.toLocalDateTime() : null);

                organs.add(organ);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return organs;
    }

    public Organ getOrganById(String organId) {
        String sql = "SELECT * FROM organ WHERE organ_id = ?";

        try (Connection conn = datasource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, organId);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Organ organ = new Organ();
                    organ.setOrganId(rs.getString("organ_id"));
                    organ.setOrganName(rs.getString("organ_name"));
                    organ.setDonorId(rs.getString("donor_id"));
                    organ.setRecipientId(rs.getString("recipient_id"));
                    organ.setBloodGroup(rs.getString("blood_group"));
                    organ.setStatus(OrganStatus.valueOf(rs.getString("status")));

                    Timestamp created = rs.getTimestamp("created_at");
                    Timestamp updated = rs.getTimestamp("updated_at");
                    Timestamp donated = rs.getTimestamp("donated_date");
                    Timestamp received = rs.getTimestamp("received_date");

                    organ.setCreatedAt(created != null ? created.toLocalDateTime() : null);
                    organ.setUpdatedAt(updated != null ? updated.toLocalDateTime() : null);
                    organ.setDonatedDate(donated != null ? donated.toLocalDateTime() : null);
                    organ.setReceivedDate(received != null ? received.toLocalDateTime() : null);

                    return organ;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public boolean updateOrgan(Organ organ) throws SQLException {
        String sql = "UPDATE organ SET organ_name = ?, donor_id = ?, recipient_id = ?, blood_group = ?, " +
                "status = ?, updated_at = ?, donated_date = ?, received_date = ? WHERE organ_id = ?";
        try (Connection conn = datasource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, organ.getOrganName());
            stmt.setString(2, organ.getDonorId());
            stmt.setString(3, organ.getRecipientId());
            stmt.setString(4, organ.getBloodGroup());
            stmt.setString(5, organ.getStatus().name());
            stmt.setTimestamp(6, organ.getUpdatedAt() != null ? Timestamp.valueOf(organ.getUpdatedAt()) : new Timestamp(System.currentTimeMillis()));
            stmt.setTimestamp(7, organ.getDonatedDate() != null ? Timestamp.valueOf(organ.getDonatedDate()) : null);
            stmt.setTimestamp(8, organ.getReceivedDate() != null ? Timestamp.valueOf(organ.getReceivedDate()) : null);
            stmt.setString(9, organ.getOrganId());
            return stmt.executeUpdate() > 0;
        }
    }

    public void assignOrganToRecipient(String organId, String recipientId) {
        String sql = "UPDATE organ SET recipient_id = ?, status = ? WHERE organ_id = ?";

        try (Connection conn = datasource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, recipientId);
            stmt.setString(2, OrganStatus.MATCHED.name());
            stmt.setString(3, organId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Find organs by status
    public List<Organ> findOrgansByStatus(OrganStatus status) throws SQLException {
        List<Organ> organs = new ArrayList<>();
        String sql = "SELECT * FROM organ WHERE status = ?";
        try (Connection conn = datasource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, status.name());
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Organ organ = new Organ();
                organ.setOrganId(rs.getString("organ_id"));
                organ.setOrganName(rs.getString("organ_name"));
                organ.setBloodGroup(rs.getString("blood_group"));
                organ.setDonorId(rs.getString("donor_id"));
                organ.setRecipientId(rs.getString("recipient_id"));
                // Set other fields as needed
                organs.add(organ);
            }
        }
        return organs;
    }

    // Fetch all organs for a given recipient
    public List<Organ> getOrgansByRecipientId(String recipientId) {
        List<Organ> organs = new ArrayList<>();
        String sql = "SELECT * FROM organ WHERE recipient_id = ?";
        try (Connection conn = datasource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, recipientId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Organ organ = new Organ();
                    organ.setOrganId(rs.getString("organ_id"));
                    organ.setOrganName(rs.getString("organ_name"));
                    organ.setDonorId(rs.getString("donor_id"));
                    organ.setRecipientId(rs.getString("recipient_id"));
                    organ.setBloodGroup(rs.getString("blood_group"));
                    organ.setStatus(rs.getString("status") != null ? OrganStatus.valueOf(rs.getString("status")) : null);
                    // Set other fields as needed
                    organs.add(organ);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return organs;
    }

    public void updateOrganStatus(String organId, OrganStatus status) throws SQLException {
        String sql = "UPDATE organ SET status = ? WHERE organ_id = ?";
        try (Connection conn = datasource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, status.name());
            stmt.setString(2, organId);
            stmt.executeUpdate();
        }
    }

    public void updateOrganStatusForDonorAndRecipient(String organName, String donorId, String recipientId, OrganStatus status) throws SQLException {
        String sql = "UPDATE organ SET status = ? WHERE organ_name = ? AND (donor_id = ? OR recipient_id = ?)";
        try (Connection conn = datasource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, status.name());
            stmt.setString(2, organName);
            stmt.setString(3, donorId);
            stmt.setString(4, recipientId);
            stmt.executeUpdate();
        }
    }
}
