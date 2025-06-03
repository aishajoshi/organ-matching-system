package com.pascal.oms.repo;

import com.pascal.oms.entities.Donor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


@Repository
public class DonorRepo {

    private final Datasource datasource;

    @Autowired
    public DonorRepo(Datasource datasource) {
        this.datasource = datasource;
    }


    public void saveDonor(Donor donor) throws SQLException {
        String sql = "INSERT INTO donor (donor_id, name, age, blood_group, phone, email, status) VALUES (?, ?,?, ?, ?,?, ?)";
        try (PreparedStatement stmt = this.datasource.getConnection().prepareStatement(sql)) {
            stmt.setString(1, donor.getDonorId());
            stmt.setString(2, donor.getName());
            stmt.setInt(3, donor.getAge());
            stmt.setString(4, donor.getBloodGroup());
            stmt.setString(5, donor.getPhone());
            stmt.setString(6, donor.getEmail());
            stmt.setString(7, donor.getStatus());
            stmt.executeUpdate();
        }
    }


    public List<Donor> getAllDonors() {
        List<Donor> donors = new ArrayList<>();
        String sql = "SELECT * FROM donor";

        try (
                Connection conn = this.datasource.getConnection(); // ✅ Get connection properly
                PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()
        ) {
            while (rs.next()) {
                Donor donor = new Donor();
                donor.setDonorId(rs.getString("donor_id"));
                donor.setName(rs.getString("name"));
                donor.setAge(rs.getInt("age"));
                donor.setBloodGroup(rs.getString("blood_group"));
                donor.setContact(rs.getString("phone"));
                donor.setStatus(rs.getString("status"));
                donors.add(donor);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return donors;
    }

    public boolean updateDonor(Donor donor) throws SQLException {
        String sql = "UPDATE donor SET name=?, age=?, blood_group=?, phone=?, email=?, status=? WHERE donor_id=?";
        try (PreparedStatement stmt = this.datasource.getConnection().prepareStatement(sql)) {
            stmt.setString(1, donor.getName());
            stmt.setInt(2, donor.getAge());
            stmt.setString(3, donor.getBloodGroup());
            stmt.setString(4, donor.getPhone());
            stmt.setString(5, donor.getEmail());
            stmt.setString(6, donor.getStatus());
            stmt.setString(7, donor.getDonorId());
            return stmt.executeUpdate() > 0;
        }
    }
}
