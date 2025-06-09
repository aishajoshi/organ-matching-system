package com.pascal.oms.controller;

import com.pascal.oms.entities.Recipient;
import com.pascal.oms.service.RecipientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/recipient")
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
        return "recipient"; // Should be recipient.html
    }

    // Handle form submission
    @PostMapping("/register")
    public String registerRecipient(@ModelAttribute("recipient") Recipient recipient, Model model) {
        Recipient savedRecipient = recipientService.registerRecipient(recipient);
        if (savedRecipient != null) {
            return "redirect:/recipient/list";
        } else {
            model.addAttribute("error", "Failed to register recipient.");
            return "recipient";
        }
    }

    // List all recipients
    @GetMapping("/list")
    public String listRecipients(Model model) {
        List<Recipient> recipients = recipientService.getAllRecipients();
        model.addAttribute("recipients", recipients);
        return "recipient_list"; // Should be recipient_list.html
    }

    // Show edit form
    @GetMapping("/edit/{recipientId}")
    public String showEditForm(@PathVariable Integer recipientId, Model model) {
        Recipient recipient = recipientService.getAllRecipients().stream()
                .filter(r -> recipientId.equals(r.getRecipientId()))
                .findFirst().orElse(null);
        if (recipient == null) {
            return "redirect:/recipient/list";
        }
        model.addAttribute("recipient", recipient);
        model.addAttribute("editMode", true);
        return "recipient"; // Should be recipient.html
    }

    // Handle update
    @PostMapping("/update")
    public String updateRecipient(@ModelAttribute("recipient") Recipient recipient) {
        recipientService.updateRecipient(recipient);
        return "redirect:/recipient/list?updated";
    }
}
