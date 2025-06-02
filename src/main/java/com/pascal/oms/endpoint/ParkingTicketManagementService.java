package com.pascal.oms.endpoint;
/*
 * Created by Ashok Kumar Pant
 * Email: asokpant@gmail.com
 * Created on 22/03/2024.
 */

import com.pascal.oms.repo.DonorRepo;
import com.pascal.oms.repo.UserRepo;
import com.pascal.oms.service.AuthService;
import com.pascal.oms.service.DonorService;
import com.pascal.oms.service.UserService;
import com.pascal.oms.repo.Datasource;


public class ParkingTicketManagementService {
    private static ParkingTicketManagementService instance;
    private final DonorService ticketService;
    private final UserService userService;

    private final AuthService authService;

    private ParkingTicketManagementService() {
        Datasource datasource = new Datasource();
        DonorRepo ticketRepo = new DonorRepo(datasource);
        this.ticketService = new DonorService(ticketRepo);

        UserRepo userRepo = new UserRepo(datasource);
        this.userService = new UserService(userRepo);
        authService = new AuthService(userRepo);
    }

    public static ParkingTicketManagementService getInstance() {
        if (instance == null) {
            instance = new ParkingTicketManagementService();
        }
        return instance;
    }

    public DonorService getTicketService() {
        return ticketService;
    }

    public UserService getUserService() {
        return userService;
    }

    public AuthService getAuthService() {
        return authService;
    }
}
