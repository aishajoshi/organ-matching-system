package com.pascal.oms;
/*
 * Created by Ashok Kumar Pant
 * Email: asokpant@gmail.com
 * Created on 27/02/2024.
 */

import com.pascal.oms.utils.Config;

public class Settings {
    public static final String DATABASE_URL = Config.getInstance().getOrDefault("db.mysql.url", "jdbc:mysql://localhost:3306/oms?useSSL=false");
    public static final String DATABASE_USER = Config.getInstance().getOrDefault("db.mysql.user", "root");
    public static final String DATABASE_PASSWORD = Config.getInstance().getOrDefault("db.mysql.password", "");
}
