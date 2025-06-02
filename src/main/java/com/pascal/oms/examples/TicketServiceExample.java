//package com.pascal.oms.examples;
///*
// * Created by Ashok Kumar Pant
// * Email: asokpant@gmail.com
// * Created on 27/02/2024.
// */
//
//import com.pascal.oms.entities.Donor;
//import com.pascal.oms.repo.DonorRepo;
//import com.pascal.oms.service.DonorService;
//import com.pascal.oms.repo.Datasource;
//
//import java.util.List;
//
//public class TicketServiceExample {
//    public static void main(String[] args) {
////        createTicketExample();
////        getTicketExample();
////        ticketCheckoutExample();
//        listTicketExample();
//    }
//
//    public static void createTicketExample() {
//        System.out.println("Ticket create example");
//        Datasource datasource = new Datasource();
//        DonorRepo ticketRepo = new DonorRepo(datasource);
//        DonorService ticketService = new DonorService(ticketRepo);
//
//        Donor ticket = new Donor();
//        ticket.setVehicleNumber("BA 1 PA 1234");
//
//        ticket = ticketService.createTicket(ticket);
//        System.out.println("Ticket created: " + ticket);
//    }
//
//    public static void getTicketExample() {
//        System.out.println("Ticket get example");
//        Datasource datasource = new Datasource();
//        DonorRepo ticketRepo = new DonorRepo(datasource);
//        DonorService ticketService = new DonorService(ticketRepo);
//        Donor ticket = ticketService.getTicketByTicketNumber("202402282827");
//        System.out.println("Ticket: " + ticket);
//    }
//
//
//    public static void ticketCheckoutExample() {
//        System.out.println("Ticket checkout example");
//        Datasource datasource = new Datasource();
//        DonorRepo ticketRepo = new DonorRepo(datasource);
//        DonorService ticketService = new DonorService(ticketRepo);
//        Donor ticket = ticketService.checkoutTicket("202402276394");
//        System.out.println("Ticket: " + ticket);
//    }
//
//
//    public static void listTicketExample() {
//        System.out.println("List Ticket Example");
//
//        Datasource datasource = new Datasource();
//        DonorRepo ticketRepo = new DonorRepo(datasource);
//        DonorService ticketService = new DonorService(ticketRepo);
//        List<Donor> ticketList = ticketService.listTicket();
//
//        System.out.println("ticket List: " + ticketList);
//
//    }
//}