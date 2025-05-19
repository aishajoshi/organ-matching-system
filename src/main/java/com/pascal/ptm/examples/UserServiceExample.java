package com.pascal.ptm.examples;

import com.pascal.ptm.entities.Ticket;
import com.pascal.ptm.entities.User;
import com.pascal.ptm.repo.TicketRepo;
import com.pascal.ptm.repo.UserRepo;
import com.pascal.ptm.service.TicketService;
import com.pascal.ptm.service.UserService;
import com.pascal.ptm.utils.Datasource;

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
        user.setUserName("aisha");
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
