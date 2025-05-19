package com.pascal.ptm.controllers;

import com.pascal.ptm.endpoint.ParkingTicketManagementService;
import com.pascal.ptm.entities.Session;
import com.pascal.ptm.entities.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;

@Controller
public class AuthController {
    private final ParkingTicketManagementService service = ParkingTicketManagementService.getInstance();


    @GetMapping("/login")
    public String showLoginForm(Model model, HttpSession session, String error) {
        System.out.println("login get");
        if (error != null)
            model.addAttribute("error", error);
        Helper.setAuthSession(session, new Session());
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String email, @RequestParam String password, HttpSession session) {
        System.out.println("login");
        System.out.println("email: " + email + ", password: " + password);
        Session loginSession = service.getAuthService().login(email, password);
        System.out.println("authResponse");
        System.out.println(loginSession);

        if (loginSession == null) {
            session.setAttribute("error", "Could not login");
            return "login";
        }

        Helper.setAuthSession(session, loginSession);
        return "redirect:/home";
    }


    @GetMapping("/register")
    public String showSignupForm(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String signup(User user, BindingResult result) {
        if (result.hasErrors()) {
            return "register";
        }
        System.out.println("Register");
        System.out.println(user);

        User response = this.service.getUserService().addUser(user);
        if (response == null) {
            return "register";
        }
        System.out.println("User added successfully");
        return "redirect:/login";
    }

    @PostMapping("/logout")
    public String logout(Model model, String error, HttpSession session) {
        Session s = Helper.getAuthSession(session);
        if (s.isAuthenticated()) {
            return "redirect:/home";
        }
        boolean success = service.getAuthService().logout(s.getAuthToken());
        if (success) {
            Helper.setAuthSession(session, new Session());
            return "redirect:/home";
        }
        return "redirect:/home";
    }
}
