package com.pascal.ptm.repo;

import com.pascal.ptm.entities.User;
import com.pascal.ptm.utils.Datasource;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserRepo {
    private final Datasource datasource;

    public UserRepo(Datasource datasource) {
        this.datasource = datasource;
    }

    public boolean addUser(User user) throws SQLException {
        String sql = "INSERT INTO user (user_id, username, email, password) VALUES (?, ?, ?, ?)";

        try (PreparedStatement statement = this.datasource.getConnection().prepareStatement(sql)) {
            statement.setInt(1, user.getUserId());
            statement.setString(2, user.getUserName());
            statement.setString(3, user.getEmail());
            statement.setString(4, user.getPassword());
            statement.executeUpdate();
            return true;
        }
    }

    public List<User> listUser() throws SQLException {
        String sql = "SELECT * FROM user";
        try (PreparedStatement statement = this.datasource.getConnection().prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            List<User> userList = new ArrayList<>();
            while (resultSet.next()) {
                userList.add(mapToUserEntity(resultSet));
            }
            return userList;
        }
    }

    private User mapToUserEntity(ResultSet resultSet) throws SQLException {
        System.out.println("test" + resultSet.getString("email"));
        User user = new User();
        user.setUserId(resultSet.getInt("user_id"));
        user.setUserName(resultSet.getString("username"));
        user.setEmail(resultSet.getString("email"));
        user.setPassword(resultSet.getString("password"));
        return user;
    }

    public User getUserByUseremail(String email) throws SQLException {
        String sql = "select * from user where email = ?";
        PreparedStatement statement = this.datasource.getConnection().prepareStatement(sql);
        statement.setString(1, email);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            return mapToUserEntity(resultSet);
        } else {
            System.out.println("No user found with email: " + email);
            return null;
        }
    }

    public String getPasswordByEmail(String email) throws SQLException {
        String sql = "select password from user where email = ?";
        PreparedStatement statement = this.datasource.getConnection().prepareStatement(sql);
        statement.setString(1, email);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            return resultSet.getString("password");
        } else {
            System.out.println("No user found with email: " + email);
            return null;
        }
    }

    public User getUserByUserName(String username) throws SQLException {
        String sql = "select * from user where username = ?";
        PreparedStatement statement = this.datasource.getConnection().prepareStatement(sql);
        statement.setString(1, username);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            return mapToUserEntity(resultSet);

        } else {
            System.out.println("No user found with username:" + username);
            return null;
        }

    }
}






