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

    @GetMapping("/register")
    public String showRegistrationForm(Model model, @RequestParam(required = false) String success,
                                       @RequestParam(required = false) String updated) {
        model.addAttribute("recipient", new Recipient());
        model.addAttribute("success", success);
        model.addAttribute("updated", updated);
        return "recipient"; // recipient.html
    }

    @PostMapping("/register")
    public String registerRecipient(@ModelAttribute("recipient") Recipient recipient, Model model) {
        Recipient savedRecipient = recipientService.registerRecipient(recipient);
        if (savedRecipient != null) {
            return "redirect:/recipient/register?success";
        } else {
            model.addAttribute("error", "Failed to register recipient.");
            return "recipient";
        }
    }

    @GetMapping("/list")
    public String listRecipients(Model model) {
        List<Recipient> recipients = recipientService.getAllRecipients();
        model.addAttribute("recipients", recipients);
        return "recipient_list"; // recipient_list.html
    }

    // ✅ FIX: Change Integer to String here
    @GetMapping("/edit/{recipientId}")
    public String showEditForm(@PathVariable String recipientId, Model model) {
        Recipient recipient = recipientService.getAllRecipients().stream()
                .filter(r -> recipientId.equals(r.getRecipientId()))
                .findFirst().orElse(null);
        if (recipient == null) {
            return "redirect:/recipient/list";
        }
        model.addAttribute("recipient", recipient);
        model.addAttribute("editMode", true);
        return "recipient"; // recipient.html
    }

    @PostMapping("/update")
    public String updateRecipient(@ModelAttribute("recipient") Recipient recipient) {
        recipientService.updateRecipient(recipient);
        return "redirect:/recipient/register?updated";
    }
}
