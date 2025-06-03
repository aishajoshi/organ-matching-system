package com.pascal.oms.controller;

import com.pascal.oms.entities.Recipient;
import com.pascal.oms.service.RecipientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/recipient")
public class RecipientController {

    private final RecipientService recipientService;

    @Autowired
    public RecipientController(RecipientService recipentService) {
        this.recipientService = recipentService;
    }

    // Show recipient registration form
    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("recipient", new Recipient());
        return "recipient"; // Make sure you have recipient_form.html in templates
    }

    // Handle recipient registration form submission
    @PostMapping("/register")
    public String registerRecipient(@ModelAttribute("recipient") Recipient recipient, Model model) {
        Recipient savedRecipient = recipientService.registerRecipient(recipient);
        if (savedRecipient != null) {
            return "redirect:/recipient/list"; // Redirect to recipient list page
        } else {
            model.addAttribute("error", "Failed to register recipient.");
            return "recipient";
        }
    }

    // List all registered recipients
    @GetMapping("/list")
    public String listRecipients(Model model) {
        List<Recipient> recipients = recipientService.getAllRecipients();
        model.addAttribute("recipients", recipients);
        return "recipient_list"; // Create recipient_list.html
    }

    // Show edit form
    @GetMapping("/edit/{recipientId}")
    public String showEditForm(@org.springframework.web.bind.annotation.PathVariable String recipientId, Model model) {
        Recipient recipient = recipientService.getAllRecipients().stream()
                .filter(r -> recipientId.equals(r.getRecipientId()))
                .findFirst().orElse(null);
        if (recipient == null) {
            return "redirect:/recipient/list";
        }
        model.addAttribute("recipient", recipient);
        model.addAttribute("editMode", true);
        return "recipient";
    }

    // Handle update
    @PostMapping("/update")
    public String updateRecipient(@ModelAttribute("recipient") Recipient recipient) {
        recipientService.updateRecipient(recipient);
        return "redirect:/recipient/list?updated";
    }
}
