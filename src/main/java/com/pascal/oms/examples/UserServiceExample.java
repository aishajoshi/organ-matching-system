package com.pascal.oms.examples;

import com.pascal.oms.entities.User;
import com.pascal.oms.repo.UserRepo;
import com.pascal.oms.service.UserService;
import com.pascal.oms.repo.Datasource;

import java.util.List;

public class UserServiceExample {
    public static void main(String[] args) {
//        createTicketExample();
//        listUserExample();
//        getUserExample();
        getUserName();
    }

    public static void createTicketExample() {
        System.out.println("User create example");
        Datasource datasource = new Datasource();
        UserRepo userRepo = new UserRepo(datasource);
        UserService userService = new UserService(userRepo);

        User user=new User();
        user.setFullName("aisha");
        user.setEmail("aisha60@gmail.com");
        user.setPassword("aisha@123");

        User addedUser=userService.addUser(user);


        System.out.println("User created: " + addedUser);
    }

    public static void listUserExample() {
        System.out.println("List User Example");
        Datasource datasource = new Datasource();
        UserRepo userRepo = new UserRepo(datasource);
        UserService userService = new UserService(userRepo);
        List<User> userList = userService.listUser();

        System.out.println("user List:" + userList);
    }
    public static void getUserExample() {
        System.out.println("User get example");
        Datasource datasource = new Datasource();
        UserRepo userRepo = new UserRepo(datasource);
        UserService userService =new UserService(userRepo);
        User user = userService.getUserByEmail("aisha60@gmail.com");
        System.out.println("User:" + user);
    }
    public static void getUserName() {
        System.out.println("User get example");
        Datasource datasource = new Datasource();
        UserRepo userRepo = new UserRepo(datasource);
        UserService userService =new UserService(userRepo);
        User user = userService.getUserByUserName("aisha");
        System.out.println("Username:" + user);
    }

}
