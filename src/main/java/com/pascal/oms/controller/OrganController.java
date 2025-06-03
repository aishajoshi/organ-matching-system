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

    // Save a new organ
    @PostMapping("/save")
    public String saveOrgan(@ModelAttribute("organ") Organ organ) {
        organService.saveOrgan(organ);
        return "redirect:/organ/list?success";
    }

    // Show organ list
    @GetMapping("/list")
    public String listOrgans(Model model) {
        List<Organ> organList = organService.getAllOrgans();
        model.addAttribute("organs", organList);
        return "organ-list";  // organ-list.html
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
