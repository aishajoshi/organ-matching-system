package com.pascal.oms.controller;

import com.pascal.oms.endpoint.ParkingTicketManagementService;
import com.pascal.oms.entities.Session;
import com.pascal.oms.service.DonorService;
import com.pascal.oms.service.OrganService;
import com.pascal.oms.service.RecipientService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
public class HomeController {
    private final ParkingTicketManagementService service = ParkingTicketManagementService.getInstance();

    @Autowired
    private DonorService donorService;
    @Autowired
    private RecipientService recipientService;
    @Autowired
    private OrganService organService;

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

    @GetMapping("/api/stats")
    @ResponseBody
    public Map<String, Integer> getStats() {
        Map<String, Integer> stats = new HashMap<>();
        stats.put("totalDonors", donorService.getAllDonors().size());
        stats.put("totalRecipients", recipientService.getAllRecipients().size());
        stats.put("totalOrgans", organService.getAllOrgans().size());
        // Calculate total required organs
        int totalRequiredOrgans = (int) organService.getAllOrgans().stream()
                .filter(o -> o.getStatus() == com.pascal.oms.entities.OrganStatus.REQUIRED)
                .count();
        stats.put("totalRequiredOrgans", totalRequiredOrgans);
        // Calculate total donated organs
        int totalDonatedOrgans = (int) organService.getAllOrgans().stream()
                .filter(o -> o.getStatus() == com.pascal.oms.entities.OrganStatus.AVAILABLE)
                .count();
        stats.put("totalDonatedOrgans", totalDonatedOrgans);
        return stats;
    }
}
