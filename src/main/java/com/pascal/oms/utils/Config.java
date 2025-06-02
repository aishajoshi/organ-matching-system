package com.pascal.oms.utils;
/*
 * Created by Ashok Kumar Pant
 * Email: asokpant@gmail.com
 * Created on 02/06/2025.
 */

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Config {
    private final Properties properties = new Properties();

    private Config() {
        try (InputStream input = getClass().getClassLoader().getResourceAsStream("application.properties")) {
            if (input == null) {
                System.out.println("Sorry, unable to find application.properties");
                return;
            }
            properties.load(input);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private static class Holder {
        private static final Config INSTANCE = new Config();
    }

    public static Config getInstance() {
        return Holder.INSTANCE;
    }

    public String get(String key) {
        return properties.getProperty(key);
    }

    public String getOrDefault(String key, String defaultValue) {
        return properties.getProperty(key, defaultValue);
    }
}

