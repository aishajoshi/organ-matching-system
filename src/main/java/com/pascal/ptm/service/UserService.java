package com.pascal.ptm.service;

import com.pascal.ptm.entities.User;
import com.pascal.ptm.repo.UserRepo;

import java.sql.SQLException;
import java.util.List;

public class UserService {
    private final UserRepo userRepo;

    public UserService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    public User addUser(User user) {
        try {
            if (!isValidUser(user)) {
                System.out.println("Invalid user information. Please check your input.");
                return null;
            }
            userRepo.addUser(user);
            return user;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<User> listUser() {
        try {
            return userRepo.listUser();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;

        }
    }

    public User getUserByEmail(String email) {
        try {
            if (email == null || email.isEmpty()) {
                System.out.println("Invalid email");
                return null;
            }
            return userRepo.getUserByUseremail(email);

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public User getUserByUserName(String username) {
        try {
            if (username == null || username.isEmpty()) {
                System.out.println("Invalid username");
                return null;
            }
            return userRepo.getUserByUserName(username);

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }


    private boolean isValidUser(User user) {
        return user.getUserName() != null && user.getEmail() != null;
    }
}
