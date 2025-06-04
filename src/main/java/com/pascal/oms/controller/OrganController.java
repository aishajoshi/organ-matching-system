package com.pascal.oms.controller;

import com.pascal.oms.entities.Organ;
import com.pascal.oms.service.OrganService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/organ")
public class OrganController {

    private final OrganService organService;

    @Autowired
    public OrganController(OrganService organService) {
        this.organService = organService;
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
        return "organ_add_donor";
    }

    // Save multiple organs for a recipient
    @PostMapping("/add-donor-organ")
    public String addOrganOfDonor(@RequestParam("donorId") String donerId,
                                  @RequestParam("organName") String organName,
                                  @RequestParam("organType") String organType,
                                  @RequestParam("bloodGroup") String bloodGroup) {
        organService.addDonorOrgan(donerId, organName, organType, bloodGroup);
        return "redirect:/organ/list?success";
    }

    // Show form to add recipient organ requirement
    @GetMapping("/add-recipient-organ")
    public String showAddRecipientOrganForm(Model model) {
        model.addAttribute("organ", new Organ());
        return "organ_add_recipient";
    }


    // Save multiple organs for a recipient
    @PostMapping("/add-recipient-organ")
    public String addOrganForRecipient(@RequestParam String recipientId,
                                       @RequestParam("organNames") List<String> organNames,
                                       @RequestParam("organTypes") List<String> organTypes,
                                       @RequestParam("bloodGroups") List<String> bloodGroups) {
        organService.saveMultipleOrgansForRecipient(recipientId, organNames, organTypes, bloodGroups);
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
}
