package com.pascal.oms.repo;

import com.pascal.oms.entities.Organ;
import com.pascal.oms.entities.OrganStatus;
import com.pascal.oms.entities.OrganType;
import com.pascal.oms.entities.BloodGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.time.LocalDateTime;
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
        String sql = "INSERT INTO organ (organ_id, organ_name, donor_id, recipient_id, organ_type, blood_group, " +
                "status, created_at, updated_at, donated_date, expiry_date, received_date) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = datasource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, organ.getOrganId());
            stmt.setString(2, organ.getOrganName());
            stmt.setString(3, organ.getDonorId());
            stmt.setString(4, organ.getRecipientId());
            stmt.setString(5, organ.getOrganType().name());
            stmt.setString(6, organ.getBloodGroup().name());
            stmt.setString(7, organ.getStatus().name());
            stmt.setTimestamp(8, Timestamp.valueOf(organ.getCreatedAt()));
            stmt.setTimestamp(9, Timestamp.valueOf(organ.getUpdatedAt()));
            stmt.setTimestamp(10, Timestamp.valueOf(organ.getDonatedDate()));
            stmt.setTimestamp(11, Timestamp.valueOf(organ.getExpiryDate()));
            stmt.setTimestamp(12, Timestamp.valueOf(organ.getReceivedDate()));

            stmt.executeUpdate();
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
                organ.setOrganType(OrganType.valueOf(rs.getString("organ_type")));
                organ.setBloodGroup(BloodGroup.valueOf(rs.getString("blood_group")));
                organ.setStatus(OrganStatus.valueOf(rs.getString("status")));
                organ.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
                organ.setUpdatedAt(rs.getTimestamp("updated_at").toLocalDateTime());
                organ.setDonatedDate(rs.getTimestamp("donated_date").toLocalDateTime());
                organ.setExpiryDate(rs.getTimestamp("expiry_date").toLocalDateTime());
                organ.setReceivedDate(rs.getTimestamp("received_date").toLocalDateTime());

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
                    organ.setOrganType(OrganType.valueOf(rs.getString("organ_type")));
                    organ.setBloodGroup(BloodGroup.valueOf(rs.getString("blood_group")));
                    organ.setStatus(OrganStatus.valueOf(rs.getString("status")));
                    organ.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
                    organ.setUpdatedAt(rs.getTimestamp("updated_at").toLocalDateTime());
                    organ.setDonatedDate(rs.getTimestamp("donated_date").toLocalDateTime());
                    organ.setExpiryDate(rs.getTimestamp("expiry_date").toLocalDateTime());
                    organ.setReceivedDate(rs.getTimestamp("received_date").toLocalDateTime());

                    return organ;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public boolean updateOrgan(Organ organ) throws SQLException {
        String sql = "UPDATE organ SET organ_name = ?, donor_id = ?, recipient_id = ?, organ_type = ?, " +
                "blood_group = ?, status = ?, updated_at = ?, donated_date = ?, expiry_date = ?, received_date = ? " +
                "WHERE organ_id = ?";

        try (Connection conn = datasource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, organ.getOrganName());
            stmt.setString(2, organ.getDonorId());
            stmt.setString(3, organ.getRecipientId());
            stmt.setString(4, organ.getOrganType().name());
            stmt.setString(5, organ.getBloodGroup().name());
            stmt.setString(6, organ.getStatus().name());
            stmt.setTimestamp(7, Timestamp.valueOf(organ.getUpdatedAt()));
            stmt.setTimestamp(8, Timestamp.valueOf(organ.getDonatedDate()));
            stmt.setTimestamp(9, Timestamp.valueOf(organ.getExpiryDate()));
            stmt.setTimestamp(10, Timestamp.valueOf(organ.getReceivedDate()));
            stmt.setString(11, organ.getOrganId());

            return stmt.executeUpdate() > 0;
        }
    }
}
