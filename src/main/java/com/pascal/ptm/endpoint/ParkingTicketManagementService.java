package com.pascal.ptm.endpoint;
/*
 * Created by Ashok Kumar Pant
 * Email: asokpant@gmail.com
 * Created on 22/03/2024.
 */

import com.pascal.ptm.repo.TicketRepo;
import com.pascal.ptm.repo.UserRepo;
import com.pascal.ptm.service.AuthService;
import com.pascal.ptm.service.TicketService;
import com.pascal.ptm.service.UserService;
import com.pascal.ptm.utils.Datasource;


public class ParkingTicketManagementService {
    private static ParkingTicketManagementService instance;
    private final TicketService ticketService;
    private final UserService userService;

    private final AuthService authService;

    private ParkingTicketManagementService() {
        Datasource datasource = new Datasource();
        TicketRepo ticketRepo = new TicketRepo(datasource);
        this.ticketService = new TicketService(ticketRepo);

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

    public TicketService getTicketService() {
        return ticketService;
    }

    public UserService getUserService() {
        return userService;
    }

    public AuthService getAuthService() {
        return authService;
    }
}
