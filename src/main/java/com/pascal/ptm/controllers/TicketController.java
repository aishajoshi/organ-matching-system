package com.pascal.ptm.controllers;

import com.pascal.ptm.endpoint.ParkingTicketManagementService;
import com.pascal.ptm.entities.Session;
import com.pascal.ptm.entities.Ticket;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.stream.Collectors;


@Controller
public class TicketController {
    private final ParkingTicketManagementService service = ParkingTicketManagementService.getInstance();


//    @GetMapping("/ticket")
//    public String showLoginForm(Model model, HttpSession session, String error) {
//        Session s = Helper.getAuthSession(session);
//        if (!s.isAuthenticated()) {
//            return "redirect:/home";
//        }
//        List<Ticket> tickets = this.service.getTicketService().listTicket();
//        if (error != null)
//            model.addAttribute("error", error);
//        model.addAttribute("tickets", tickets);
//        return "tickets";
//    }
@GetMapping("/ticket")
public String showTickets(Model model, HttpSession session, String error) {
    Session s = Helper.getAuthSession(session);
    if (!s.isAuthenticated()) {
        return "redirect:/home";
    }
    List<Ticket> tickets = this.service.getTicketService().listTicket();
    if (error != null) {
        model.addAttribute("error", error);
    }
    model.addAttribute("tickets", tickets);
    return "tickets";
}


    @GetMapping("/filter")
    public String filterTicketsByDate(@RequestParam("date") String dateStr, Model model, HttpSession session) {
        Session s = Helper.getAuthSession(session);
        if (!s.isAuthenticated()) {
            return "redirect:/home";
        }

        try {
            LocalDate date = LocalDate.parse(dateStr, DateTimeFormatter.ISO_DATE);
            List<Ticket> filteredTickets = this.service.getTicketService().listTicket().stream()
                    .filter(ticket -> ticket.getEntryTime().toLocalDate().isEqual(date))
                    .collect(Collectors.toList());

            model.addAttribute("filteredTickets", filteredTickets);
            return "filtered_tickets";
        } catch (DateTimeParseException e) {
            model.addAttribute("error", "Invalid date format. Please use yyyy-MM-dd.");
            return "tickets";
        }
    }
    @GetMapping("/ticket/new")
    public String showIssueTicketForm(Model model, HttpSession session) {
        Session s = Helper.getAuthSession(session);
        if (!s.isAuthenticated()) {
            return "redirect:/home";
        }
        model.addAttribute("ticket", new Ticket());
        return "issue_ticket";
    }

    @PostMapping("/ticket/new")
    public String issueTicket(Ticket ticket, BindingResult result, HttpSession session) {
        Session s = Helper.getAuthSession(session);
        if (!s.isAuthenticated()) {
            return "redirect:/home";
        }
        if (result.hasErrors()) {
            return "issue_ticket";
        }
        System.out.println(ticket);

        Ticket response = this.service.getTicketService().createTicket(ticket);
        System.out.println("Ticket response" + response);
        if (response == null) {
            return "issue_ticket";
        }
        System.out.println("Ticket added successfully");
        return "issue_ticket";
    }

    @GetMapping("/ticket/checkout")
    public String showCheckoutTicketForm(Model model, HttpSession session) {

        Session s = Helper.getAuthSession(session);
        if (!s.isAuthenticated()) {
            return "redirect:/home";
        }
        model.addAttribute("ticket", new Ticket());
        return "checkout_ticket";
    }

    @GetMapping("/ticket/checkout/inline")
    public String processCheckoutTicket(String ticketNumber, Model model, HttpSession session) {
        System.out.println("ticket number" + ticketNumber);
        return "redirect:/ticket/checkout?ticketNumber=" + ticketNumber;
    }

    @PostMapping("/ticket/checkout")
    public String checkoutTicket(String ticketNumber, Model model, HttpSession session) {
        Session s = Helper.getAuthSession(session);
        if (!s.isAuthenticated()) {
            return "redirect:/home";
        }
        Ticket response = this.service.getTicketService().checkoutTicket(ticketNumber);
        System.out.println("Ticket response" + response);
        if (response == null) {
            return "checkout_ticket";
        }
        System.out.println("Ticket checkout successfully");
        model.addAttribute("ticket", response);
        return "checkout_ticket";
    }

    }



