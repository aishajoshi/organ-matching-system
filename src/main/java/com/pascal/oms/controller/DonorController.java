package com.pascal.oms.controller;

import com.pascal.oms.entities.Donor;
import com.pascal.oms.service.DonorService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/donor")
public class DonorController {

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

    // List all donors
    @GetMapping("/list")
    public String listDonors(Model model) {
        model.addAttribute("donors", donorService.getAllDonors());
        return "donor_list";
    }

    // Show edit form
    @GetMapping("/edit/{donorId}")
    public String showEditForm(@PathVariable String donorId, Model model) {
        Donor donor = donorService.getAllDonors().stream()
                .filter(d -> donorId.equals(d.getDonorId()))
                .findFirst().orElse(null);
        if (donor == null) {
            return "redirect:/donor/list";
        }
        model.addAttribute("donor", donor);
        model.addAttribute("editMode", true);
        return "donor";
    }

    // Handle update
    @PostMapping("/update")
    public String updateDonor(@ModelAttribute("donor") Donor donor) {
        donorService.updateDonor(donor);
        return "redirect:/donor/list?updated";
    }
}
