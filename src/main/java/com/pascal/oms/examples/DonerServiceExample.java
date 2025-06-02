package com.pascal.oms.examples;

import com.pascal.oms.entities.Donor;
import com.pascal.oms.repo.Datasource;
import com.pascal.oms.repo.DonorRepo;
import com.pascal.oms.service.DonorService;

import java.sql.SQLException;

public class DonerServiceExample {
    public static void main(String[] args) throws SQLException {
        Datasource ds = new Datasource();
        DonorRepo repo = new DonorRepo(ds);
        DonorService service = new DonorService(repo);
        Donor d = new Donor();
        d.setName("a");
        d.setContact("c");
        d.setBloodGroup("a");
        d.setStatus("s");
        d.setAge(12);
        d.setDonorId("1");

        service.registerDonor(d);
        System.out.println("=========================================");
        var donors = service.getAllDonors();
        System.out.println("Donors: ");
        for (Donor donor : donors) {
            System.out.println(donor);
        }
    }
}

