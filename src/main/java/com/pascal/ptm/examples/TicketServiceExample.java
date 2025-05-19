package com.pascal.ptm.examples;
/*
 * Created by Ashok Kumar Pant
 * Email: asokpant@gmail.com
 * Created on 27/02/2024.
 */

import com.pascal.ptm.entities.Ticket;
import com.pascal.ptm.repo.TicketRepo;
import com.pascal.ptm.service.TicketService;
import com.pascal.ptm.utils.Datasource;

import java.util.List;

public class TicketServiceExample {
    public static void main(String[] args) {
//        createTicketExample();
//        getTicketExample();
//        ticketCheckoutExample();
        listTicketExample();
    }

    public static void createTicketExample() {
        System.out.println("Ticket create example");
        Datasource datasource = new Datasource();
        TicketRepo ticketRepo = new TicketRepo(datasource);
        TicketService ticketService = new TicketService(ticketRepo);

        Ticket ticket = new Ticket();
        ticket.setVehicleNumber("BA 1 PA 1234");

        ticket = ticketService.createTicket(ticket);
        System.out.println("Ticket created: " + ticket);
    }

    public static void getTicketExample() {
        System.out.println("Ticket get example");
        Datasource datasource = new Datasource();
        TicketRepo ticketRepo = new TicketRepo(datasource);
        TicketService ticketService = new TicketService(ticketRepo);
        Ticket ticket = ticketService.getTicketByTicketNumber("202402282827");
        System.out.println("Ticket: " + ticket);
    }


    public static void ticketCheckoutExample() {
        System.out.println("Ticket checkout example");
        Datasource datasource = new Datasource();
        TicketRepo ticketRepo = new TicketRepo(datasource);
        TicketService ticketService = new TicketService(ticketRepo);
        Ticket ticket = ticketService.checkoutTicket("202402276394");
        System.out.println("Ticket: " + ticket);
    }


    public static void listTicketExample() {
        System.out.println("List Ticket Example");

        Datasource datasource = new Datasource();
        TicketRepo ticketRepo = new TicketRepo(datasource);
        TicketService ticketService = new TicketService(ticketRepo);
        List<Ticket> ticketList = ticketService.listTicket();

        System.out.println("ticket List: " + ticketList);

    }
}