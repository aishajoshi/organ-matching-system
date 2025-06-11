package com.pascal.oms.repo;

import com.pascal.oms.entities.BloodGroup;
import com.pascal.oms.entities.Organ;
import com.pascal.oms.entities.OrganStatus;
import com.pascal.oms.entities.OrganType;
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
        String sql = "INSERT INTO organ (organ_id, organ_name, donor_id, recipient_id, type, blood_group, status, created_at, updated_at, donated_date, expiry_date, received_date) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = datasource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, organ.getOrganId());
            stmt.setString(2, organ.getOrganName());
            stmt.setString(3, organ.getDonorId());
            stmt.setString(4, organ.getRecipientId());
            stmt.setString(5, organ.getOrganType() != null ? organ.getOrganType().name() : null);
            stmt.setString(6, organ.getBloodGroup() != null ? organ.getBloodGroup().name() : null);
            stmt.setString(7, organ.getStatus() != null ? organ.getStatus().name() : null);
            stmt.setTimestamp(8, organ.getCreatedAt() != null ? Timestamp.valueOf(organ.getCreatedAt()) : new Timestamp(System.currentTimeMillis()));
            stmt.setTimestamp(9, organ.getUpdatedAt() != null ? Timestamp.valueOf(organ.getUpdatedAt()) : new Timestamp(System.currentTimeMillis()));
            stmt.setTimestamp(10, organ.getDonatedDate() != null ? Timestamp.valueOf(organ.getDonatedDate()) : null);
            stmt.setTimestamp(11, organ.getExpiryDate() != null ? Timestamp.valueOf(organ.getExpiryDate()) : null);
            stmt.setTimestamp(12, organ.getReceivedDate() != null ? Timestamp.valueOf(organ.getReceivedDate()) : null);
            stmt.executeUpdate();
        }
    }

    public void saveMultipleOrgans(List<Organ> organs) {
        String sql = "INSERT INTO organ (organ_id, organ_name, donor_id, recipient_id, type, blood_group, status, donated_date, received_date) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = datasource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            for (Organ organ : organs) {
                stmt.setString(1, organ.getOrganId());
                stmt.setString(2, organ.getOrganName());
                stmt.setString(3, organ.getDonorId());
                stmt.setString(4, organ.getRecipientId());
                stmt.setString(5, organ.getOrganType().name());
                stmt.setString(6, organ.getBloodGroup().name());
                stmt.setString(7, organ.getStatus().name());
                stmt.setTimestamp(8, organ.getDonatedDate() != null ? Timestamp.valueOf(organ.getDonatedDate()) : null);
                stmt.setTimestamp(9, organ.getReceivedDate() != null ? Timestamp.valueOf(organ.getReceivedDate()) : null);
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
                organ.setOrganType(OrganType.valueOf(rs.getString("type")));
                organ.setBloodGroup(BloodGroup.valueOf(rs.getString("blood_group")));
                organ.setStatus(OrganStatus.valueOf(rs.getString("status")));

                Timestamp created = rs.getTimestamp("created_at");
                Timestamp updated = rs.getTimestamp("updated_at");
                Timestamp donated = rs.getTimestamp("donated_date");
                Timestamp expiry = rs.getTimestamp("expiry_date");
                Timestamp received = rs.getTimestamp("received_date");

                organ.setCreatedAt(created != null ? created.toLocalDateTime() : null);
                organ.setUpdatedAt(updated != null ? updated.toLocalDateTime() : null);
                organ.setDonatedDate(donated != null ? donated.toLocalDateTime() : null);
                organ.setExpiryDate(expiry != null ? expiry.toLocalDateTime() : null);
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
                    organ.setOrganType(OrganType.valueOf(rs.getString("type")));
                    organ.setBloodGroup(BloodGroup.valueOf(rs.getString("blood_group")));
                    organ.setStatus(OrganStatus.valueOf(rs.getString("status")));

                    Timestamp created = rs.getTimestamp("created_at");
                    Timestamp updated = rs.getTimestamp("updated_at");
                    Timestamp donated = rs.getTimestamp("donated_date");
                    Timestamp expiry = rs.getTimestamp("expiry_date");
                    Timestamp received = rs.getTimestamp("received_date");

                    organ.setCreatedAt(created != null ? created.toLocalDateTime() : null);
                    organ.setUpdatedAt(updated != null ? updated.toLocalDateTime() : null);
                    organ.setDonatedDate(donated != null ? donated.toLocalDateTime() : null);
                    organ.setExpiryDate(expiry != null ? expiry.toLocalDateTime() : null);
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
        String sql = "UPDATE organ SET organ_name = ?, donor_id = ?, recipient_id = ?, type = ?, blood_group = ?, " +
                "status = ?, updated_at = ?, donated_date = ?, expiry_date = ?, received_date = ? WHERE organ_id = ?";

        try (Connection conn = datasource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, organ.getOrganName());
            stmt.setString(2, organ.getDonorId());
            stmt.setString(3, organ.getRecipientId());
            stmt.setString(4, organ.getOrganType().name());
            stmt.setString(5, organ.getBloodGroup().name());
            stmt.setString(6, organ.getStatus().name());
            stmt.setTimestamp(7, organ.getUpdatedAt() != null ? Timestamp.valueOf(organ.getUpdatedAt()) : new Timestamp(System.currentTimeMillis()));
            stmt.setTimestamp(8, organ.getDonatedDate() != null ? Timestamp.valueOf(organ.getDonatedDate()) : null);
            stmt.setTimestamp(9, organ.getExpiryDate() != null ? Timestamp.valueOf(organ.getExpiryDate()) : null);
            stmt.setTimestamp(10, organ.getReceivedDate() != null ? Timestamp.valueOf(organ.getReceivedDate()) : null);
            stmt.setString(11, organ.getOrganId());

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
}
