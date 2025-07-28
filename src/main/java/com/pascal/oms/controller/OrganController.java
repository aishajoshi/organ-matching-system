package com.pascal.oms.controller;

import com.pascal.oms.entities.Organ;
import com.pascal.oms.service.DonorService;
import com.pascal.oms.service.OrganMatcher;
import com.pascal.oms.service.OrganService;
import com.pascal.oms.service.RecipientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/organ")
public class OrganController {

    private final OrganService organService;
    private final OrganMatcher organMatchingService;
    private final DonorService donorService;
    private final RecipientService recipientService;

    @Autowired
    public OrganController(OrganService organService, OrganMatcher organMatchingService, DonorService donorService, RecipientService recipientService) {
        this.organService = organService;
        this.organMatchingService = organMatchingService;
        this.donorService = donorService;
        this.recipientService = recipientService;
    }

    // Show registration form
    @GetMapping("/register")
    public String showOrganForm(Model model) {
        model.addAttribute("organ", new Organ());
        model.addAttribute("editMode", false);
        return "organ";  // organ.html
    }

    // Show form to add donor organ
    @GetMapping("/add-donor-organ")
    public String showAddDonorOrganForm(Model model) {
        model.addAttribute("organ", new Organ());
        model.addAttribute("donors", donorService.getAllDonors());
        return "organ_add_donor";
    }

    // Save multiple organs for a recipient
    @PostMapping("/add-donor-organ")
    public String addOrganOfDonor(@RequestParam("donorId") String donorId,
                                  @RequestParam("organName") String organName,
                                  @RequestParam("bloodGroup") String bloodGroup) {
        organService.addDonorOrgan(donorId, organName, bloodGroup);
        return "redirect:/organ/list?success";
    }

    // Show form to add recipient organ requirement
    @GetMapping("/add-recipient-organ")
    public String showAddRecipientOrganForm(Model model) {
        model.addAttribute("organ", new Organ());
        model.addAttribute("recipients", recipientService.getAllRecipients());
        return "organ_add_recipient";
    }


    // Save organ for a recipient (single organ per submission)
    @PostMapping("/add-recipient-organ")
    public String addOrganForRecipient(@RequestParam("recipientId") String recipientId,
                                       @RequestParam("organName") String organName,
                                       @RequestParam("bloodGroup") String bloodGroup) {
        organService.addRecipientOrgan(recipientId, organName,  bloodGroup);
        return "redirect:/organ/list?success";
    }

    // Assign existing organ to recipient
    @PostMapping("/assign")
    public String assignOrganToRecipient(@RequestParam String organId, @RequestParam String recipientId) {
        organService.assignOrganToRecipient(organId, recipientId);
        return "redirect:/organ/list?success";
    }

    // Show organ list
    @GetMapping("/list")
    public String listOrgans(Model model) {
        List<Organ> organList = organService.getAllOrgans();
        model.addAttribute("organs", organList);
        return "organ_list";  // organ-list.html
    }

    // Show edit form
    @GetMapping("/edit/{organId}")
    public String editOrgan(@PathVariable("organId") String organId, Model model) {
        Organ organ = organService.getOrganById(organId);
        if (organ != null) {
            model.addAttribute("organ", organ);
            model.addAttribute("editMode", true);
            return "organ";
        } else {
            return "redirect:/organ/list?notfound";
        }
    }

    // Update organ
    @PostMapping("/update")
    public String updateOrgan(@ModelAttribute("organ") Organ organ) {
        organService.updateOrgan(organ);
        return "redirect:/organ/list?updated";
    }

    // Run organ matching
    @PostMapping("/match")
    public String matchOrgans(Model model) {
        organMatchingService.matchOrgansToRecipients();
        model.addAttribute("message", "Organ matching completed successfully.");
        List<Organ> organList = organService.getAllOrgans();
        model.addAttribute("organs", organList);
        return "organ_list";
    }

    // Approve a matched organ
    @PostMapping("/approve")
    public String approveOrgan(@RequestParam("organId") String organId, Model model) {
        boolean success = organService.approveOrgan(organId);
        if (success) {
            model.addAttribute("message", "Organ approved successfully.");
        } else {
            model.addAttribute("message", "Failed to approve organ. Only matched organs can be approved.");
        }
        List<Organ> organList = organService.getAllOrgans();
        model.addAttribute("organs", organList);
        return "organ_list";
    }

    // Match organs (dashboard button)
    @PostMapping("/organ/match")
    @ResponseBody
    public String matchOrgans() {
        organMatchingService.matchOrgansToRecipients();
        return "Organ matching completed.";
    }
}
