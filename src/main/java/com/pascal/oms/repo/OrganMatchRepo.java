package com.pascal.oms.repo;

import com.pascal.oms.entities.OrganMatch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository
public class OrganMatchRepo {
    private final Datasource datasource;

    @Autowired
    public OrganMatchRepo(Datasource datasource) {
        this.datasource = datasource;
    }

    public void saveOrganMatch(OrganMatch match) throws SQLException {
        // Check if match already exists for this organ and recipient
        String checkSql = "SELECT COUNT(*) FROM organ_match WHERE organ_id = ? AND recipient_id = ?";
        try (Connection conn = datasource.getConnection();
             PreparedStatement checkStmt = conn.prepareStatement(checkSql)) {
            checkStmt.setString(1, match.getOrganId());
            checkStmt.setString(2, match.getRecipientId());
            java.sql.ResultSet rs = checkStmt.executeQuery();
            if (rs.next() && rs.getInt(1) > 0) {
                // Match already exists, do not insert
                return;
            }
        }
        // Insert new match if not exists
        String sql = "INSERT INTO organ_match (match_id, organ_id, donor_id, recipient_id, match_date, status) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = datasource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, match.getMatchId() != null ? match.getMatchId() : UUID.randomUUID().toString());
            stmt.setString(2, match.getOrganId());
            stmt.setString(3, match.getDonorId());
            stmt.setString(4, match.getRecipientId());
            stmt.setTimestamp(5, match.getMatchDate() != null ? Timestamp.valueOf(match.getMatchDate()) : new Timestamp(System.currentTimeMillis()));
            stmt.setString(6, match.getStatus() != null ? match.getStatus() : "MATCHED");
            stmt.executeUpdate();
        }
    }

    public List<OrganMatch> getAllOrganMatches() {
        List<OrganMatch> matches = new ArrayList<>();
        String sql = "SELECT m.*, o.organ_name, d.name AS donor_name, r.name AS recipient_name " +
                "FROM organ_match m " +
                "JOIN organ o ON m.organ_id = o.organ_id " +
                "JOIN donor d ON m.donor_id = d.donor_id " +
                "JOIN recipient r ON m.recipient_id = r.recipient_id";
        try (Connection conn = datasource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             java.sql.ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                OrganMatch match = new OrganMatch();
                match.setMatchId(rs.getString("match_id"));
                match.setOrganId(rs.getString("organ_id"));
                match.setDonorId(rs.getString("donor_id"));
                match.setRecipientId(rs.getString("recipient_id"));
                java.sql.Timestamp matchDate = rs.getTimestamp("match_date");
                if (matchDate != null) match.setMatchDate(matchDate.toLocalDateTime());
                match.setStatus(rs.getString("status"));
                match.setOrganName(rs.getString("organ_name"));
                match.setDonorName(rs.getString("donor_name"));
                match.setRecipientName(rs.getString("recipient_name"));
                matches.add(match);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return matches;
    }
}
