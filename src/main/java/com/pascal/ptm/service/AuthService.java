package com.pascal.ptm.service;
/*
 * Created by Ashok Kumar Pant
 * Email: asokpant@gmail.com
 * Created on 22/03/2024.
 */

import com.pascal.ptm.entities.Session;
import com.pascal.ptm.entities.User;
import com.pascal.ptm.repo.UserRepo;
import com.pascal.ptm.utils.Utils;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class AuthService {
    private final UserRepo userRepo;

    private final Map<String, String> loginMap = new HashMap<>(); // email -> token
    private final Map<String, Session> tokenMap = new HashMap<>(); // token -> session

    public AuthService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    public Session login(String email, String password) {
        try {

            if (this.loginMap.containsKey(email)) {
                System.out.println("User already logged in. Please logout first.");
                return this.tokenMap.get(this.loginMap.get(email));
            }

            User user = userRepo.getUserByUseremail(email);
            System.out.println("User: " + user);
            if (user == null) {
                System.out.println("User not found. Please check your email.");
                return null;
            }
            String userPassword = userRepo.getPasswordByEmail(email);
            if (!userPassword.equals(password)) {
                System.out.println("Invalid password. Please check your password.");
                return null;
            }
            String token = generateToken();
            Session session = new Session();
            session.setAuthToken(token);
            session.setUser(user);
            loginMap.put(email, token);
            tokenMap.put(token, session);

            return session;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean logout(String token) {
        Session session = tokenMap.get(token);
        if (session == null) {
            System.out.println("Invalid token. Please check your token.");
            return false;
        }
        loginMap.remove(session.getUser().getEmail());
        tokenMap.remove(token);
        return true;
    }

    public boolean authorize(String token) {
        return tokenMap.containsKey(token);
    }

    private String generateToken() {
        return Utils.UUID();
    }
}
