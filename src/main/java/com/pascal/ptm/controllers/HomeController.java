package com.pascal.ptm.controllers;

import com.pascal.ptm.endpoint.ParkingTicketManagementService;
import com.pascal.ptm.entities.Session;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpSession;

@Controller
public class HomeController {
    private final ParkingTicketManagementService service = ParkingTicketManagementService.getInstance();


    @GetMapping(value = {"/", "/index", "/home"})
    public String home(Model model, HttpSession session) {
        System.out.println("home");
        Session s = Helper.getAuthSession(session);
        Helper.setAuthSession(model, s);
        return "home";
    }

    @GetMapping(value = {"/profile"})
    public String profile(Model model, HttpSession session) {
        System.out.println("home");
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
}
