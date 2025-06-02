package com.pascal.oms.examples;

import com.pascal.oms.entities.Donor;
import com.pascal.oms.repo.Datasource;
import com.pascal.oms.repo.DonorRepo;

import java.sql.SQLException;

public class DonerRepoExample {
    public static void main(String[] args) throws SQLException {
        Datasource ds = new Datasource();
        DonorRepo repo = new DonorRepo(ds);
        Donor d = new Donor();
        d.setName("a");
        d.setContact("c");
        d.setBloodType("a");
        d.setStatus("s");
        d.setAge(12);
        d.setDonorID(1);
        repo.saveDonor(d);
    }
}

