package com.pascal.ptm.service;

import com.pascal.ptm.entities.Ticket;
import com.pascal.ptm.repo.TicketRepo;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class TicketService {
    private final TicketRepo ticketRepo;

    public TicketService(TicketRepo ticketRepo) {
        this.ticketRepo = ticketRepo;
    }

    public Ticket createTicket(Ticket ticket) {
        try {
            if (!isValidTicket(ticket)) {
                System.out.println("Invalid ticket information. Please check your input.");
                return null;
            }

            String ticketNumber = generateTicketNumber();
            ticket.setEntryTime(LocalDateTime.now());
            ticket.setTicketNumber(ticketNumber);

            ticketRepo.createTicket(ticket);

            // TODO: Get saved ticket if needed

            return ticket;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
    public List<Ticket> listTicket() {
        try {
//
//            }
            return ticketRepo.listTicket();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }


    public Ticket getTicketByTicketNumber(String ticketNumber) {
        try {
            if (ticketNumber == null || ticketNumber.isEmpty()) {
                System.out.println("Invalid ticket number.");
                return null;
            }
            return ticketRepo.getTicketByTicketNumber(ticketNumber);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }


    public Ticket checkoutTicket(String ticketNumber) {
        try {
            Ticket ticket = this.getTicketByTicketNumber(ticketNumber);
            if (ticket == null) {
                System.out.println("Ticket not found");
                return null;
            }


            LocalDateTime exitTime = LocalDateTime.now(); // Assuming exit time is the current time
            int ticketId = ticket.getTicketId(); // Assuming you have a method to get ticket ID from Ticket object

            long durationSeconds = Duration.between(ticket.getEntryTime(), exitTime).getSeconds(); // Calculate duration in seconds
            double totalAmount = calculateAmount(durationSeconds);// Calculate total amount
            int updatedBy = 0; // Assuming you have a method to get the user ID who is checking out the ticket

            boolean status = ticketRepo.saveTicketCheckoutDetail(ticketId, exitTime, updatedBy, totalAmount, durationSeconds);
            return this.getTicketByTicketNumber(ticketNumber);
        } catch (SQLException e) {
            e.printStackTrace(); // Handle or log the exception appropriately
            return null; // Return false indicating failure
        }
    }

    public static double calculateAmount(long totalTime) {
        System.out.println("Calculate Total amount example");
        double fiveMinuteRate = 0.0;
        double halfHourRate = 15.0;
        double hourlyRate = 25.0;


        int hrs = (int) (totalTime / 3600);
        System.out.println("Hours: " + hrs);
        double totalCost = hrs * hourlyRate;
        long remainingTimeInSec = totalTime - hrs * 3600L;

        if (remainingTimeInSec <= 300) { // 5 minutes (300 seconds)
            totalCost += fiveMinuteRate;
        } else if (remainingTimeInSec <= 1800) { // 30 minutes (1800 seconds)
            totalCost += halfHourRate;
        } else if (remainingTimeInSec <= 3600) { // 1 hour (3600 seconds)
            totalCost += hourlyRate;
        } else { // More than 1 hour
            System.out.println("Remaining time error: " + remainingTimeInSec);
        }
        return totalCost;
    }
    public static double calculateAmountRecursive(long totalTime) {
        System.out.println("Calculate Total amount example");
        double fiveMinuteRate = 0.0;
        double halfHourRate = 15.0;
        double hourlyRate = 25.0;

        double totalCost = 0.0;

        if (totalTime <= 300) { // 5 minutes (300 seconds)
            totalCost = fiveMinuteRate;
        } else if (totalTime <= 1800) { // 30 minutes (1800 seconds)
            totalCost = halfHourRate;
        } else if (totalTime <= 3600) { // 1 hour (3600 seconds)
            totalCost = hourlyRate;
        } else { // More than 1 hour
            long remainingTime = totalTime - 3600; // Subtract 1 hour
            totalCost = 25 + calculateAmountRecursive(remainingTime);
        }
        return totalCost;
    }


    // Method to calculate total amount based on entry and exit times (you can adjust the logic accordingly)



    private boolean isValidTicket(Ticket ticket) {
        return ticket.getVehicleNumber() != null && !ticket.getVehicleNumber().isEmpty();
    }

    private String generateTicketNumber() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        String datePart = dateFormat.format(new Date());
        Random random = new Random();
        int randomNumber = random.nextInt(10000);
        return datePart + String.format("%04d", randomNumber);
    }
}
