package com.pascal.oms.repo;
/*
 * Created by Ashok Kumar Pant
 * Email: asokpant@gmail.com
 * Created on 27/02/2024.
 */

import com.pascal.oms.Settings;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;

@Component
public class Datasource {
    public Connection getConnection() {
        try {
            return DriverManager.getConnection(Settings.DATABASE_URL, Settings.DATABASE_USER, Settings.DATABASE_PASSWORD);
        } catch (Exception e) {
            throw new RuntimeException("Failed to get DB connection", e);
        }
    }
}
