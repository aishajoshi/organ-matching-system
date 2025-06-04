package com.pascal.oms.controller;

import com.pascal.oms.endpoint.ParkingTicketManagementService;
import com.pascal.oms.entities.Session;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpSession;

@Controller
public class HomeController {
    private final ParkingTicketManagementService service = ParkingTicketManagementService.getInstance();


    @GetMapping(value = {"/", "/index", "/home", "/dashboard"})
    public String home(Model model, HttpSession session) {
        Session s = Helper.getAuthSession(session);
        Helper.setAuthSession(model, s);
        return "dashboard";
    }

    @GetMapping(value = {"/profile"})
    public String profile(Model model, HttpSession session) {
        Session s = Helper.getAuthSession(session);
        if (s.isAuthenticated()) {
            return "redirect:/login";
        }
        model.addAttribute("user", s.getUser());
        return "profile";
    }

    @GetMapping("/about")
    public String about() {
        return "about";
    }

    @GetMapping("/donor")
    public String donor() {
        return "donor";
    }
}
