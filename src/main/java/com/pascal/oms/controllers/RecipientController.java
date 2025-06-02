package com.pascal.oms.controller;

import com.pascal.oms.entities.Recipient;
import com.pascal.oms.service.RecipientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/recipients")
public class RecipientController {

    private final RecipientService recipientService;

    @Autowired
    public RecipientController(RecipientService recipientService) {
        this.recipientService = recipientService;
    }

    // Show recipient registration form
    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("recipient", new Recipient());
        return "recipient_form"; // Make sure you have recipient_form.html in templates
    }

    // Handle recipient registration form submission
    @PostMapping("/register")
    public String registerRecipient(@ModelAttribute("recipient") Recipient recipient, Model model) {
        Recipient savedRecipient = recipientService.registerRecipient(recipient);
        if (savedRecipient != null) {
            return "redirect:/recipients/list"; // Redirect to recipient list page
        } else {
            model.addAttribute("error", "Failed to register recipient.");
            return "recipient_form";
        }
    }

    // List all registered recipients
    @GetMapping("/list")
    public String listRecipients(Model model) {
        List<Recipient> recipients = recipientService.getAllRecipients();
        model.addAttribute("recipients", recipients);
        return "recipient_list"; // Create recipient_list.html
    }
}
