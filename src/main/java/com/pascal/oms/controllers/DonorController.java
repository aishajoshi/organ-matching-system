package com.pascal.oms.controller;

import com.pascal.oms.entities.Donor;
import com.pascal.oms.service.DonorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/donor")
public class DonorController {

    @Autowired
    private final DonorService donorService;

    public DonorController(DonorService donorService) {
        this.donorService = donorService;
    }


    // Show registration form
    @GetMapping("/register")
    public String showDonorForm(Model model) {
        model.addAttribute("donor", new Donor());
        return "donor"; // Make sure your donor.html is under templates/
    }

    // Handle form submission
    @PostMapping("/save")
    public String saveDonor(@ModelAttribute("donor") Donor donor) {
        donorService.registerDonor(donor);
        return "redirect:/donor/register?success"; // Or redirect to a success page
    }
}
