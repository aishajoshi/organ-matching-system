package com.pascal.oms.repo;

import com.pascal.oms.entities.Donor;
import com.pascal.oms.repo.Datasource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DonorRepo {

    private final Datasource datasource;

    public DonorRepo(Datasource datasource) {
        this.datasource = datasource;
    }


    public void saveDonor(Donor donor) throws SQLException {
        String sql = "INSERT INTO donor (Name, Age, BloodType, Contact, Status) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = this.datasource.getConnection().prepareStatement(sql)) {
            stmt.setString(1, donor.getName());
            stmt.setInt(2, donor.getAge());
            stmt.setString(3, donor.getBloodType());
            stmt.setString(4, donor.getContact());
            stmt.setString(5, donor.getStatus());
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
                donor.setDonorID(rs.getInt("DonorID"));
                donor.setName(rs.getString("Name"));
                donor.setAge(rs.getInt("Age"));
                donor.setBloodType(rs.getString("BloodType"));
                donor.setContact(rs.getString("Contact"));
                donor.setStatus(rs.getString("Status"));
                donors.add(donor);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return donors;
    }
    public Donor getDonorById(int id) {
        String sql = "SELECT * FROM donor WHERE DonorID = ?";
        try (
                Connection conn = this.datasource.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)
        ) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Donor donor = new Donor();
                    donor.setDonorID(rs.getInt("DonorID"));
                    donor.setName(rs.getString("Name"));
                    donor.setAge(rs.getInt("Age"));
                    donor.setBloodType(rs.getString("BloodType"));
                    donor.setContact(rs.getString("Contact"));
                    donor.setStatus(rs.getString("Status"));
                    return donor;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    //
    public boolean updateDonor(Donor donor) {
        String sql = "UPDATE donor SET Name = ?, Age = ?, BloodType = ?, Contact = ?, Status = ? WHERE DonorID = ?";
        try (
                Connection conn = this.datasource.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)
        ) {
            stmt.setString(1, donor.getName());
            stmt.setInt(2, donor.getAge());
            stmt.setString(3, donor.getBloodType());
            stmt.setString(4, donor.getContact());
            stmt.setString(5, donor.getStatus());
            stmt.setInt(6, donor.getDonorID());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    //
//
    public boolean deleteDonor(int donorId) {
        String sql = "DELETE FROM donor WHERE DonorID = ?";
        try (
                Connection conn = this.datasource.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)
        ) {
            stmt.setInt(1, donorId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    //
    public List<Donor> findDonorsByBloodType(String bloodType) {
        List<Donor> donors = new ArrayList<>();
        String sql = "SELECT * FROM donor WHERE BloodType = ?";

        try (
                Connection conn = this.datasource.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)
        ) {
            stmt.setString(1, bloodType);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Donor donor = new Donor();
                    donor.setDonorID(rs.getInt("DonorID"));
                    donor.setName(rs.getString("Name"));
                    donor.setAge(rs.getInt("Age"));
                    donor.setBloodType(rs.getString("BloodType"));
                    donor.setContact(rs.getString("Contact"));
                    donor.setStatus(rs.getString("Status"));
                    donors.add(donor);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return donors;
    }
}
